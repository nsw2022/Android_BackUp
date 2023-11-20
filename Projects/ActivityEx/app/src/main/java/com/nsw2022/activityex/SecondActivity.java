package com.nsw2022.activityex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {
    EditText et_name, et_nickname,et_title,et_content;
    TextView tv_letter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        et_name=findViewById(R.id.et_name);
        et_nickname=findViewById(R.id.et_nickname);
        et_title=findViewById(R.id.et_title);
        et_content=findViewById(R.id.et_content);
        tv_letter=findViewById(R.id.tv_letter);

        findViewById(R.id.btn_ok).setOnClickListener(view -> {
            String name=et_name.getText().toString();
            String nickname=et_nickname.getText().toString();
            String title=et_title.getText().toString();
            String content=et_content.getText().toString();

            Intent intent = getIntent();
            intent.putExtra("name",name);
            intent.putExtra("nickname",nickname);
            intent.putExtra("title",title);
            intent.putExtra("content",content);

            setResult(RESULT_OK,intent);

            finish();

        });

    }
}