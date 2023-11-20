package com.nsw2022.exappbarlayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    ViewPager2 pager;
    MyPagerAdapter adapter;

    TabLayout tabLayout;
    String[] tabTitle = new String[]{"HOME","SHOP","MAP"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pager=findViewById(R.id.pager);
        adapter=new MyPagerAdapter(this);
        pager.setAdapter(adapter);

        // Tablayout과 Viwpager를 연동하기
        tabLayout=findViewById(R.id.tab_layout);

        //둘 사이를 연동하기 위해 중재하는 역할의 중재자( Mediator) 객체
        TabLayoutMediator tabLayoutMediator=new TabLayoutMediator(tabLayout, pager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                //탭의 개수만틈 호출됨
                //첫번째파라미터 : 자동으로만들어진 tab객체
                //두전째파라미터 : 만들어질위치 position
                tab.setText(tabTitle[position]);

            }
        });

        // 중재자에게 둘이 붙이도록 요청
        tabLayoutMediator.attach();

    }//onCreat

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.option,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //옵션메뉴 항목(아이템)을 선택했을때 자동발동하는 콜백 메소드
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {



        switch (item.getItemId()){
            case  R.id.menu_search:
                Toast.makeText(this, "SEARCH", Toast.LENGTH_SHORT).show();
                break;

            case  R.id.menu_aa:
                Toast.makeText(this, "AA", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menu_bb:
                Toast.makeText(this, "BB", Toast.LENGTH_SHORT).show();
                break;

        }

        return super.onOptionsItemSelected(item);
    }
}