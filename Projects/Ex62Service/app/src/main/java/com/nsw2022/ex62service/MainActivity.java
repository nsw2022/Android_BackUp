package com.nsw2022.ex62service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.btn_start).setOnClickListener(view -> clickStart());
        findViewById(R.id.btn_stop).setOnClickListener(view -> clickStop());

    }

    void clickStart(){
        // 음악을 백그라운드에서 재생시키는 서비스 실행
        Intent intent = new Intent(this,MyService.class);

        // Oreo 버전 부터 서비스를 실행하면 일정시간 후에 OS에서 강제로 서비스를 종료시켜버림(배터리보호정책)
        // 그래서 OS에서 강제로 종료시키지 못하는 서비스로 실행하도록 권고.
        // foreground service 라고 부름. 그래서 서비스 시작 명령을 foreground로 요청
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O) startForegroundService(intent);
        else startService(intent);

        startService(intent);

    }

    void clickStop(){
        // 백그라운드에서 음악을 재생시키던 서비스를 종료
        Intent intent = new Intent(this,MyService.class);
        stopService(intent);
    }


}