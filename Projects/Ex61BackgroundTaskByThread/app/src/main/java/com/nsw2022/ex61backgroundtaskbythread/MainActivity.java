package com.nsw2022.ex61backgroundtaskbythread;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_start).setOnClickListener(view -> clickStart());
        findViewById(R.id.btn_stop).setOnClickListener(view -> clickStop());

    }

    // 디바이스의 뒤로가기 버튼을 클릭했을때.. 자동 발동하는 콜백메소드
    @Override
    public void onBackPressed() {
        //super.onBackPressed(); 여기가 진짜나갈꺼야? 같은 다이알로그
        finish();
    }

    MyThread myThread;

    void clickStart(){
        if (myThread!=null) return;

        // MainThread는 액티비티가 화면에 보이지 않으면 동작을 멈춤.
        myThread=new MyThread();
        myThread.start();


    }


    void clickStop(){
        if(myThread !=null){
            myThread.isRun=false;
            myThread=null;
        }else{
            Toast.makeText(MainActivity.this, "Thread를 참조하고 있지 않습니다.", Toast.LENGTH_SHORT).show();
        }


    }
    // 5초에 한번씩 Toast를 보여주는 동작을 수행하는 별도 Thread 클래스를 설계
    class MyThread extends Thread{
        boolean isRun=true;
        @Override
        public void run() {
            while(isRun){
                // 별도의 Thread는 UI 변경작업 불가
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "aaa", Toast.LENGTH_SHORT).show();

                    }
                });

                // 5초간 잠시 잠재우기
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}