package com.nsw2022.ex43activity4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn).setOnClickListener(view -> {

            //SecondActivity를 실행해보기.. 단, 클래스명을 직접 명시하지 않고 - 묵시적 인텐트
            Intent intent = new Intent();

            intent.setAction("aaa"); //디바이스에 설치된 모든 앱 중에 "aaa"라는 액션값을 받아들일 수 있는 컴포넌트를 찾음.


            startActivity(intent);
        });

    }
}