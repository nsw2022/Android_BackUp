package com.nsw2022.ex33toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        //툴바를 ActionBar로 대체하겠다고 액티비티에 설정
        setSupportActionBar(toolbar);

        //제목줄 변경해보기 - 툴바에게 말하는 것이 아니라 액션바에게 요청해야함
        ActionBar actionBar = getSupportActionBar();
        //actionBar.setTitle("툴바");
        //툴바 안에 가운데 정렬한 TextView로 제목글씨를 표시했으니.. 원래 액션바의 제목글씨를 보이지 않도록
        actionBar.setDisplayShowTitleEnabled(false);





    }

    //옵션 메뉴를 만들기위해 자동으로 실행되는 콜백 메소드


    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.option,menu);



        return super.onCreateOptionsMenu(menu);
    }
}