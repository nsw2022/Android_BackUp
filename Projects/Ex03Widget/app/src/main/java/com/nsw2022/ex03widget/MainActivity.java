package com.nsw2022.ex03widget;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //멤버변수 - XML에서 만든 View들을 참조하는 참조벼수 선언
    TextView tv;
    Button btn;
    EditText et;
    TextView tvResult;
    TextView ontoplf;

    int num = 0;
    // 점수를 더해줌
    int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // xml에서 만든 view들을 찾아서 참조하기
        tv = findViewById(R.id.tv);
        btn = findViewById(R.id.btn);
        et = findViewById(R.id.et);
        tvResult = findViewById(R.id.tv_result);



        // 문제 단어와 정답 영단어 배열
        String[] qs = new String[]{"사과", "집", "자동차"};
        String[] as = new String[]{"apple", "house", "car"};


        // 현재번째 문항 번호


        // 첫번째 문재 표시( qs배열의 0번방 문제)

        tv.setText(qs[num]);

        // 버튼 클릭햇을때 EditText의 쓴 영단어가 정답인지 확인하는 코드
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //버튼 클릭될때 마다 실행되는 메소드 - call back method

                String s=et.getText().toString();

                if( s.equalsIgnoreCase(as[num]) ){
                    tvResult.setText("정답");
                    score=score+10;
                    num++;
                    if (num>2) num=0;

                    tv.setText(qs[num]);
                    et.setText("");

                }else {
                    tvResult.setText("오답");
                }


            } //Onclick
        });//ClickListender




    }//On Creat
}

        /*
        //xml에서 만들어진 view 객체들을 찾아와서 참조변수에 대입.
        tv=findViewById(R.id.tv);
        btn=findViewById(R.id.btn);
        et=findViewById(R.id.et);

        //테스트로 TextView의 글씨를 이 java문서에서 변경해보기
        //tv.setText("Nice");

        //버튼이 클릭되었을 때 TextView의 글씨를 변경하기
        // 버튼(btn) 이 클릭되는 것을 듣는 리스너 객체 생성 및 설정
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//이 리스너가 감시하는 버튼이 클릭되면 자동 발동하는 메소드
                //이 영역안에 작성한 코드는 항상실행되는 것이 아니라. 버튼이 클릭되었을때 마다 실행됨.
                // tv.setText("Nice to meet you");


                // EditText에 써있는 글씨를 얻어와서 TextView에 보여주기.
                String s=et.getText().toString();
                tv.setText(s);

                //혹시 기존에 EditText에 있는 글씨를 지우고 싶다면..
                et.setText("");//빈 글씨를 설정하면 됨.

            }
        });


    }

         */
