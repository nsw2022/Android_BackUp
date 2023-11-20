package com.nsw2022.ex50threadcontrol;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    MyThread myThread;

    // 엑티비티의 화면은 사용자가 원하지 않아도 언제든 가려질 수 있음. 모바일의 특징
    // 액티비티가 생성되고 화면에 보여지다가.. 다시 안보여지고 추후 메모리에서 제거될때 까지..
    // 여러 상황별로 자동으로 실행되는 콜백메소드들을 미리 설계해 놓았음.
    // 액티비티의 생성부터 소멸까지의 상황에서 자동 발동하는 메소드들을 [ 라이프 사이클 메소드 ] 라고 부름


    @Override
    protected void onPause() {
        super.onPause();

        myThread.isRun=false; //Thread의 while 반복문을 종료..
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn).setOnClickListener(view -> {

            // 현재시간을 5초마다 Toast로 보여주기
            myThread=new MyThread();
            myThread.start();

        });
    }

    class MyThread extends Thread{

        boolean isRun=true;

        @Override
        public void run() {
            //5초간격으로 현재시간을 Toast로 보이기
            while (isRun){
                //화면 UI 갱신은 UI thread만 가능함
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "aaa", Toast.LENGTH_SHORT).show();
                    }
                });


                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }//while

        }//run method

    }//MyThread

}//MainActivity