package com.nsw2022.ex51activitylifecyclemethod;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    // 액티비티가 생성되어 화면에 보여지다가... 안보여지고.. 메모리에서 사라지는 순간까지.. 상황별로
    // 자동 발동하는 콜백 메소드 : Life cycle method.. 라고 부름.

    // 1. 액티비티가 처음 메모리에 만들어질때 자동으로 실행되는 콜백메소드 [ 이 메소드가 끝나기 전에는 어떤 화면 작업도 안된 상태임 ]
    //   보통 이 메소드에서 화면에 보여질 view 들을 설정하고 리스너 처리 및 초기화 작업을 수행함.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 아직 화면이 그려지기 전이어서 이 메소드가 실행되었음을 알 수 있도록.. 기록(log) 남기기..
        Log.i("TAG","onCreate method..");


        findViewById(R.id.btn).setOnClickListener(view -> finish());

    }
    // 2. 액티비티의 뷰들이 보이기 시작할 때 자동으로 실행되는 콜백메소드
    @Override
    protected void onStart() {
        super.onStart();
        Log.i("TAG","onStart method..");
    }
    // 3. 액티비티 뷰들이 온전히 보이고 뷰들과 상호작용(클릭등의 행동)이 가능할때 자동으로 실행되는 메소드
    //   보통 이 메소드 아넹서 화면에 보여줄 Data들을 서버에서 불러오는 등의 작업을 수행함.
    @Override
    protected void onResume() {
        super.onResume();
        Log.i("TAG","onResume method..");
    }

    // 1~~3 까지의 라이프사이클 메소드가 자동 실행된 후 .. 사용자가 화면을 제어하게 됨.
    // 라이트사이클 메소드는  일단 여기서 더이상 진해오디지 않음.
    // 4. 어떤이유에서든 화면에서 안보이는 순간이 될때 자동 발동하는 콜백메소드
    // 보통 이 메소드 안에서 스레드를 멈추거나 하는 등의 작업 수행

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("TAG","onPause method..");
    }

    // 5. 액티비티가 화면에서 완전히 안보이면 자동 실행되는 메소드

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("TAG","onStop method..");
    }

    // 액티비티가 종료되지 않고 다른 화면에 의해 가려질때 4~5 메소드가 발동하고 더이상 진행하지 않음.

    //6. 사용자가 '뒤로가기' 버튼을 누르거나 개발자가 finish() 기능으로 액티비티를 종료시켜서
    //   메모리에서 완전히 없어질때 발동되는 콜백 메소드

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.i("TAG","onDestroy method..");
    }
}