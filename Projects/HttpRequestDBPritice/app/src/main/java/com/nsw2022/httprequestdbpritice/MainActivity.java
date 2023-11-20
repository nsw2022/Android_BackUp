package com.nsw2022.httprequestdbpritice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.nsw2022.httprequestdbpritice.databinding.ActivityMainBinding;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnSave.setOnClickListener(v->clickSave());
        binding.btnLoad.setOnClickListener(v->clickLoad());
    }

    void clickSave(){
        new Thread(){
            @Override
            public void run() {
                String title = binding.etTitle.getText().toString();
                String message = binding.etMsg.getText().toString();

                String serverUrl = "http://tmddn3410.dothome.co.kr/03AndroidHttp/insertDB.php";

                try {
                    URL url = new URL(serverUrl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    connection.setUseCaches(false);

                    


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    void clickLoad(){

    }
}