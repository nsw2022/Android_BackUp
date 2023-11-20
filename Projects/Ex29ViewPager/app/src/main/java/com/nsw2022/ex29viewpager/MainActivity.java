package com.nsw2022.ex29viewpager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Integer> items=new ArrayList<Integer>();

    ViewPager2 pager;
    MyPagerAdapter pagerAdapter;

    Button btnPrev, btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //페이지 별로 보여줄 이미지들.

        items.add(R.drawable.bg_one01);
        items.add(R.drawable.bg_one02);
        items.add(R.drawable.bg_one03);
        items.add(R.drawable.bg_one04);
        items.add(R.drawable.bg_one05);
        items.add(R.drawable.bg_one06);
        items.add(R.drawable.bg_one07);
        items.add(R.drawable.bg_one08);
        items.add(R.drawable.bg_one09);
        items.add(R.drawable.bg_one10);

        pager=findViewById(R.id.pager);
        pagerAdapter=new MyPagerAdapter(this,items);
        pager.setAdapter(pagerAdapter);

        //페이지를 이동 버튼
        btnPrev=findViewById(R.id.btn_prev);
        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //현재번째 페이지 번호의 앞번호를 알아내기
                int position=pager.getCurrentItem()-1;
                //특정 페이지 위치로 이동
                pager.setCurrentItem(position);
            }
        });

        btnNext=findViewById(R.id.btn_next);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pager.setCurrentItem(pager.getCurrentItem() + 1);
            }
        });

    }
}