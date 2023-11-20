package com.nsw2022.zzz_recylerviewtwo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Item> items = new ArrayList<Item>();

    MyAdapter adapter;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        items.add(new Item(R.drawable.img01, R.drawable.ic_baseline_favorite_border_24, "워드프로세스 홈페이지 제작해드립니다.", "2,000,000"));
        items.add(new Item(R.drawable.img02, R.drawable.ic_baseline_favorite_border_24, "앱 웹서비스 기횝해드립니다.", "1,500,000"));
        items.add(new Item(R.drawable.img03, R.drawable.ic_baseline_favorite_border_24, "사업자를위한 맞춤형.", "3,000,000"));
        items.add(new Item(R.drawable.img04, R.drawable.ic_baseline_favorite_border_24, "Smart Eco", "8,000,000"));
        items.add(new Item(R.drawable.img05, R.drawable.ic_baseline_favorite_border_24, "SEO최적화 반응형 홈페이지 제작해드립니다.", "6,000,000"));
        items.add(new Item(R.drawable.img06, R.drawable.ic_baseline_favorite_border_24, "앱 웹서비스 기횝해드립니다.", "1,500,000"));
        items.add(new Item(R.drawable.img07, R.drawable.ic_baseline_favorite_border_24, "사업자를위한 맞춤형.", "3,000,000"));
        items.add(new Item(R.drawable.img08, R.drawable.ic_baseline_favorite_border_24, "Smart Eco", "8,000,000"));
        items.add(new Item(R.drawable.img09, R.drawable.ic_baseline_favorite_border_24, "Smart Eco", "8,000,000"));
        items.add(new Item(R.drawable.img10, R.drawable.ic_baseline_favorite_border_24, "워드프로세스 홈페이지 제작해드립니다.", "2,000,000"));



        recyclerView=findViewById(R.id.recyclerView);
        adapter=new MyAdapter(this,items);
        recyclerView.setAdapter(adapter);

    }
}