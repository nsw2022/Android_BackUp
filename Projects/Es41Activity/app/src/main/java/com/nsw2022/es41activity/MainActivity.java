package com.nsw2022.es41activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btn, btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn=findViewById(R.id.btn);
        //람다표현식 - 인터페이스의 익명클래스 구현을 충약형으로 작성하는 문법
        btn.setOnClickListener(view -> {
            //secondActivity를 실행하기.
            // 새로운 액티비티를 실행시켜주는 택배기사(Intent) 객체 생성
            Intent intent = new Intent(this,SecondActivity.class);
            startActivity(intent);

            // 현재 액티비티를 종료시켜 버리기.. [ Back Stack 에 보관되지 않도록 ]
            finish();

        });

        btn2=findViewById(R.id.btn2);
        btn2.setOnClickListener(view -> {
            Intent intent = new Intent(this, ThridActivity.class);
            startActivity(intent);

        });

    }
}