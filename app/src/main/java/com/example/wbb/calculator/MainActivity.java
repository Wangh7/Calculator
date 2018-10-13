package com.example.wbb.calculator;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public String exp = "0";
    public double a = 0;
    public int click = 0;
    public int dot = 0;
    public int state = 0;//0 is null,1 is add,2 is sub,3 is mul,4 is div
    public Button[] buttons = new Button[11];
    public Button[] operations = new Button[5];
    public Button[] functions = new Button[3];
    public int[] flags = new int[]{R.id.bt_num0, R.id.bt_num1, R.id.bt_num2, R.id.bt_num3, R.id.bt_num4,
            R.id.bt_num5, R.id.bt_num6, R.id.bt_num7, R.id.bt_num8, R.id.bt_num9, R.id.bt_clear};
    public int[] flags_ope = new int[]{R.id.bt_equ, R.id.bt_add, R.id.bt_sub, R.id.bt_mul, R.id.bt_div};
    public int[] flags_fun = new int[]{R.id.bt_fuhao, R.id.bt_clear, R.id.bt_dot};

    //public TextView tx = (TextView) findViewById(R.id.tx_bus);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        for (int i = 0; i < flags.length; i++)
            buttons[i] = (Button) findViewById(flags[i]);
        for (int i = 0; i < flags_ope.length; i++)
            operations[i] = (Button) findViewById(flags_ope[i]);
        for (int i = 0; i < flags_fun.length; i++)
            functions[i] = (Button) findViewById(flags_fun[i]);
        buttons[0].setOnClickListener(v -> click_show("0"));
        buttons[1].setOnClickListener(v -> click_show("1"));
        buttons[2].setOnClickListener(v -> click_show("2"));
        buttons[3].setOnClickListener(v -> click_show("3"));
        buttons[4].setOnClickListener(v -> click_show("4"));
        buttons[5].setOnClickListener(v -> click_show("5"));
        buttons[6].setOnClickListener(v -> click_show("6"));
        buttons[7].setOnClickListener(v -> click_show("7"));
        buttons[8].setOnClickListener(v -> click_show("8"));
        buttons[9].setOnClickListener(v -> click_show("9"));
        functions[0].setOnClickListener(v -> click_fuhao());//+-
        functions[1].setOnClickListener(v -> click_clear());//AC
        functions[2].setOnClickListener(v -> click_dot());//.
        operations[0].setOnClickListener(v -> click_equ());//eql
        operations[1].setOnClickListener(v -> click_add());//add
        operations[2].setOnClickListener(v -> click_sub());//sub
        operations[3].setOnClickListener(v -> click_mul());//mul
        operations[4].setOnClickListener(v -> click_div());//div
    }

    public void change_clear(int i) {
        if (i == 0) {
            functions[1].setText("AC");
            functions[1].setTextColor(Color.RED);
        }
        if (i == 1) {
            functions[1].setText("CE");
            functions[1].setTextColor(Color.rgb(0,200,0));
        }
    }

    public void click_show(String i) {
        change_clear(1);
        resume_color();
        if (exp.equals("0"))
            exp = i;
        else
            exp = exp + i;
        show(exp);
        click = 1;
    }

    public void click_dot() {
        change_clear(1);
        if (dot == 0) {
            if (exp.equals("0"))
                exp = "0.";
            else
                exp = exp + ".";
            show(exp);
        }
        dot = 1;
    }

    public void click_fuhao() {
        double test;
        if (state == 0 && a != 0) {
            test = -1 * a;
            a = test;
            if (Math.round(test) - test == 0)
                show(String.valueOf((int) test));
            else
                show(String.valueOf(test));
        } else {
            test = -1 * Double.valueOf(exp);
            if (Math.round(test) - test == 0)
                exp = String.valueOf((int) test);
            else
                exp = String.valueOf(test);
            show(exp);
        }
    }

    public void click_clear() {
        if (functions[1].getText().equals("AC")) {
            exp = "0";
            a = 0;
            state = 0;
            dot = 0;
            resume_color();
            show(exp);
        }
        if (functions[1].getText().equals("CE")) {
            exp = "0";
            dot = 0;
            if(state!=0)
                change_color(state);
            show(exp);
            change_clear(0);
        }
    }

    public void trans() {
        a = Double.valueOf(exp);
    }

    public void resume_color() {
        operations[1].setTextColor(Color.BLACK);
        operations[2].setTextColor(Color.BLACK);
        operations[3].setTextColor(Color.BLACK);
        operations[4].setTextColor(Color.BLACK);
    }

    public void change_color(int i) {
        resume_color();
        operations[i].setTextColor(Color.rgb(255, 215, 0));
    }

    public void click_add() {
        change_clear(1);
        change_color(1);
        if (state == 0 && a != 0)
            state = 1;
        if (state != 0 && click == 0)
            state = 1;
        equ();
        state = 1;
        click = 0;
        dot = 0;
    }

    public void click_sub() {
        change_clear(1);
        change_color(2);
        if (state == 0 && a != 0)
            state = 2;
        if (state != 0 && click == 0)
            state = 2;
        equ();
        state = 2;
        click = 0;
        dot = 0;
    }

    public void click_mul() {
        change_clear(1);
        change_color(3);
        if (state == 0 && a != 0 || click == 0)
            state = 3;
        else {
            equ();
            state = 3;
        }
        click = 0;
        dot = 0;
    }

    public void click_div() {
        change_clear(1);
        change_color(4);
        if (state == 0 && a != 0 || click == 0)
            state = 4;
        else {
            equ();
            state = 4;
        }
        click = 0;
        dot = 0;
    }

    public void equ() {
        switch (state) {
            case 0:
                trans();
                break;
            case 1:
                a = a + Double.valueOf(exp);
                break;
            case 2:
                a = a - Double.valueOf(exp);
                break;
            case 3:
                a = a * Double.valueOf(exp);
                break;
            case 4:
                try {
                    a = a / Double.valueOf(exp);
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                    click_clear();
                }
                break;
        }

        exp = "0";
        if (Math.round(a) - a == 0)
            show(String.valueOf((int) a));
        else
            show(String.valueOf(a));
    }

    public void click_equ() {
        change_clear(0);
        click = 0;
        if (state != 0) {
            equ();
            state = 0;
            resume_color();
        }
        dot = 0;
    }

    public void show(String text) {
        TextView tx = (TextView) findViewById(R.id.tx_bus);
        tx.setText(text);
    }
}
