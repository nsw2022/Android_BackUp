package com.nsw2022.ex13ediltext;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText et01, et02, et03;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et01=findViewById(R.id.et01);
        et02=findViewById(R.id.et02);
        et03=findViewById(R.id.et03);

        //EditText의 글씨가 변결될때 마다 반응하는 리스너 객체 생성 및 설정
        et01.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //첫번째 파라미터 CharSequence : 변경된 글씨
                if ( charSequence.length() == 3 ){
                    //포커스를 et02으로 변경
                    et02.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        et02.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if ( charSequence.length()==4 ) et03.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
}