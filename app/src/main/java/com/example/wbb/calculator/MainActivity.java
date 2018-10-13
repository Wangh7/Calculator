package com.example.wbb.calculator;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public String exp = "0";
    public int a = 0;
    public int click = 0;
    public int state = 0;//0 is null,1 is add,2 is sub,3 is mul,4 is div
    public Button[] buttons = new Button[11];
    public Button[] operations = new Button[5];
    public int[] flags = new int[]{R.id.bt_num0, R.id.bt_num1, R.id.bt_num2, R.id.bt_num3, R.id.bt_num4,
            R.id.bt_num5, R.id.bt_num6, R.id.bt_num7, R.id.bt_num8, R.id.bt_num9, R.id.bt_clear};
    public int[] flags_ope = new int[]{R.id.bt_equ, R.id.bt_add, R.id.bt_sub, R.id.bt_mul, R.id.bt_div};

    //public TextView tx = (TextView) findViewById(R.id.tx_bus);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        for (int i = 0; i < flags.length; i++) {
            buttons[i] = (Button) findViewById(flags[i]);
        }
        for (int i = 0; i < flags_ope.length; i++)
            operations[i] = (Button) findViewById(flags_ope[i]);
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
        buttons[10].setOnClickListener(v -> click_clear());
        operations[1].setOnClickListener(v -> click_add());
        operations[2].setOnClickListener(v -> click_sub());
        operations[3].setOnClickListener(v -> click_mul());
        operations[4].setOnClickListener(v -> click_div());
        operations[0].setOnClickListener(v -> click_equ());
    }

    public void click_show(String i) {
        resume_color();
        if (exp.equals("0"))
            exp = i;
        else
            exp = exp + i;
        show(exp);
        click = 1;
    }

    public void click_clear() {
        exp = "0";
        a = 0;
        state = 0;
        resume_color();
        show(exp);
    }

    public void trans() {
        a = Integer.valueOf(exp);
    }

    public void resume_color() {
        operations[1].setTextColor(Color.BLACK);
        operations[2].setTextColor(Color.BLACK);
        operations[3].setTextColor(Color.BLACK);
        operations[4].setTextColor(Color.BLACK);
    }

    public void change_color(int i) {
        resume_color();
        operations[i].setTextColor(Color.rgb(255, 220, 0));
    }

    public void click_add() {
        change_color(1);
        if (state == 0 && a != 0)
            state = 1;
        equ();
        state = 1;
        click = 0;
    }

    public void click_sub() {
        change_color(2);
        if (state == 0 && a != 0)
            state = 2;
        if (state != 0 && click == 0)
            state = 2;
        equ();
        state = 2;
        click = 0;
    }

    public void click_mul() {
        change_color(3);
        if (state == 0 && a != 0 || click == 0)
            state = 3;
        else {
            equ();
            state = 3;
        }
        click = 0;
    }

    public void click_div() {
        change_color(4);
        if (state == 0 && a != 0 || click == 0)
            state = 4;
        else {
            equ();
            state = 4;
        }
        click = 0;
    }

    public void equ() {
        switch (state) {
            case 0:
                trans();
                break;
            case 1:
                a = a + Integer.valueOf(exp);
                break;
            case 2:
                a = a - Integer.valueOf(exp);
                break;
            case 3:
                a = a * Integer.valueOf(exp);
                break;
            case 4:
                try {
                    a = a / Integer.valueOf(exp);
                }catch (Exception e){
                    Toast.makeText(MainActivity.this,"Error!",Toast.LENGTH_SHORT).show();
                    click_clear();
                }
                break;
        }
        exp = "0";
        show(String.valueOf(a));
    }

    public void click_equ() {
        click = 0;
        if (state != 0) {
            equ();
            state = 0;
            resume_color();
        }
    }

    public void show(String a) {
        TextView tx = (TextView) findViewById(R.id.tx_bus);
        tx.setText(a);
    }
}
