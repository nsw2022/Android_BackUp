package com.nsw2022.zzz_recylerviewulex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Item> items=new ArrayList<Item>();

    RecyclerView recyclerView;
    MyAdpter adpter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        items.add(new Item(R.drawable.paris,(int)0.8,"8.0","(4234)","85,000"));
        items.add(new Item(R.drawable.sydney,(int)0.4,"5.0","(9999+)","185,000"));
        items.add(new Item(R.drawable.bg,(int)0.6,"0.6","(999+)","985,000"));
        items.add(new Item(R.drawable.moana03,(int)1,"10.0","(1174)","1,000,000"));

        recyclerView=findViewById(R.id.recyclerview);
        adpter=new MyAdpter(this,items);
        recyclerView.setAdapter(adpter);


    }
}