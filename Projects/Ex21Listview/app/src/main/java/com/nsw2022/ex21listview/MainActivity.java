package com.nsw2022.ex21listview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ListView listView;

    ListView listView2;
    // 대량의 데이터를 뷰로 적절한 view객체들로 만들어주는 아답터객체의 참조변수
    ArrayAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView=findViewById(R.id.listview);
        listView2=findViewById(R.id.listview2);
        // 리스트뷰 항목 뷰들의 모양을 원하는 모양의 View객체들로 만들어주는 아답터 객체 생성
        // 대량의 데이터가 xml에 있을때...
        adapter=ArrayAdapter.createFromResource(this, R.array.datas,R.layout.listview_item);
        listView2.setAdapter(adapter);

        //리스트뷰의 항목(Item)을 클릭하는 것을 듣는 리스너 객체 생성 및 설정
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // 세번째 파라미터 i값 : 아이템의 포션 index번호 - 0,1,2,3...

                // arrys,xml에 있는 문자열 배열 불러오기

                // res폴더 창고관리자 소환
                Resources res=getResources();

                // 창고관리자에게 arrys,xml에 문서 안에 있는 datas객체 라는 이름의 배열객체 불러오기
                String[] aaa= res.getStringArray(R.array.datas);


                Toast.makeText(MainActivity.this, aaa[i], Toast.LENGTH_SHORT).show();
            }
        });
    }
}