package com.nsw2022.ex24spinner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Spinner spinner, spinner2;
    ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner=findViewById(R.id.spinner);

        //스피너의 아이템 1개를 선택했을때 반응하는 리스너 객체 생성 및 설정
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // 3번째 파라미터 i가 : 선택한 아이템의 position
                Toast.makeText(MainActivity.this, i+"", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // 내가 원하는 모양으로 item의 레이아웃을 지정하려면. Adapter를 직접만들어 설정해야함.
        spinner2=findViewById(R.id.spinner2);
        adapter=ArrayAdapter.createFromResource(this,R.array.city,R.layout.spinner_selected_item);
        spinner2.setAdapter(adapter);

        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        //드롭다운된 아이템들의 위치를 조정할 수 있음
        spinner2.setDropDownVerticalOffset(120);
        spinner2.setDropDownHorizontalOffset(50);

    }
}