package com.nsw2022.ex79httprequest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.nsw2022.ex79httprequest.databinding.ActivityMainBinding;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnGet.setOnClickListener(v->clickGet());
        binding.btnPost.setOnClickListener(v->clickPost());


    }

    void clickGet(){
        // 네트워크작업은 언제나 별도 Thread 가 해야함
        new Thread(){
            @Override
            public void run() {

                //서버로 보낼 데이터들
                String title=binding.etTitle.getText().toString();
                String message=binding.etMsg.getText().toString();

                // GET방식으로 보낼 서버의 주소
                String severUrl="http://tmddn3410.dothome.co.kr/03AndroidHttp/getTest.php";

                // GET방식은 보낼데이터를 서버뒤에 붙혀서 보내는 방식
                // 단, URL에는 특수문자나 한글은 붙일 수 없음. 그래서 특수문자와 한글을 URL에서 사용하도록 암호화(변환: 인코딩)
                try {
                    title= URLEncoder.encode(title,"utf-8");
                    message= URLEncoder.encode(message,"utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                String getUrl= severUrl + "?title=" + title + "&msg=" + message;

                // 서버와 연결

                try {
                    URL url = new URL(getUrl);

                    // URL 객체는 Http 통신용 객체가 아니여서 GET, POST 방식에 대한 설정기능이 없음.
                    // 그래서 Http 통신을 관장하는 별도 객체 소환
                    HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");// 반드시대문자
                    connection.setDoInput(true);
                    connection.setUseCaches(false);

                    //보낼데이터를 OutputStream으로 보내야 하지만 GET 방식은 주소 URL뒤에
                    //붙여서 연결하기에 별도의 OutputStream작업이 필요하지 않음.

                    // 서버에서 응답한 echo 글씨는 읽어와야하기에 InputStream작업이 필요
                    InputStream is = connection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader reader = new BufferedReader(isr);

                    StringBuffer buffer = new StringBuffer();
                    while (true){
                        String line = reader.readLine();
                        if (line==null) break;
                        buffer.append(line+"\n");
                    }

                    //별도 Thread는 UI작업 불가
                    runOnUiThread( ()-> binding.tv.setText(buffer.toString()) );

                }catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();

    }

    void clickPost(){
        new Thread(){
            @Override
            public void run() {
                String title = binding.etTitle.getText().toString();
                String message = binding.etMsg.getText().toString();

                //POST방식으로 데이터를 보낼 서버 주소\
                String severUrl="http://tmddn3410.dothome.co.kr/03AndroidHttp/postTest.php";

                try {
                    URL url = new URL(severUrl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    connection.setUseCaches(false);

                    // 보낼데이터를 key=value 형태로 포맷팅하기
                    String data = "title="+title+"&msg"+message;

                    // 데이터를 OutputStream을 통해 보내기
                    OutputStream os = connection.getOutputStream();
                    OutputStreamWriter writer = new OutputStreamWriter(os);

                    writer.write(data,0,data.length());
                    writer.flush();
                    writer.close();

                    // 서버로 부터 응답(echo)된 글씨 읽어오기
                    InputStream is = connection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader reader = new BufferedReader(isr);

                    StringBuffer buffer = new StringBuffer();
                    while (true){
                        String line = reader.readLine();
                        if (line==null) break;
                        buffer.append(line+"\n");
                    }

                    runOnUiThread( ()-> binding.tv.setText(buffer.toString()));

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }.start();
    }
}