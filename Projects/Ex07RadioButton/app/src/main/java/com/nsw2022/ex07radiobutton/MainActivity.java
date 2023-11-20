package com.nsw2022.ex07radiobutton;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // RadioButton은 RadioGroup단위로 제어함.
    RadioGroup rg;
    Button btn;
    TextView tv;
    RatingBar ratingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rg=findViewById(R.id.rg);
        btn=findViewById(R.id.btn);
        tv=findViewById(R.id.tv);

        //버튼 클릭시에 선택된 RadioButton의 글씨를 얻어와서 TextView에 보여주기
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //체크가 되어있는 RadioButton의 id찾기
                int id = rg.getCheckedRadioButtonId();

                RadioButton rb=findViewById(id);
                tv.setText(rb.getText().toString());

            }
        });

        // RadioBotton의 선택 상태가 바뀔때마다 반응하는 리스너는 개별로 붙이지 않고,
        // RadioGroup 용 리스너를 생성하여 적용함.
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                // 두번째 파라미터 i : 현재 선택된 RadioBotton의 id값

                RadioButton rb = findViewById(i);
                tv.setText(rb.getText().toString());

            }
        });

        ratingBar=findViewById(R.id.ratingbar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                // 두번째 파라미터 float v : 평점 값
                // 세번째 파라미터 boolean b : 사용자에 의해 평점이 변경되었는지 여부 true/false

                tv.setText( v+"점");


            }
        });


    }
}