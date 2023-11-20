package com.nsw2022.ex49threadprogressdialog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.le.PeriodicAdvertisingParameters;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Trace;

public class MainActivity extends AppCompatActivity {
    ProgressDialog dialog=null;
    int gauge=0;


    Handler handler=new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            dialog.dismiss();
            dialog=null;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn).setOnClickListener(view -> {
            if (dialog!=null) return;

            //wheel type progress dialog
            dialog=new ProgressDialog(this);
            dialog.setTitle("wheel dialog");
            dialog.setMessage("downloading");
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

            dialog.setCanceledOnTouchOutside(false);

            dialog.show();

            //3초뒤에 다이얼로그 종료시키기..
            handler.sendEmptyMessageDelayed(0,3000);

        });

        findViewById(R.id.btn2).setOnClickListener(view -> {
            if (dialog!=null) return;

            dialog=new ProgressDialog(this);
            dialog.setTitle("막대바 다이얼로그");
            dialog.setMessage("다운로드중...");
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

            dialog.setMax(100);

            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

            dialog.setProgress(gauge);

            // 게이지값을 증가시키는 작업을 수행하는 별도  Thread를 만들고 곧바로 실행
            Thread t = new Thread(){
                @Override
                public void run() {
                    gauge=0;
                    while (gauge<100){
                        gauge++;
                        dialog.setProgress(gauge);// 자동 runOnUiThread()

                        // 0.5 멈추기
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }//while

                    dialog.dismiss();
                    dialog=null;
                }
            };
            t.start();

        });

    }


}