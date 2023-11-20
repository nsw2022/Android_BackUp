package com.nsw2022.ex35drawernavigation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView nav;

    ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout=findViewById(R.id.drawer_layout);
        nav=findViewById(R.id.nav);

        //제목줄의 왼쪼겡 붙어 있는 삼선아이콘모양(행버거 아이콘) 매뉴를 통해 Drawer를 열고 닫기
        //1. 삼선아이콘 버튼(메뉴) 만들기
        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close);
        //2. 삼선아이콘이 버티도록 버튼을 동기화 하도록 요청
        drawerToggle.syncState();
        //3. 삼선아이콘 모양과 화살표아이콘 모양이 자동으로 변환되도록..
        drawerLayout.addDrawerListener(drawerToggle);

        // 네비게이션뷰의 항목(아이템)메뉴를 선택했을때 반응하는 리스너
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.menu_aa:
                        Toast.makeText(MainActivity.this, "aa", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.menu_bb:
                        Toast.makeText(MainActivity.this, "bb", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.menu_tt:
                        Toast.makeText(MainActivity.this, "tt", Toast.LENGTH_SHORT).show();
                        break;

                }

                //Drawer 뷰를 닫기
                drawerLayout.closeDrawer(nav);


                return false;
            }
        });

        //네비게이션 뷰 안에 있는 HeaderView 안에있는 Circle ImageView 찾아오기
        CircleImageView civ = nav.getHeaderView(0).findViewById(R.id.header_iv);
        civ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                civ.setImageResource(R.drawable.crew_zoro);
            }
        });

    }
}