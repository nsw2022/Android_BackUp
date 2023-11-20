package com.nsw2022.ex42activity2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // 이 액티비티를 실행시켜준 택배기사(Intent)를 참조하기
        Intent intent = getIntent();

        // 택배기사가 가지고 온 추가(Extra)데이터 받기
        String name=intent.getStringExtra("name");
        int age=intent.getIntExtra("age",0);

        // "이름" name 은 제목줄에 표시
        getSupportActionBar().setTitle(name);

        // "나이" age 는 TextView에 표시
        tv=findViewById(R.id.tv);
        tv.setText(age+"살");


    }
}