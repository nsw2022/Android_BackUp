package com.nsw2022.zzz_viewpagerulex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ListAdapter;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ViewPager2 pager;
    MyFagmentAdapter adapter;

    TabLayout tabLayout;
    String[] tabTitles = new String[]{"인기","팔로잉","사진","집들이","노하우"};


    ArrayList<Item> items=new ArrayList<>();

    GridView gridView;
    ArrayAdapter arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pager=findViewById(R.id.pager);
        adapter=new MyFagmentAdapter(this);
        pager.setAdapter(adapter);


        tabLayout=findViewById(R.id.tab_layout);

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, pager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(tabTitles[position]);
            }
        });

        tabLayoutMediator.attach();

        items.add(new Item("쇼핑하기",R.drawable.ch_chopa));
        items.add(new Item("오시즌워크",R.drawable.ch_luffy));
        items.add(new Item("N평집들이",R.drawable.ch_nami));
        items.add(new Item("공간별사진",R.drawable.ch_sandi));
        items.add(new Item("리모델링",R.drawable.ch_usoup));
        items.add(new Item("간식특가",R.drawable.ch_zoro));

//        gridView=findViewById(R.id.gridview);
//        arrayAdapter=new ArrayAdapter(this,R.layout.grideview_item,items);
//        gridView.setAdapter(arrayAdapter);




    }
}