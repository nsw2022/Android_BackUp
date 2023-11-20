package com.nsw2022.spinnerpritice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import android.os.Bundle;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {
    Spinner spinner, spinner2;
    ArrayAdapter adapter, adapter2;
    Button btn;

    int[] Rsijuso = new int[]{

            R.array.seoul,
            R.array.busan,
            R.array.in,
            R.array.gyeonggi,
            R.array.gangwon
    };

    int[] Rgujuso = new int[]{
            R.array.test,
            R.array.seoul_gu,
            R.array.busan_gu,
            R.array.in_gu,
            R.array.gyeonggi_gu,
            R.array.gyeonggi_gu,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn=findViewById(R.id.btn);

        spinner=findViewById(R.id.spinner);
        spinner2=findViewById(R.id.spinner2);


        adapter=ArrayAdapter.createFromResource(this,R.array.si,R.layout.spinner_ic_itema);
        spinner.setAdapter(adapter);

        adapter2=ArrayAdapter.createFromResource(this,R.array.test,R.layout.spinner_ic_itema);
        spinner2.setAdapter(adapter2);

        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        adapter2.setDropDownViewResource(R.layout.spinner_dropdown_item);

        spinner.setDropDownVerticalOffset(100);
        spinner.setDropDownHorizontalOffset(0);

        spinner2.setDropDownVerticalOffset(100);
        spinner2.setDropDownHorizontalOffset(0);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                adapter=ArrayAdapter.createFromResource(MainActivity.this,Rgujuso[i],R.layout.spinner_ic_itema);
                spinner2.setAdapter(adapter);
                adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(MainActivity.this,btn);
                MenuInflater menuInflater = getMenuInflater();
                menuInflater.inflate(R.menu.popup,popupMenu.getMenu());
            }
        });





    }
}