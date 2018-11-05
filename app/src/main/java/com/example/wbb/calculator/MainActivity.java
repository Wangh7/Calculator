package com.example.wbb.calculator;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public String exp = "0";
    public String last_exp = "0";
    public double a = 0;
    public int click = 0;
    public int dot = 0;
    public int finish = 0;
    public int powstate = 0;
    public int state = 0;//0 is null,1 is add,2 is sub,3 is mul,4 is div
    public int last_state = 0;
    public Button[] buttons = new Button[11];
    public Button[] operations = new Button[5];
    public Button[] functions = new Button[3];
    public Button[] new_functions = new Button[16];
    public int[] flags = new int[]{R.id.bt_num0, R.id.bt_num1, R.id.bt_num2, R.id.bt_num3, R.id.bt_num4,
            R.id.bt_num5, R.id.bt_num6, R.id.bt_num7, R.id.bt_num8, R.id.bt_num9, R.id.bt_clear};
    public int[] flags_ope = new int[]{R.id.bt_equ, R.id.bt_add, R.id.bt_sub, R.id.bt_mul, R.id.bt_div};
    public int[] flags_fun = new int[]{R.id.bt_fuhao, R.id.bt_clear, R.id.bt_dot};
    public int[] flags_newf = new int[]{R.id.bt_x2, R.id.bt_x3, R.id.bt_xy, R.id.bt_ex,
            R.id.bt_x_2, R.id.bt_x_3, R.id.bt_x_y, R.id.bt_ln,
            R.id.bt_sin, R.id.bt_cos, R.id.bt_tan, R.id.bt_e,
            R.id.bt_1x, R.id.bt_xx, R.id.bt_test, R.id.bt_pi,};

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.normalcal_item:
                break;
            case R.id.numbertrans_item:
                Intent intent = new Intent(MainActivity.this,TransActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        return true;
    }

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
        for (int i = 0; i < flags_newf.length; i++)
            new_functions[i] = (Button) findViewById(flags_newf[i]);
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
        new_functions[0].setOnClickListener(v -> click_pow(2));//pow2
        new_functions[1].setOnClickListener(v -> click_pow(3));//pow3
        new_functions[2].setOnClickListener(v -> click_powy());//powy
        new_functions[4].setOnClickListener(v -> click_pow(1.0 / 2.0));
        new_functions[5].setOnClickListener(v -> click_pow(1.0 / 3.0));
        new_functions[6].setOnClickListener(v -> click_powhy());
        new_functions[8].setOnClickListener(v -> click_sct(1));
        new_functions[9].setOnClickListener(v -> click_sct(2));
        new_functions[10].setOnClickListener(v -> click_sct(3));


    }

    public void click_sct(int i){
        finish = 0;
        if (click == 0){
            switch (i){
                case 1:
                    a=Math.sin(a);break;
                case 2:
                    a=Math.cos(a);break;
                case 3:
                    a=Math.tan(a);break;
                default:break;
            }
        }

        if (click != 0){
            switch (i){
                case 1:
                    a=Math.sin(Double.valueOf(exp));break;
                case 2:
                    a=Math.cos(Double.valueOf(exp));break;
                case 3:
                    a=Math.tan(Double.valueOf(exp));break;
                default:break;
            }
        }
        state = 7;
        powstate = 1;
        click_equ();
    }
    public void click_pow(double i) {

        finish = 0;
        if (click == 0)
            a = Math.pow(a, i);
        if (click != 0)
            a = Math.pow(Double.valueOf(exp), i);
        state = 7;
        powstate = 1;
        click_equ();
    }

    public void click_powy() {
        change_clear(1);
        change_color(5);
        finish = 0;
        if (state == 0 && a != 0)
            state = 5;
        if (state != 0 && click == 0)
            state = 5;
        equ();
        state = 5;
        click = 0;
        dot = 0;
    }

    public void click_powhy() {
        change_clear(1);
        change_color(6);
        finish = 0;
        if (state == 0 && a != 0)
            state = 6;
        if (state != 0 && click == 0)
            state = 6;
        equ();
        state = 6;
        click = 0;
        dot = 0;
    }

    public void change_clear(int i) {
        if (i == 0) {
            functions[1].setText("AC");
            functions[1].setTextColor(Color.RED);
        }
        if (i == 1) {
            functions[1].setText("CE");
            functions[1].setTextColor(Color.rgb(0, 200, 0));
        }
    }

    public void click_show(String i) {
        if (finish == 1) {
            a = 0;
            finish = 0;
        }
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
            last_exp = "0";
            a = 0;
            state = 0;
            powstate = 0;
            last_state = 0;
            dot = 0;
            finish = 0;
            resume_color();
            show(exp);
        }
        if (functions[1].getText().equals("CE")) {
            exp = "0";
            dot = 0;
            if (state != 0)
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
        new_functions[2].setTextColor(Color.BLACK);
        new_functions[6].setTextColor(Color.BLACK);

    }

    public void change_color(int i) {
        resume_color();
        if (i == 5)
            new_functions[2].setTextColor(Color.rgb(255, 215, 0));
        else if (i == 6)
            new_functions[6].setTextColor(Color.rgb(255, 215, 0));
        else
            operations[i].setTextColor(Color.rgb(255, 215, 0));
    }

    public void click_add() {
        change_clear(1);
        change_color(1);
        finish = 0;
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
        finish = 0;
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
        finish = 0;
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
        finish = 0;
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
            case 5:
                a = Math.pow(a, Double.valueOf(exp));
                break;
            case 6:
                a = Math.pow(a, 1.0 / Double.valueOf(exp));
            default:
                break;
        }
        last_exp = exp;
        exp = "0";
        if (Math.round(a) - a == 0)
            show(String.valueOf((int) a));
        else
            show(String.valueOf(a));
    }

    public void click_equ() {
        change_clear(0);
        click = 0;
        if (state == 0 && finish == 1) {
            state = last_state;
            exp = last_exp;
            equ();
            state = 0;
        }
        if (powstate == 1) {
            equ();
            powstate = 0;
        }
        if (state != 0) {
            equ();
            last_state = state;
            state = 0;
            resume_color();
        }
        dot = 0;
        finish = 1;
    }

    public void show(String text) {
        TextView tx = (TextView) findViewById(R.id.tx_bus);
        tx.setText(text);
    }
}
