package com.nsw2022.recyclerpractice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    ArrayList<Item> items = new ArrayList<>();
    RecyclerView recyclerView;
    MyAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        items.add(new Item("루피","밀짚모자 해적단 두목",R.drawable.crew_luffy,R.drawable.bg_one01));
        items.add(new Item("조로","밀짚모자 해적단 부선장",R.drawable.crew_zoro,R.drawable.bg_one02));
        items.add(new Item("나미","밀짚모자 해적단 향해사",R.drawable.crew_nami,R.drawable.bg_one03));
        items.add(new Item("상디","밀짚모자 해적단 요리사",R.drawable.crew_sanji,R.drawable.bg_one04));
        items.add(new Item("우솝","밀짚모자 해적단 저격수",R.drawable.crew_usopp,R.drawable.bg_one05));
        items.add(new Item("쵸파","밀짚모자 해적단 의사",R.drawable.crew_chopper,R.drawable.bg_one06));
        items.add(new Item("니코로빈","밀짚모자 해적단 역사학자",R.drawable.crew_nicorobin,R.drawable.bg_one07));
        items.add(new Item("겨울왕국","자매들의 눈싸움",R.drawable.bg_one09,R.drawable.winter));
        items.add(new Item("모아나","바다의정령 모아나를 선택",R.drawable.bg_one10,R.drawable.moana));

        recyclerView=findViewById(R.id.recycler_layout);
        adapter=new MyAdapter(this,items);
        recyclerView.setAdapter(adapter);


    }
}