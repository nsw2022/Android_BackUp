package com.nsw2022.es41activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ThridActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thrid);

        //제목줄의(ActionBar) 좌측에 뒤로가기 화사료 모양의 버튼 메뉴버튼 [ Up버튼 ] 보이도록, 설정
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    // 제목줄의 up버튼을 클릭했을때 자동으로 발동하는 콜백 메소드
    @Override
    public boolean onSupportNavigateUp() {
        //finish();
        onBackPressed(); // 디바이스의 뒤로가기 버튼을 클릭했을때 실행되는 메소드를 직접 호출
        return super.onSupportNavigateUp();
    }
}