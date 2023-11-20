package com.nsw2022.compoundbotton;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    RadioButton rb01, rb02, rb03, rb04, rb05;

    CheckBox cb01, cb02, cb03, cb04;

    Button btn;

    TextView tv, tv2;

    String str;

    EditText et01, et02, et03, et04;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rb01=findViewById(R.id.rb_male);
        rb02=findViewById(R.id.rb_female);

        rb03=findViewById(R.id.rb_seoul);
        rb04=findViewById(R.id.rb_busan);
        rb05=findViewById(R.id.rb_etc);

        cb01=findViewById(R.id.cb_mail);
        cb02=findViewById(R.id.cb_phone);
        cb03=findViewById(R.id.cb_vist);
        cb04=findViewById(R.id.cb_sms);

        btn=findViewById(R.id.btn);

        tv=findViewById(R.id.tv);
        tv2=findViewById(R.id.tv_name);

        et01=findViewById(R.id.et_id);
        et02=findViewById(R.id.et_num1);
        et03=findViewById(R.id.et_num2);
        et04=findViewById(R.id.et_num3);




        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s ="";
                str="";

                String nametext = et01.getText().toString();
                String phonenumber = et02.getText().toString();
                String phonenumber2 = et03.getText().toString();
                String phonenumber3 = et04.getText().toString();







                if( rb01.isChecked() ) s += rb01.getText().toString();
                if( rb02.isChecked() ) s += rb02.getText().toString();
                if( rb03.isChecked() ) s += rb03.getText().toString();
                if( rb04.isChecked() ) s += rb04.getText().toString();
                if( rb05.isChecked() ) s += rb05.getText().toString();

                if( cb01.isChecked() ) s += cb01.getText().toString();
                if( cb02.isChecked() ) s += cb02.getText().toString();
                if( cb03.isChecked() ) s += cb03.getText().toString();
                if( cb04.isChecked() ) s += cb04.getText().toString();
                s= nametext + " " + phonenumber + "-" + phonenumber2 + "-" + phonenumber3 + "\n";
                str += s;


                tv.setText(str);

            }
        });




    }
}