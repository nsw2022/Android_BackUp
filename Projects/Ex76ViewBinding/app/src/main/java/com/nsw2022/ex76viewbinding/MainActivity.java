package com.nsw2022.ex76viewbinding;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.nsw2022.ex76viewbinding.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // ViewBinding : findViewVyId()의 번거로움과 성능이슈를 해소해주는 기능 - 라이브러리 아님
    // 다만, 기본적으로는 이 기능이 off 되어 있어서 켜줘야 함. Build.gradle 에서

    // xml 파일의 이름을 기반으로 참조변수들이 알아서 만들어지는 Binding 클래스가 존재함
    ActivityMainBinding binding;

    ArrayList<Item> items=new ArrayList<>();
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        items.add(new Item(R.drawable.sydney,"sydney"));
        items.add(new Item(R.drawable.paris,"paris"));
        items.add(new Item(R.drawable.sydney,"sydney"));
        items.add(new Item(R.drawable.paris,"paris"));
        items.add(new Item(R.drawable.sydney,"sydney"));
        items.add(new Item(R.drawable.paris,"paris"));
        items.add(new Item(R.drawable.sydney,"sydney"));
        items.add(new Item(R.drawable.paris,"paris"));
        items.add(new Item(R.drawable.sydney,"sydney"));
        items.add(new Item(R.drawable.paris,"paris"));
        items.add(new Item(R.drawable.sydney,"sydney"));
        items.add(new Item(R.drawable.paris,"paris"));
        items.add(new Item(R.drawable.sydney,"sydney"));
        items.add(new Item(R.drawable.paris,"paris"));


        //activity_main.xml 과 연결되는 객체 생성
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        adapter=new MyAdapter(this,items);
        binding.recyclerView.setAdapter(adapter);


        binding.btn.setOnClickListener(v->{
            binding.tv.setText("Nice to Meet you");
        });

        binding.btn2.setOnClickListener(v->{
            binding.tv2.setText(binding.et.getText().toString());
        });

    }


}