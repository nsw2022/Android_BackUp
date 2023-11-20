package com.nsw2022.ex04imageview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    // xml에서 만든 View들을 참조하는 참조변수 - 리모컨
    ImageView iv;
    Button btnAus, btnBel, btnCan, btnKor;

    //이미지의 resID(int형)를 여러개 가진 배열
    int[] imgs= new int[]{
            R.drawable.flag_australia,
            R.drawable.flag_belgium,
            R.drawable.flag_canada,
            R.drawable.flag_china,
            R.drawable.flag_france,
            R.drawable.flag_germany,
            R.drawable.flag_ghana,
            R.drawable.flag_italy,
            R.drawable.flag_japan,
            R.drawable.flag_nepal,
            R.drawable.flag_russia,
            R.drawable.flag_usa,
            R.drawable.flag_korea
    };

    //이미지의 인덱스 번호
    int index=0;

    ImageView iv2;
    Button btnRandom;

    Button btnRead;
    TextView tvNation;

    // 이미지뷰에 설정한 tag 값들 배열
    String[] tags= new String[]{
            "호주","벨기에","캐나다","중국","프랑스","독일","가나","이탈리아","일본","네팔","러시아","미국","대한민국"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 참조변수에 xml의 뷰들을 찾아서 대입해주기
        iv= findViewById(R.id.iv);
        btnAus= findViewById(R.id.btn01);
        btnBel= findViewById(R.id.btn02);
        btnCan= findViewById(R.id.btn03);
        btnKor= findViewById(R.id.btn04);

        btnAus.setOnClickListener( listener );
        btnBel.setOnClickListener( listener );
        btnCan.setOnClickListener( listener );
        btnKor.setOnClickListener( listener );

        //이미지뷰를 클릭했을때 국기 이미지를 차례대로..다음 국기로 변경해보기.
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv.setImageResource( imgs[index] );
                index++;
                if(index>imgs.length-1){
                    index=0;
                }

            }
        });


        //버튼 클릭시에 랜덤 국기 이미지 보이기 -일종의 복불복 게임
        iv2= findViewById(R.id.iv2);
        btnRandom= findViewById(R.id.btn_random);

        btnRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Random rnd= new Random(); //랜덤값 만드는 능력을 가진 객체

                int n= rnd.nextInt(imgs.length); //0~12
                iv2.setImageResource( imgs[n] );

                //이미지뷰에 국기 이미지에 해당하는 국가 tag를 저장
                iv2.setTag(tags[n]);
            }
        });


        // 버튼 클릭시에 iv2 의 랜덤한 국기 이미지의 국가명을 TextView에 출력
        btnRead= findViewById(R.id.btn_read);
        tvNation= findViewById(R.id.tv_nation);

        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //iv2 에 설정된 이미지 읽어오도록 시도..
                //iv2.getImage(); //error : 이런 기능 없음

                //이미지뷰에 특정 기록을 남기는 목적의 tag 설정이 가능함 - 위 랜덤 이미지 지정할때 저장
                //저장된 tag문을 통해 어떤 이미지인지 식별..
                String nation= iv2.getTag().toString();
                tvNation.setText(nation);

            }
        });


    }//onCreate method..

    // 버튼이 클릭하는 것을 듣는 리스너 객체 생성 및 참조변수에 대입 - 멤버변수
    View.OnClickListener listener= new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            // 이 리스너가 감시하는 버튼 중에 어떤 버튼이 눌렀는지를 알아내기.
            // 이 콜백메소드는 파라미터(매개변수)가 있음. 이 파라미터에 클릭된 버튼이 전달됨.
            int id= view.getId();

            switch (id){
                case R.id.btn01:
                    iv.setImageResource(R.drawable.flag_australia);
                    break;

                case R.id.btn02:
                    iv.setImageResource(R.drawable.flag_belgium);
                    break;

                case R.id.btn03:
                    iv.setImageResource(R.drawable.flag_canada);
                    break;

                case R.id.btn04:
                    iv.setImageResource(R.drawable.flag_korea);
                    break;
            }


        }
    };



}//MainActivity class..

