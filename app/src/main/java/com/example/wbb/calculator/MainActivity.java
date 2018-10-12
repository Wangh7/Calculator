package com.example.wbb.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public String exp="0";
    public Button[] buttons = new Button[11];
    public int[] flags = new int[]{R.id.bt_num0,R.id.bt_num1,R.id.bt_num2,R.id.bt_num3,R.id.bt_num4,
    R.id.bt_num5,R.id.bt_num6,R.id.bt_num7,R.id.bt_num8,R.id.bt_num9,R.id.bt_clear};
    //public TextView tx = (TextView) findViewById(R.id.tx_bus);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        for(int i=0;i<flags.length;i++){
            buttons[i] = (Button)findViewById(flags[i]);
        }
        buttons[0].setOnClickListener(v->click_show("0"));
        buttons[1].setOnClickListener(v->click_show("1"));
        buttons[2].setOnClickListener(v->click_show("2"));
        buttons[3].setOnClickListener(v->click_show("3"));
        buttons[4].setOnClickListener(v->click_show("4"));
        buttons[5].setOnClickListener(v->click_show("5"));
        buttons[6].setOnClickListener(v->click_show("6"));
        buttons[7].setOnClickListener(v->click_show("7"));
        buttons[8].setOnClickListener(v->click_show("8"));
        buttons[9].setOnClickListener(v->click_show("9"));
        buttons[10].setOnClickListener(v->click_clear());
    }
    public void click_show(String i){
        if(exp.equals("0"))
            exp=i;
        else
            exp=exp+i;
        show();
    }
    public void click_clear(){
        exp="0";
        show();
    }
    public void show(){
        TextView tx = (TextView) findViewById(R.id.tx_bus);
        tx.setText(exp);
    }
}
