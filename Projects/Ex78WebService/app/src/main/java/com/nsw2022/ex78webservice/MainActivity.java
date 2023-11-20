package com.nsw2022.ex78webservice;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import com.bumptech.glide.Glide;
import com.nsw2022.ex78webservice.databinding.ActivityMainBinding;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btn.setOnClickListener(v-> loadData());
        binding.btn2.setOnClickListener(v->{
            //이미지 로드 라이브러리.
            Glide.with(this).load("http://mrhi2022.dothome.co.kr/koala.jpg").into(binding.iv);
        });


    }

    void loadData(){
        // 서버에 있는 파일의 글씨를 읽어와서 TextView에 보여주기 - 인터넷 퍼미션필요
        // 네트워크 작업은 반드시 별도의 Thread가 해야 함.
        new Thread(){
            @Override
            public void run() {

                // 글씨를 가진 파일이 있는 서버 주소 URL 문자열
                String address= "http://mrhi2022.dothome.co.kr/index.html";

                try {
                    URL url= new URL(address);
                    InputStream is= url.openStream();
                    InputStreamReader isr= new InputStreamReader(is);
                    BufferedReader reader= new BufferedReader(isr);

                    StringBuffer buffer= new StringBuffer();
                    while(true){
                        String line= reader.readLine();
                        if(line==null) break;

                        buffer.append(line+"\n");
                    }

                    //별도의 Thread는 UI 변경작업 불가
                    runOnUiThread(()->binding.tv.setText(buffer.toString()));

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }.start();
    }

}