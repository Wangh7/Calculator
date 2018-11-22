package com.example.wbb.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.Button;
import android.widget.TextView;

import static android.widget.TextView.AUTO_SIZE_TEXT_TYPE_NONE;
import static android.widget.TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM;

public class TransActivity extends AppCompatActivity {
    public int[] flags = new int[]{R.id.bt_num0, R.id.bt_num1, R.id.bt_num2, R.id.bt_num3, R.id.bt_num4,
            R.id.bt_num5, R.id.bt_num6, R.id.bt_num7, R.id.bt_num8, R.id.bt_num9, R.id.bt_clear};
    public Button[] buttons = new Button[11];
    public String exp = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans);
        for (int i = 0; i < flags.length; i++)
            buttons[i] = (Button) findViewById(flags[i]);
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
        buttons[10].setOnClickListener(v-> click_clear());
    }
    public void click_show(String i) {
        TextView tx = (TextView) findViewById(R.id.ans2_tx);
        tx.setAutoSizeTextTypeWithDefaults(AUTO_SIZE_TEXT_TYPE_UNIFORM);
        //自动调整字体大小

        //resume_color();
        if (exp.equals("0"))
            exp = i;
        else
            exp = exp + i;
        show(exp);
        //click = 1;
    }
    public void show(String text) {
        TextView tx10 = (TextView) findViewById(R.id.ans10_tx);
        TextView tx2 = (TextView) findViewById(R.id.ans2_tx);
        TextView tx8 = (TextView) findViewById(R.id.ans8_tx);
        TextView tx16 = (TextView) findViewById(R.id.ans16_tx);

        tx10.setText(text);
        tx2.setText(Integer.toBinaryString(Integer.valueOf(text)));
        tx8.setText(Integer.toOctalString(Integer.valueOf(text)));
        tx16.setText(Integer.toHexString(Integer.valueOf(text)));

    }
    public void click_clear() {
        TextView tx = (TextView)findViewById(R.id.ans2_tx);
        tx.setAutoSizeTextTypeWithDefaults(AUTO_SIZE_TEXT_TYPE_NONE);
        tx.setTextSize(TypedValue.COMPLEX_UNIT_SP, 40);
        exp = "0";
        show(exp);
    }
}
