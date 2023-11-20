package com.nsw2022.ex48threadnetworkimage;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.stream.Stream;

public class MainActivity extends AppCompatActivity {

    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv= findViewById(R.id.iv);
        findViewById(R.id.btn).setOnClickListener(view->{

            // 네트워크 상에 있는 이미지를 불러와서 ImageView에 보여주기

            // MainThread는 네트워크 작업 불가!!
            // 별도의 Thread를 생성해서 네트워크 작업을 수행하도록.
            MyThread thread= new MyThread();
            thread.start(); // 자동  run() 메소드가 실행됨

            // **주의** 앱에서 네트워크를 사용하는 것은 인터넷 비용이 사용될 수 있다는 것임.
            //          그래서 반드시 인터넷 사용에 대한 권한 요청.. [퍼미션 요청] - AndroidManifest.xml에서 작성

        });

        findViewById(R.id.btn2).setOnClickListener(view -> {
            // 네트워크상의 이미지를 손쉽게 읽어와서 이미지뷰에 기능을 가진
            // 외부 라이브러리를 사용해보기 [ Glide, Picasso ]
            String imgUrl="https://img4.yna.co.kr/photo/yna/YH/2010/02/01/PYH2010020102450000400_P2.jpg";
            Glide.with(this).load(imgUrl).into(iv);


        });



    }//onCreate method..

    class MyThread extends Thread{
        @Override
        public void run() {

            // Network 상에 있는 이미지의 경로 URL
            // 이미지가 너무 고해상도면 불러올수없음.. 그땐 옵션작업필요
            String imgUrl="https://cdn.pixabay.com/photo/2016/07/11/15/43/woman-1509956_1280.jpg";
            imgUrl="https://img4.yna.co.kr/photo/yna/YH/2010/02/01/PYH2010020102450000400_P2.jpg";

            // 서버 주소까지 연결되는 무지개로드( Stream ) 열기
            try {
                //Stream 을 열수 있는 해임달객체(URL) 생성
                URL url= new URL(imgUrl);
                // 무지개로드 열기
                InputStream is= url.openStream();

                // 스트림을 통해 이미지 데이터(byte[])들을 읽어와서 이미지뷰에 설정하기 위해
                // 안드로이드에서 이미지를 가지는 클래스(Bitmap)를 객체로 생성
                final Bitmap bm= BitmapFactory.decodeStream(is);

                // 비트맵 객체를 이미지뷰에 설정
                //iv.setImageBitmap(bm); // 에러..why? 별도 Thread 는 UI 변경작업이 불가능!!
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //이 영역안에서는 ui 변경이 가능함.
                        iv.setImageBitmap(bm); //익명클래스안에서는 지역변수 사용불가. 단, final 지역변수는 가능함
                    }
                });


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


}// MainActivity class..