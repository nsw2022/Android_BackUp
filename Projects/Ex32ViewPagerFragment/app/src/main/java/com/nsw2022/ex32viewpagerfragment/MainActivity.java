package com.nsw2022.ex32viewpagerfragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    ViewPager2 pager;
    MyAdapter adapter;

    TabLayout tabLayout;
    //탭 버튼에 보여질 글씨들
    String[] tabTitles = new String[]{"PAGE1","PAGE2","PAGE3"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pager=findViewById(R.id.pager);
        adapter=new MyAdapter(this);
        pager.setAdapter(adapter);

        tabLayout=findViewById(R.id.tab_layout);

        //TabLayout과 ViewPager를 연동 - 이둘을 연동해주는 중재자(Mediator) 객체 필요
        TabLayoutMediator tabLayoutMediator= new TabLayoutMediator(tabLayout, pager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                // pager의 개수만큼 이 콜백메소드가 실행됨 - 이 곳에서 Tab의 글씨같은 것을 설정함.
                tab.setText(tabTitles[position]);
            }
        });

        //중재자가 객체에게 tabLayout과 ViewPager를 붙여달라고 요청!
        tabLayoutMediator.attach();

    }
}