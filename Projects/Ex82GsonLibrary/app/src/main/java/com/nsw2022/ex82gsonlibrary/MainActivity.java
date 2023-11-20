package com.nsw2022.ex82gsonlibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;

import com.google.gson.Gson;
import com.nsw2022.ex82gsonlibrary.databinding.ActivityMainBinding;

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
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btn.setOnClickListener(v->clickBtn());
        binding.btn2.setOnClickListener(v->clickBtn2());
        binding.btn3.setOnClickListener(v->clickBtn3());
        binding.btn4.setOnClickListener(v->clickBtn4());

    }

    void clickBtn4(){
        //네트워크 서버에 있는 json array 파일을 Gson을 이용하여 Person[] 배열객체로 전환
        new Thread(){
            @Override
            public void run() {
                String serverUrl = "http://tmddn3410.dothome.co.kr/04Retrofit/bbb.json";
                try {
                    URL url = new URL(serverUrl);
                    InputStream is = url.openStream();
                    InputStreamReader isr = new InputStreamReader(is);

                    Gson gson = new Gson();
                    Person[] people = gson.fromJson(isr,Person[].class);

                    StringBuffer buffer = new StringBuffer();
                    for ( Person p : people ) {
                        buffer.append( p.name + " : " + p.age + "\n" );
                    }
                    runOnUiThread(  ()->  binding.tv.setText(buffer.toString())   );



                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    void clickBtn3(){
        // 네트워크 서버에 있는 json 파일을 읽어와서 GSON을 이용하여 Person객체로 변환
        new Thread(){
            @Override
            public void run() {
                String serverUrl = "http://tmddn3410.dothome.co.kr/04Retrofit/aaa.json";
                try {
                    URL url = new URL(serverUrl);
                    InputStream is = url.openStream();
                    InputStreamReader isr = new InputStreamReader(is);// 문자스트림

                    // Gson 이 알아서 저 문자스트림을 통해 json파일을 읽어와서 Person 객체로 만들어줌
                    Gson gson = new Gson();
                    Person person = gson.fromJson(isr,Person.class);

                    runOnUiThread(()-> binding.tv.setText(person.name + "\n" + person.age));




                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }.start();

    }

    void clickBtn2(){
        //Person객체를 json 문자열로 변환
        Person person = new Person("robin",25);

        Gson gson = new Gson();
        String s=gson.toJson(person);
        binding.tv.setText(s);

    }

    void clickBtn(){
        // GSON library를 이요하여 json문자열을 곧바로 Person 객체 파싱
        String jsonStr="{'name':'sam','age':20}";

        Gson gson = new Gson();
        Person person = gson.fromJson(jsonStr,Person.class); //json문자열-->Person객체
        binding.tv.setText( person.name + " : " + person.age );
    }


}