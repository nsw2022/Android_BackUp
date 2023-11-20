package com.nsw2022.ex59brodcastreceiver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn).setOnClickListener(view -> {
            // 방송보내기 [ 원래는 OS가 방송하는 것임.. 연습으로.. 이 앱에서 강제로 방송 실행 ]

            // 방송 : 실제 전파를 쏜다는 느낌이 아니고 Intent라는 객체가 모든 앱에게 전달되는 모습..

            //1. 명시적 Intent - 특정한 수신기(Receiver)만 들을 수 있는 방송 [ 같은 앱안에 있는 리시버만 가능 ]
//            Intent intent = new Intent(this,MyReceiver.class);
//            sendBroadcast(intent);



            //2. 묵시적(암시적) Intent - 디바이스에 설치된 모든 앱(ON/OFF 상관없이)에게 전달되는 방송
            Intent intent = new Intent();

            intent.setAction("bbb");

            sendBroadcast(intent);

            // Oreo 버전(api 26)부터 묵시적 인텐트는 OS(시스텐) 액션만 가능하도록 제한함.
            // 그럼에도 불구하고 방송하고 싶다면. 리시버를 동적으로 등록해야만 함.-AndroidManifest.xml에 등록하지 않고.. Java에서 등록..



        });
    }

    //멤버변수
    MyReceiver receiver;

    // 앱이 화면에 보여질때 리시버 등록

    @Override
    protected void onResume() {
        super.onResume();

        receiver = new MyReceiver();

        IntentFilter filter = new IntentFilter();
        filter.addAction("aaa");
        filter.addAction("bbb");

        registerReceiver(receiver,filter);

    }


    // 화면에 안보이기 시작할때 리시버 해제


    @Override
    protected void onPause() {
        super.onPause();

        unregisterReceiver(receiver);
    }
}//MainActivity