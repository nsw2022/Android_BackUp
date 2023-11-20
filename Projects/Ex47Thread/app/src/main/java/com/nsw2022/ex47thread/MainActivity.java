package com.nsw2022.ex47thread;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int num=0;

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv= findViewById(R.id.tv);


        //람다식 - 익명클래스의 코드를 축약형으로 작성하는 문법
        findViewById(R.id.btn).setOnClickListener(view->{
            // 오래 걸리는 작업  -- 예) 네트워크에서 이미지나 데이터를 받아오는 작업 등..
            // 테스트 목적으로 그냥 반복문으로 대략 10초 걸리는 작업 코딩.
            // 별도의 Thread를 만들지 않고 그냥 작성하면 이 코드는 Main Thread가 처리함.
            for(int i=0; i<20; i++){
                num++;
                tv.setText(num+"");

                //억지로 스레드를 잠시 멈추기. [0.5초 == 500ms]
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }//for..

        });

        // Main Thread가 오래걸리는 작업을 수행하는 약 10여초동안 다른 작업을 할 수 없으므로..
        // 다른 버튼을 눌러도 반응하지 않고.. 만약 5초 정도 앱이 반응하지 않으면 운영체제에서 강제로
        // 에러를 발동함. 이 에러를 ANR(Application Not Responding) 라고 부름
        // 이를 해결하려면.. 오래걸리는 작업은 별도의 Thread 를 만들어서 수행하도록 하고
        // Main Thread는 UI 작업만 하도록 해야 함.


        findViewById(R.id.btn2).setOnClickListener(view->{
            //오래걸리는 작업을 수행하는 별도 직원객체(MyThread) 생성 및 작업시작
            MyThread thread= new MyThread();
            thread.start();  //자동으로 MyThread의 run()메소드가 실행됨.
        });


    }//onCreate method..

    //inner class..///////////////////////////////////////
    //오래걸리는 작업을 수행하는 직원 스레드의 클래스 설계
    class MyThread extends Thread{
        //이 스레드가 수행할 작업을 코딩하는 메소드..
        @Override
        public void run() {
            //반드시 이 영역안에서 작성해야 별도의 스레드가 처리함.
            for(int i=0;i<20;i++){
                num++;

                // 화면에 보여주는
                //tv.setText(num+"");//-에러 : 안드로이드는 별도의 Thread가 UI를 변경하는 작업을 금지함

                // Main Thread만이 UI를 변경할 수 있음. 그래서 Main Thread를 UiThread라고도 부름

                // 별도 스레드가 화면에 보여줄 내용이 있다면
                // Main Thread에게 UI변경작업 요청

                // 방법1. Handler를 이용하는 방법
                //handler.sendEmptyMessage(0);


                // 방법2. UI이 작업이 가능하도록 위임장을 받은 별도의 스레드를 만들어 처리하는 방법
                // Context 는 UI 작업이 가능하도록 위임장을 주는 기능이 이미 있음
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // 이 영역안에서는 UI변경 작업이 가능함.
                        tv.setText(num+"");
                    }
                });



                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }//for
        }//run
    }//////////////////////////////////////////

    // 별도의 Thread가 MainThread에게 UI변경 작업을 요청하는 메세지를 전달하고 처리하는 객체
    Handler handler=new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            // sendEmptyMessage()가 호출되면 자동으로 실행되는 메소드
            // 이 영역안에서는 UI작업 가능함.
            tv.setText(num+"");
        }
    };


}//MainActivity class..