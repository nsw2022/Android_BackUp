package com.nsw2022.ex06compoundbutton;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    CheckBox cb01, cb02, cb03;
    Button btn;
    TextView tv;

    ToggleButton tb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cb01=findViewById(R.id.cd01);
        cb02=findViewById(R.id.cd02);
        cb03=findViewById(R.id.cd03);
        btn=findViewById(R.id.btn);
        tv=findViewById(R.id.tv);


        //버튼을 클릭했을때 반응하는 리스너 객체 생성 및 설정
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //체크박스들 중에서 체크되어 있는 녀석의, 글씨 얻어와서 TextView에 보여주기
                String s="";

                if ( cb01.isChecked() ) s += cb01.getText().toString();
                if ( cb02.isChecked() ) s += cb02.getText().toString();
                if ( cb03.isChecked() ) s += cb03.getText().toString();

                tv.setText(s);
            }
        });

        // 체크박스의 체크상태가 변경될때 마다 반응하는 리스너 객체 생성 및 설정
        cb01.setOnCheckedChangeListener(changeListener);
        cb02.setOnCheckedChangeListener(changeListener);
        cb03.setOnCheckedChangeListener(changeListener);

        tb=findViewById(R.id.tb);
        //토글버튼이 변경될때마다 반응하는 리스너 생성 및 설정
        tb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                tv.setText( b+"" );
            }
        });


    }// onCreate method..

    //멤버변수의 위치..
    //체크상태가 변경되는 것에 반응하는 리스너 객체 생성 및 참조변수에 대입

    CompoundButton.OnCheckedChangeListener changeListener= new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

            //체크박스들 중에서 체크되어 있는 녀석의, 글씨 얻어와서 TextView에 보여주기
            String s="";

            if ( cb01.isChecked() ) s += cb01.getText().toString();
            if ( cb02.isChecked() ) s += cb02.getText().toString();
            if ( cb03.isChecked() ) s += cb03.getText().toString();

            tv.setText(s);

        }
    };


}// MainActivity class..