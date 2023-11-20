package com.nsw2022.ex42activity2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    Button btn;
    EditText etName,etAge;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn=findViewById(R.id.btn);
        etName=findViewById(R.id.et_name);
        etAge=findViewById(R.id.et_age);

        btn.setOnClickListener(view -> {

            //SecondActivity로 보낼 데이터를 EditText 로 부터 얻어오기
            String name=etName.getText().toString();

            int age = Integer.parseInt(etAge.getText().toString());

            // SecondActivity를 실행시켜주는 택배기사(Intent)객체 생성성
            Intent intent = new Intent(this,SecondActivity.class);

            //SecondActivity에 전달한 데이터를 택배기사(Intent)에게 추가하기
            intent.putExtra("name",name); // String
            intent.putExtra("age",age); // int


            startActivity(intent);

        });

    }
}