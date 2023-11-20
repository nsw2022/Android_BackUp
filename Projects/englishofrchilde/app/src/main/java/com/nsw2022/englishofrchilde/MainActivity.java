package com.nsw2022.englishofrchilde;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ImageView first;//팻말처음꺼
    ImageView btnRandom;//게임스타트버튼

    ImageView a_ele;
    ImageView a_frog;
    ImageView a_lion;
    ImageView a_monkey;
    ImageView a_pig;

    ImageView b_ele;
    ImageView b_frog;
    ImageView b_lion;
    ImageView b_monkey;
    ImageView b_pig;

    int []  imgs = new int[] {  //동물사진
            R.drawable.a_ele,
            R.drawable.a_frog,
            R.drawable.a_lion,
            R.drawable.a_monkey,
            R.drawable.a_pig,
    };


    int [] patimgs = new int[]{ // 팻말사진
            R.drawable.b_ele,
            R.drawable.b_frog,
            R.drawable.b_lion,
            R.drawable.b_monkey,
            R.drawable.b_pig,

    };

    ImageView ivGood;
   // ImageView ivBad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        a_ele=findViewById(R.id.a_ele);
        a_ele.setTag(R.drawable.a_ele);

        a_frog=findViewById(R.id.a_frog);
        a_frog.setTag(R.drawable.a_frog);

        a_lion=findViewById(R.id.a_lion);
        a_lion.setTag(R.drawable.a_lion);

        a_monkey=findViewById(R.id.a_monkey);
        a_monkey.setTag(R.drawable.a_monkey);

        a_pig=findViewById(R.id.a_pig);
        a_pig.setTag(R.drawable.a_pig);

        b_ele=findViewById(R.id.b_ele);
        b_frog=findViewById(R.id.b_frog);
        b_lion=findViewById(R.id.b_lion);
        b_monkey=findViewById(R.id.b_monkey);
        b_pig=findViewById(R.id.b_pig);



        first = findViewById(R.id.b_first);
        btnRandom =findViewById(R.id.btn_strat);

        btnRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Random rnd = new Random();

                int n=rnd.nextInt(patimgs.length);
                first.setImageResource( patimgs[n] ); // 팻말사진을 랜덤으로
                first.setTag( imgs[n]);// 팻말사진에 해당하는 동물사진

                ivGood.setVisibility(View.GONE);
          //      ivBad.setVisibility(View.GONE);
            }
        });

        a_monkey.setOnClickListener( listener );
        a_pig.setOnClickListener( listener );
        a_lion.setOnClickListener( listener );
        a_ele.setOnClickListener( listener );
        a_frog.setOnClickListener( listener );

        ivGood= findViewById(R.id.good);
      //  ivBad.findViewById(R.id.bad);
    }//onCreate method..

    // 온클릭 한번더
    // if(만약에 너가 클릭한값이 팻말값과 같냐?) - tag 로 랜덤이미지가 사용자가 클릭한 이미지와 맞는지 비교
    // { 숨겨놧던 그림출력 }
    // else if { 다시한번 그림출력}
    //----------------------------------------------


    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            //클릭한 이미지의 tag
            String tag= view.getTag().toString();
            int picId= Integer.parseInt(tag);

            //보드판에 저장했던 tag
            String tag2= first.getTag().toString();
            int boardId= Integer.parseInt(tag2);

            if( picId == boardId ){
                ivGood.setVisibility(View.VISIBLE);
            }else{
              //  ivBad.setVisibility(View.VISIBLE);
            }



        }
    };


}