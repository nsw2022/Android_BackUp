package com.nsw2022.ex43activity3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {

    EditText etTitle;
    EditText etPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        etTitle=findViewById(R.id.et_title);
        etPrice=findViewById(R.id.et_price);

        findViewById(R.id.btn).setOnClickListener(view -> {

            //EdText에 써있는 글씨를 가져오기
            String title=etTitle.getText().toString();
            double price=Double.parseDouble(etPrice.getText().toString());

            // 나에게온 택배기사님 소환하여 MainActivity 로 보낼 데이터를 추가데이터 넣기
            Intent intent = getIntent();
            intent.putExtra("title",title); // String
            intent.putExtra("price",price); // Double

            // 결과를 주었다고 명시!! [ 이게결과!~!!! ]
            setResult( RESULT_OK,intent);

            // 작성완료했으니 EditActivity를 종료
            finish();

        });
    }
}