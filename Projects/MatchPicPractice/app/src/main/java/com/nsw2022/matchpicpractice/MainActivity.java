package com.nsw2022.matchpicpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    //주석 뭔가수정.

   ImageView iv_strbtn, iv_ele,  iv_forg, iv_pig, iv_monk, iv_lion;
    ImageView iv_00, iv_01, iv_02, iv_03, iv_04, iv_05;

    int [] imgs = new int[] { //동물사진
            R.drawable.a_ele,
            R.drawable.a_frog,
            R.drawable.a_lion,
            R.drawable.a_monkey,
            R.drawable.a_pig
    };

    int [] patimgs = new int[] {  //팻말사진
            R.drawable.b_ele,
            R.drawable.b_frog,
            R.drawable.b_lion,
            R.drawable.b_monkey,
            R.drawable.b_pig
    };

    ImageView iv_good, iv_bad;

    int [] test = new int[] {
            R.drawable.a_ele,
            R.drawable.a_frog,
            R.drawable.a_lion,
            R.drawable.a_monkey,
            R.drawable.a_pig
    };



    SoundPool sp;
    int sdgood, sdagain, sdstart, sdselect;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ele forg lion monk pig
        iv_ele = findViewById(R.id.iv_ele);
        iv_ele.setTag(R.drawable.a_ele);

        iv_forg = findViewById(R.id.iv_frog);
        iv_forg.setTag(R.drawable.a_frog);

        iv_lion = findViewById(R.id.iv_lion);
        iv_lion.setTag(R.drawable.a_lion);

        iv_monk = findViewById(R.id.iv_monk);
        iv_monk.setTag(R.drawable.a_monkey);

        iv_pig = findViewById(R.id.iv_pig);
        iv_pig.setTag(R.drawable.a_pig);

        iv_strbtn = findViewById(R.id.iv_strbtn);

        iv_01 = findViewById(R.id.iv_01);
        iv_02 = findViewById(R.id.iv_02);
        iv_03 = findViewById(R.id.iv_03);
        iv_04 = findViewById(R.id.iv_04);
        iv_05 = findViewById(R.id.iv_05);
        iv_00 = findViewById(R.id.iv_00);

        iv_good = findViewById(R.id.iv_good);
        iv_bad = findViewById(R.id.iv_bad);

        SoundPool.Builder builder = new SoundPool.Builder();
        builder.setMaxStreams(10);
        sp=builder.build();


        sdgood=sp.load(this,R.raw.s_goodjob,0);
        sdagain=sp.load(this,R.raw.s_again,0);
        sdstart=sp.load(this,R.raw.s_sijak,0);
        sdselect=sp.load(this,R.raw.s_select,0);





        iv_strbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp.play(sdstart,0.8f,0.8f,1,0,1);


                Random rnd = new Random();

                int n = rnd.nextInt(patimgs.length);

                int[] nums = new int[5];

                    for (int i = 0; i < imgs.length; i++) {
                        nums[i] = rnd.nextInt(5);//0~4
                        for (int j = 0; j < i; j++) {
                            if (nums[i] == nums[j]) {
                                i--;

                            }
                        }
                    }

                // 겹치지않는 숫자 4개 만들어서 각 사진 배열에 넣어줌
                iv_ele.setImageResource(imgs[nums[0]]);
                iv_forg.setImageResource(imgs[nums[1]]);
                iv_lion.setImageResource(imgs[nums[2]]);
                iv_monk.setImageResource(imgs[nums[3]]);
                iv_pig.setImageResource(imgs[nums[4]]);


                iv_00.setImageResource(patimgs[nums[n]]);
                iv_00.setTag((imgs[n]));


                iv_good.setVisibility(View.GONE);
                iv_bad.setVisibility(View.GONE);



            }



        });//iv_strbtn onclickListener

        iv_ele.setOnClickListener(listener);
        iv_forg.setOnClickListener(listener);
        iv_lion.setOnClickListener(listener);
        iv_monk.setOnClickListener(listener);
        iv_pig.setOnClickListener(listener);




    }//OnCreat 메소드

         View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            sp.play(sdselect,0.8f,0.8f,1,0,1);
            String tag = view.getTag().toString();
            int picId = Integer.parseInt(tag);

            String tag2 = iv_00.getTag().toString();
            int bordId = Integer.parseInt(tag2);

            if (picId == bordId) {

                iv_bad.setVisibility(View.GONE);
                iv_good.setVisibility(View.VISIBLE);
                sp.play(sdgood,0.8f,0.8f,10,0,1);
            } else {

                iv_good.setVisibility(View.GONE);
                iv_bad.setVisibility(View.VISIBLE);
                sp.play(sdagain,0.8f,0.8f,2,0,1);
            }


        }
     };





}






