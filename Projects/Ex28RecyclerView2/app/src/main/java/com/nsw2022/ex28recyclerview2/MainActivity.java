package com.nsw2022.ex28recyclerview2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //대량의 데이터
    ArrayList<Item> items=new ArrayList<>();
    RecyclerView recyclerView;
    MyAdapter adapter;

    ImageView btnLinear;
    ImageView btnGrid;

    Button btnAdd, btnDelete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //대량의 데이터를 추가 - 실무에서는 DB나 서버에서 데이터를 받아옴. 지금은 테스트 목적


        items.add(new Item("루피","밀짚모자 해적단 두목",R.drawable.crew_luffy,R.drawable.bg_one01));
        items.add(new Item("조로","밀짚모자 해적단 부선장",R.drawable.crew_zoro,R.drawable.bg_one02));
        items.add(new Item("나미","밀짚모자 해적단 향해사",R.drawable.crew_nami,R.drawable.bg_one03));
        items.add(new Item("상디","밀짚모자 해적단 요리사",R.drawable.crew_sanji,R.drawable.bg_one04));
        items.add(new Item("우솝","밀짚모자 해적단 저격수",R.drawable.crew_usopp,R.drawable.bg_one05));
        items.add(new Item("쵸파","밀짚모자 해적단 의사",R.drawable.crew_chopper,R.drawable.bg_one06));
        items.add(new Item("니코로빈","밀짚모자 해적단 역사학자",R.drawable.crew_nicorobin,R.drawable.bg_one07));
        items.add(new Item("겨울왕국","자매들의 눈싸움",R.drawable.bg_one09,R.drawable.winter));
        items.add(new Item("모아나","바다의정령 모아나를 선택",R.drawable.bg_one10,R.drawable.moana));

        //리사이클러뷰와 아답터 연결
        recyclerView=findViewById(R.id.recyclerView);
        adapter=new MyAdapter(this,items);
        recyclerView.setAdapter(adapter);

        // 리사이클러뷰의 배치관리자 변경해보기
        btnLinear=findViewById(R.id.btn_linear);
        btnLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 배치관리자를 LinearLaoutManager로 설정해보기
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false);
                recyclerView.setLayoutManager(linearLayoutManager);
            }
        });

        btnGrid=findViewById(R.id.btn_grid);
        btnGrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StaggeredGridLayoutManager layoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);//한줄에 2칸씩
                recyclerView.setLayoutManager(layoutManager);
            }
        });

        btnAdd=findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //새로운 아이템 추가하는 것은 리사이클러뷰나 아답터에 추가하는 것이아님!!
                //대량의 데이터를 가진 리스트에 추가하는 것임
                items.add(0,new Item("NEW","새로운선원",R.drawable.bg_one07,R.drawable.crew_nami));
                // ** 아답터에게 새로운 데이터가 추가되었다고 공지해 줘야 화면이 갱신됨
                //adapter.notifyDataSetChanged();//단 이렇게하면 화면 전체를 모두 갱신하기에 비효율적임.

                //adapter.notifyItemInserted( items.size()-1 );
                adapter.notifyItemInserted(0);

                recyclerView.scrollToPosition(0);//스크롤 위치를 0번 인덱스 위치로 이동!
            }
        });

        btnDelete=findViewById(R.id.btn_delete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 데이터를 가진 리스트에서 항목을 삭제하는것임
                items.remove(0);
                //데이터가 변경된것을 아답터에게 공지
                adapter.notifyItemRemoved(0);


            }
        });

    }
}