package com.nsw2022.ex16optionmenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // Option Menu는 뷰가 아니기에 activity_main.xml에서 만들지 않음.

    // Activity는 기본적으로 이미 Option Menu를 가지고 있음. 다만, 그안에 MenuItem(메뉴항목)이
    // 없기때문에 보이지 않을 뿐임
    // Menu Item(메뉴항목)을 만들어내기 위해 OnCreate()메소드 실행이 끝나면
    // 자동으로 실행되는 메소드가 발동함. - 이미 activity에 존재하는 메소드임. 이름 Override 함.


    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {

        // 여기서 menu객체에 MenuItem(메뉴항목)을 추가,,
        // 메뉴항목들을 XML 언어를 이용하여 설계하고 자바에서 이를 객체로 만들어 추가하는 방법이 선호됨.
        // res폴더 안에 menu 폴더를 만들고 그 안에 xml문법으로 MenuItem 설계

        // res>>menu>>option.xml 문서를 읽어와서 자바의 Menu객체로 만들어주는(부풀리는 - inflate) 능력을 가진 클래스 객체
        // MenuInflater 클래스 객체를 이용하여 메뉴객체를 생성함.
        // MenuInflater를 이미 Context(운영체제 대리인) 객체 안에 존재하고 있기에 소환만 하면 됨.
        MenuInflater menuInflater= this.getMenuInflater();
        menuInflater.inflate(R.menu.option, menu );


        return super.onCreateOptionsMenu(menu);


    }

    // 액티비티 클래스안에 Option Menu의 Iteam을 선택했을때 반응하는 콜백메소드가 이미 존재함.


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        // 선택된 Iteam의 id를 얻어오기
        int id= item.getItemId();

        switch (id){
            case R.id.menu_search:
                Toast.makeText(this,"SERCH",Toast.LENGTH_SHORT).show();
                break;

            case R.id.menu_add:
                Toast.makeText(this,"ADD",Toast.LENGTH_SHORT).show();
                break;

            case R.id.menu_help:
                Toast.makeText(this,"HELP",Toast.LENGTH_SHORT).show();
                break;
        }


        return super.onOptionsItemSelected(item);

    }
}