package com.nsw2022.ex25listviewcustom;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // 대량의 데이터들
    ArrayList<Item>items = new ArrayList<Item>();

    ListView listView;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //대량의 데이터들 추가 - 실무에서는 DB나 서버에서 데이터를 받아옴
        items.add( new Item("전현무","대한민국",R.drawable.flag_korea) );
        items.add(new Item("기욤패트리","캐나다",R.drawable.flag_canada));
        items.add(new Item("타일러","미국",R.drawable.flag_usa));
        items.add(new Item("알베르토","이탈리아",R.drawable.flag_italy));
        items.add(new Item("타쿠야","일본",R.drawable.flag_japan));
        items.add(new Item("줄리안","벨기에",R.drawable.flag_belgium));
        items.add(new Item("샘 오취리","가나",R.drawable.flag_ghana));

        listView=findViewById(R.id.listview);
        adapter=new MyAdapter(this,items);
        listView.setAdapter(adapter);


    }
}