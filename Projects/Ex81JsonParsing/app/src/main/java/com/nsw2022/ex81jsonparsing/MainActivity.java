package com.nsw2022.ex81jsonparsing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.os.Bundle;

import com.nsw2022.ex81jsonparsing.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    ArrayList<Item> items= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btn.setOnClickListener(v->clickBtn());
        binding.btn2.setOnClickListener(v->clickBtn2());
    }

    void clickBtn2(){
        AssetManager assetManager= getAssets();

        try {
            InputStream is= assetManager.open("jsons/bbb.json");
            InputStreamReader isr= new InputStreamReader(is);
            BufferedReader reader= new BufferedReader(isr);

            StringBuffer buffer= new StringBuffer();
            while (true){
                String line= reader.readLine();
                if(line==null) break;
                buffer.append(line+"\n");
            }

            // json array 문자열을 파싱
            JSONArray jsonArray= new JSONArray(buffer.toString());

            for(int i=0; i<jsonArray.length(); i++){
                JSONObject jo= jsonArray.getJSONObject(i);

                int no= jo.getInt("no");
                String name= jo.getString("name");

                JSONObject addr= jo.getJSONObject("addr");
                String nation= addr.getString("nation");
                String city= addr.getString("city");

                Address a= new Address(nation, city);
                Item item= new Item(no, name, a);
                items.add(item);
            }

            binding.tv.setText( items.size() + ""  );

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    void clickBtn(){
        // assets 폴더안에 json 파일을 위치.

        // json문서를 분석하기 위해 Assets폴더 관리자 소환
        AssetManager assetManager= getAssets();

        // assets/jsons/aaa.json 파일을 읽기위한 InputStream

        try {
            InputStream is= assetManager.open("jsons/aaa.json");
            InputStreamReader isr= new InputStreamReader(is);
            BufferedReader reader= new BufferedReader(isr);

            StringBuffer buffer= new StringBuffer();
            while (true){
                String line= reader.readLine();
                if(line==null) break;
                buffer.append(line+"\n");
            }

            // json문자열을 분석!
            // json문자열을 json객체로 변환
            JSONObject jo= new JSONObject(buffer.toString());
            int no= jo.getInt("no");
            String name= jo.getString("name");
            String message= jo.getString("msg");

            binding.tv.setText(no+" : " + name +" - " + message);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}