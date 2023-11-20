package com.nsw2022.ex27recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //대량의 데이터
    ArrayList<Item> items = new ArrayList<Item>();

    RecyclerView recyclerView;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //테스트 목적으로 대량의 데이터를 추가 - 실무에서는 DB나 서버에 있는 데이터를 읽어옴.
        items.add( new Item("sam", "Hello android.")  );
        items.add( new Item("robin", "Nice to meet you.")  );
        items.add( new Item("kim", "Good day.")  );
        items.add( new Item("park", "안녕하세요. 반가워요.")  );
        items.add( new Item("lee", "만나서 반가워요.\n잘 지내봅시다.\nGood afternoon.")  );
        items.add( new Item("hong", "Hi, RecyclerView")  );
        items.add( new Item("sam", "Hello android.")  );
        items.add( new Item("robin", "Nice to meet you.")  );
        items.add( new Item("kim", "Good day.")  );
        items.add( new Item("park", "안녕하세요. 반가워요.")  );
        items.add( new Item("lee", "만나서 반가워요.\n잘 지내봅시다.\n새로운 줄")  );
        items.add( new Item("hong", "Hi, RecyclerView")  );


        recyclerView=findViewById(R.id.recyclerview);
        adapter=new MyAdapter(this,items);
        recyclerView.setAdapter(adapter);



    }
}