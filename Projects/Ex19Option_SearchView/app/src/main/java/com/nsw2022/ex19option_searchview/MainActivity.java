package com.nsw2022.ex19option_searchview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // 옵션메뉴의 메뉴아이템 안에 있는 SearchView의 참조변수
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // 옵션 메뉴를 만들기 위해 onCrate 메소드가 실행된 후 자동으로 발동하는 콜백메소드


    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.option,menu);

        // menu 안에 있는 MenuItem의 SearchView를 찾아오기
        MenuItem menuItem=menu.findItem(R.id.menu_seach);
        searchView=(SearchView) menuItem.getActionView();

        // 힌트적용하기
        searchView.setQueryHint("닉네임을 입력하세요");

        // 소프트키보드의 검색버튼(돋보기 모양 버튼)을 클릭하는 것을 듣는 리스너 추가..
        // 검색창의 글시가 변경될때마다.. 반응..
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {//검색버튼을 눌렀을때 실행!
                Toast.makeText(MainActivity.this, query, Toast.LENGTH_SHORT).show();


                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {//글씨바뀔때 마다
                return false;
            }
        });



        return super.onCreateOptionsMenu(menu);

    }
}