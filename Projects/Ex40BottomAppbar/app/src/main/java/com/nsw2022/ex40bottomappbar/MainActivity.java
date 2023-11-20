package com.nsw2022.ex40bottomappbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    BottomAppBar bad;
    FloatingActionButton fab;

    boolean isCenter = true;//FAB가 가운데 있는지 여부

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //BottomAppBar를 ActionBar로 대체하겠다고 설정
        bad=findViewById(R.id.bab);
        setSupportActionBar(bad);

        //제목줄 좌측에 화살표[ 업버튼 ] 표시
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //BAB는 제목글씨가 보이지 않습니다 설정한다하더라도..
        getSupportActionBar().setTitle("aaa");

        fab=findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isCenter) bad.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_END);
                else bad.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_CENTER);

                isCenter= !isCenter;
            }
        });


    }////////////////////////////////////////////////////////////////

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.option,menu);
        return super.onCreateOptionsMenu(menu);
    }
}