package com.nsw2022.ex45activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn).setOnClickListener(view -> {
            //다른앱의 Ex44 SecondActivity 실행해보기 --- 명시적 인텐트는 본인앱의 액티비티만 실행할 수 있음.
            Intent intent = new Intent();
            intent.setAction("aaa"); // 디바이스에 설치된 모든앱을 대상으로 검사함
            startActivity(intent);

            //이 기술을 이용하여 다른 앱을 실행시킬 수 있음.
            // 예) 내 앱에서 지도앱을 실행하기
            // 예2) 내 앱에서 인터넷앱을 실행하기기
            // 예3) 내 앱에서 갤러리 (사진)앱을 실행하기
            // 예4) 내 앱에서 카메라앱을 실행하기

        });
    }
}