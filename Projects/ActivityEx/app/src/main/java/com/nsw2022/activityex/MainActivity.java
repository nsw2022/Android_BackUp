package com.nsw2022.activityex;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
   RecyclerView recyclerView;
   MyAdapter adapter;
   ArrayList<Item> items= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.btn).setOnClickListener(view -> {
            Intent intent=new Intent(this,SecondActivity.class);
            activityResultLauncher.launch(intent);


        });

        recyclerView=findViewById(R.id.recyclerview);
        adapter=new MyAdapter(this,items);
        recyclerView.setAdapter(adapter);
        adapter.notifyItemInserted(items.size()-1);
        adapter.notifyDataSetChanged();




    }//onCreat

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode()==RESULT_OK){
                Intent intent = result.getData();

                String name = intent.getStringExtra("name");
                String nickname = intent.getStringExtra("nickname");
                String title = intent.getStringExtra("title");
                String content = intent.getStringExtra("content");

                items.add(new Item(name,nickname,title,content));



                adapter.notifyDataSetChanged();


            }else{
                Toast.makeText(MainActivity.this, "test", Toast.LENGTH_SHORT).show();
            }


        }
    });

}