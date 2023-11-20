package com.nsw2022.ex75butterknife;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

public class MainActivity extends AppCompatActivity {

    // ButterKinfe : findViewbyId() 가 번거오워서 사용하는 라이브러리

    @BindView(R.id.tv) TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 버터나이프와 이 액티비티의 xml을 연동
        ButterKnife.bind(this);

        tv.setText("Nice to meet you");
    }

    @OnClick(R.id.btn)
    void clickBtn(){
        tv.setText("Good ButterKnife");
    }

    @OnLongClick(R.id.btn)
    boolean longClickBtn(){
        Toast.makeText(this, "롱~~~클릭", Toast.LENGTH_SHORT).show();
        return true;
    }

}