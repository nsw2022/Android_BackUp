package com.nsw2022.httprequestprtice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;

import com.nsw2022.httprequestprtice.databinding.ActivityMainBinding;

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
        binding= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnGet.setOnClickListener(v->clickGet());
        binding.btnPost.setOnClickListener(v->clickPost());
    }

    void clickGet(){
        new Thread(){
            @Override
            public void run() {

                String title=binding.etTitle.getText().toString();
                String message=binding.etMsg.getText().toString();

                String severUrl="http://tmddn3410.dothome.co.kr/03AndroidHttp/getTest.php";

                try {
                    title= URLEncoder.encode(title,"utf-8");
                    message=URLEncoder.encode(message,"utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                String getUrl=severUrl + "?title=" + title + "&msg=" + message;

                try {
                    URL url = new URL(getUrl);

                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setDoInput(true);
                    connection.setDoOutput(false);

                    InputStream is = connection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader reader= new BufferedReader(isr);
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

    void clickPost(){
        new Thread(){
            @Override
            public void run() {
                String title = binding.etTitle.getText().toString();
                String message = binding.etMsg.getText().toString();

                String severUrl="http://tmddn3410.dothome.co.kr/03AndroidHttp/postTest.php";

                try {
                    URL url = new URL(severUrl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    connection.setUseCaches(false);

                    String data = "title=" + title + "&msg=" + message;

                    OutputStream os= connection.getOutputStream();
                    OutputStreamWriter writer= new OutputStreamWriter(os);
                    writer.write(data,0,data.length());
                    writer.flush();
                    writer.close();

                    InputStream is = connection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader reader = new BufferedReader(isr);
                    StringBuffer buffer = new StringBuffer();

                    while (true){
                        String line = reader.readLine();
                        if (line==null) break;
                        buffer.append(line+"\n");
                    }

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