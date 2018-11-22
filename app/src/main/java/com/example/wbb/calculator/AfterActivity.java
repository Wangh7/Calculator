package com.example.wbb.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AfterActivity extends AppCompatActivity {
    private int[] idNum = {R.id.bt_num0, R.id.bt_num1, R.id.bt_num2, R.id.bt_num3,
            R.id.bt_num4, R.id.bt_num5, R.id.bt_num6, R.id.bt_num7, R.id.bt_num8, R.id.bt_num9};  //数字Number输入
    private int[] idCal = {R.id.bt_add, R.id.bt_sub, R.id.bt_mul, R.id.bt_div,R.id.bt_left,R.id.bt_right,R.id.bt_dot};  //运算符
    private Button[] buttonsCal = new Button[idCal.length];
    private Button[] buttonsNum = new Button[idNum.length];
    private Button buttonEqu;   //=
    private Button buttonClear;  // AC
    private Button buttonDel;
    private TextView input ;
    private TextView output;
    private static String Text;
    @Override
     // EditText为输入字符行
     // TextView为结果
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after);
        input = (TextView) findViewById(R.id.tx_input);
        input.setText("");
        input.setEnabled(false);
        output = (TextView) findViewById(R.id.tx_output);
        output.setText("");
        buttonEqu = (Button)findViewById(R.id.bt_equ);
        buttonEqu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                output.setText(new Calculate(input.getText().toString()).str);
            }
        });
        buttonClear = (Button) findViewById(R.id.bt_clear);
        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input.setText("");
                output.setText("");
            }
        });
        buttonDel = (Button) findViewById(R.id.bt_del);

        buttonDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!input.getText().toString().isEmpty() ) {
                    Text = input.getText().toString();
                    Text = Text.substring(0, Text.length() - 1);
                    input.setText(Text);
                }
            }
        });

        //注册单击事件

        for (int idcal = 0; idcal < idCal.length; idcal++) {
            buttonsCal[idcal] = (Button) findViewById(idCal[idcal]);
            buttonsCal[idcal].setOnClickListener(new CalOnClick(buttonsCal[idcal].getText().toString()));

        }
        for (int i = 0; i < idNum.length; i++) {
            buttonsNum[i] = (Button) findViewById(idNum[i]);
            buttonsNum[i].setOnClickListener(new NumberOnClick(buttonsNum[i].getText().toString()));
        }




    }
    //继承OnClick接口
    class NumberOnClick implements View.OnClickListener {
        String Msg;
        /**
         *
         * @param msg 点击按钮传入字符
         */
        public NumberOnClick(String msg) {
            Msg = msg;
        }

        @Override
        public void onClick(View v) {
            if (!output.getText().toString().equals("")) {
                input.setText("");
                output.setText("");
            }

            input.append(Msg);
        }
    }
    class CalOnClick implements  View.OnClickListener{
        String Msg;
        String[] calSymbol = {"+", "-", "×", "÷","."};
        public CalOnClick(String msg) {
            Msg = msg;
        }
        @Override
        public void onClick(View v) {
            if (!output.getText().toString().equals("")) {
                input.setText("");
                output.setText("");
            }
            // 检查是否运算符重复输入
            for (int i = 0; i < calSymbol.length; i++) {
                if (Msg.equals(calSymbol[i])) {
                    if (input.getText().toString().split("")
                            [input.getText().toString().split("").length - 1].equals(calSymbol[i])) {
                        Msg = "";
                    }

                }
            }
            input.append(Msg);
        }
    }


    public class Calculate {    //具体运算
        public  String s1;
        StringBuilder str;

        public Calculate(String m) {
            this.s1 = m;
            try {
                eval();
            } catch (Exception e) {
                str.delete(0, str.length());
                str.append("ERROR");
            }
        }

        /**
         *中缀表达式转后缀表达式
         *
         *遍历中缀的list
         *1、数字时，加入后缀list
         *2、“(”时，压栈
         *3、 若为 ')'，则依次弹栈,把弹出的运算符加入后缀表达式中，直到出现'('；
         *4、若为运算符，对做如下处置
         *   1、如果栈为空，则压栈
         *   2、如果栈不为空:
         *     1、stack.peek().equals("(")  则压栈
         *     2、比较str和stack.peek()的优先级
         *        1、如果>,则运算符压栈
         *        2、<=的情况：当栈不为空时:
         *           1、stack.peek()是左括号，压栈
         *           2、<=,把peek加入后缀表达式，弹栈
         *           3、>，把运算符压栈，停止对栈的操作
         *    执行完栈的操作之后，还得判断:如果栈为空,运算符压栈
         */
        public List<String> midToAfter(List<String> midList)throws EmptyStackException {
            List<String> afterList=new ArrayList<String>();
            Stack<String> stack=new Stack<String>();
            for(String str:midList){
                int flag=this.matchWitch(str);
                switch (flag) {
                    case 7:
                        afterList.add(str);
                        break;
                    case 1:
                        stack.push(str);
                        break;
                    case 2:
                        String pop=stack.pop();
                        while(!pop.equals("(")){
                            afterList.add(pop);
                            pop=stack.pop();
                        }
                        break;
                    default:
                        if(stack.isEmpty()){
                            stack.push(str);
                            break;
                        }
                        else{
                            if(stack.peek().equals("(")){
                                stack.push(str);
                                break;
                            }else{
                                int ji1=this.firstLv(str);
                                int ji2=this.firstLv(stack.peek());
                                if(ji1>ji2){
                                    stack.push(str);
                                }else{
                                    while(!stack.isEmpty()){
                                        String f=stack.peek();
                                        if(f.equals("(")){
                                            stack.push(str);
                                            break;
                                        }else{
                                            if(this.firstLv(str)<=this.firstLv(f)){
                                                afterList.add(f);
                                                stack.pop();
                                            }else{
                                                stack.push(str);
                                                break;
                                            }
                                        }
                                    }
                                    if(stack.isEmpty()){
                                        stack.push(str);
                                    }
                                }
                                break;
                            }
                        }
                }
            }
            while(!stack.isEmpty()){
                afterList.add(stack.pop());
            }
            StringBuffer sb=new StringBuffer();
            for(String s:afterList){
                sb.append(s+" ");
            }
            //System.out.println(sb.toString());
            return afterList;
        }

        public int firstLv(String str){   //判断优先级
            int result=0;
            if(str.equals("+")||str.equals("-")){
                result=1;
            }else{
                result=2;
            }
            return result;
        }

        public int matchWitch(String s){    //判断当前是什么类型字符
            if(s.equals("(")){
                return 1;
            }else if(s.equals(")")){
                return 2;
            }else if(s.equals("+")){
                return 3;
            }else if(s.equals("-")){
                return 4;
            }else if(s.equals("×")){
                return 5;
            }else if(s.equals("÷")){
                return 6;
            }else{
                return 7;
            }
        }

        public Double calculteEqu(Double pop2, Double pop1, String str){   //运算类
            Double value=0.0;
            if(str.equals("+")){
                value=pop2+pop1;
            }else if(str.equals("-")){
                value=pop2-pop1;
            }else if(str.equals("×")){
                value=pop2*pop1;
            }else {
                value=pop2/pop1;
            }
            return value;
        }
        private double result;

        public double getResult() {
            return result;
        }
        public void setResult(double result) {
            this.result = result;
        }
        private int state;

        public int getState() {
            return state;
        }
        public void setState(int state) {
            this.state = state;
        }

        public void countHouzhui(List<String> list){
            str = new StringBuilder("");
            state=0;
            result=0;
            Stack<Double> stack=new Stack<Double>();
            for(String str:list){
                int flag=this.matchWitch(str);
                switch (flag) {
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                        Double pop1=stack.pop();
                        Double pop2=stack.pop();
                        Double value=this.calculteEqu(pop2, pop1, str);
                        stack.push(value);
                        break;
                    default:
                        Double push=Double.parseDouble(str);
                        stack.push(push);
                        break;
                }
            }
            if(stack.isEmpty()){
                state=1;
            }else{
                result=stack.peek();
                str.append(stack.pop());
            }


        }

        public void eval()throws Exception{
            List<String> list=new ArrayList<String>();
            //匹配运算符、括号、整数、小数，注意-和*要加\\
            Pattern p = Pattern.compile("[+\\-÷×()]|\\d+\\.?\\d*");
            Matcher m = p.matcher(s1);
            while(m.find()){
                list.add(m.group());
            }
            List<String> afterList=this.midToAfter(list);
            this.countHouzhui(afterList);
        }



    }
}