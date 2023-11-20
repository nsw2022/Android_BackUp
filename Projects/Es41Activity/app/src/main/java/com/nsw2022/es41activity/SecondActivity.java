package com.nsw2022.es41activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

//Activity는 안드로이드의 4대 구성요소(Component) 는 반드시 AndroidManifest.xml에 등록해야만 동작함
public class SecondActivity extends AppCompatActivity {
    //엑티비티가 보여줄 View 를 설정하는 코드를 작성하기 위해 자동으로 실행되는 메소드
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //엑티비티가 보여줄 View를 설정 - 별도의 xml 문서로 레이아웃을 작성하는 것이 훨씬더 편함.
        setContentView( R.layout.activity_second );




    }
}
