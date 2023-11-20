package com.nsw2022.ex80httprequestdb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import android.widget.Toolbar;

import com.nsw2022.ex80httprequestdb.databinding.ActivityMainBinding;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;



public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnSave.setOnClickListener(v->clickSave());
        binding.btnLoad.setOnClickListener(v->clickLoad());
    }

    void clickSave(){
        new Thread(){
            @Override
            public void run() {

                //서버에 보낼 데이터들
                String title= binding.etTitle.getText().toString();
                String message= binding.etMsg.getText().toString();

                String serverUrl="http://tmddn3410.dothome.co.kr/03AndroidHttp/insertDB.php";

                try {
                    URL url= new URL(serverUrl);
                    HttpURLConnection connection= (HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    connection.setUseCaches(false);

                    // 보낼 데이터들을 formatting [key=value]
                    String data= "title="+title + "&msg="+message;

                    OutputStream os= connection.getOutputStream();
                    OutputStreamWriter writer= new OutputStreamWriter(os);
                    writer.write(data, 0, data.length());
                    writer.flush();
                    writer.close();

                    InputStream is= connection.getInputStream();
                    InputStreamReader isr= new InputStreamReader(is);
                    BufferedReader reader= new BufferedReader(isr);

                    StringBuffer buffer= new StringBuffer();
                    while (true){
                        String line= reader.readLine();
                        if(line==null) break;
                        buffer.append(line+"\n");
                    }

                    runOnUiThread(()->{
                        binding.tvTest.setText(buffer.toString());
                        Toast.makeText(MainActivity.this, buffer+"성공", Toast.LENGTH_SHORT).show();
                    } );

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }.start();
    }

    void clickLoad(){
        // 서버 DB의 데이터들을 불러와서 보여주는 화면
        Intent intent = new Intent(this,BoardActivity.class);
        startActivity(intent);

    }
}