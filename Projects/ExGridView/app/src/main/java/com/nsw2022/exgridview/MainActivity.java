package com.nsw2022.exgridview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> datas=new ArrayList<>();

    ArrayAdapter adapter;
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        datas.add( "aaa" );
        datas.add( "bbb" );
        datas.add( "ccc" );
        datas.add( "ddd" );
        datas.add( "eee" );
        datas.add( "fff" );
        datas.add( "ggg" );

        gridView=findViewById(R.id.gridview);
        adapter=new ArrayAdapter(this,R.layout.gridview_item,datas);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String data=datas.get(i);
                Toast.makeText(MainActivity.this, data, Toast.LENGTH_SHORT).show();
            }
        });
    }
}