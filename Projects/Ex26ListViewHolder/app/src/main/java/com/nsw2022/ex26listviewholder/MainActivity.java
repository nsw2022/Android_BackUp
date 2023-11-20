package com.nsw2022.ex26listviewholder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    ArrayList<String> items=new ArrayList<String>();

    ListView listView;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //대량의 데이터 추가
        items.add("aaa");
        items.add("bbb");
        items.add("ccc");
        items.add("ddd");
        items.add("eee");
        items.add("aaa");
        items.add("bbb");
        items.add("ccc");
        items.add("ddd");
        items.add("eee");

        listView=findViewById(R.id.listview);
        adapter=new MyAdapter(this,items);
        listView.setAdapter(adapter);






    }
}