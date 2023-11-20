package com.nsw2022.clickclick;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    LinearLayout layoutMain;
    TextView tv;
    ImageView[] btns= new ImageView[12];

    int cnt=0;
    int stage=1;

    int[] arr= new int[12];
    Random rnd= new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        makeRandom();

        layoutMain= findViewById(R.id.layout_main);
        tv= findViewById(R.id.tv_msg);
        for(int i=0; i<12; i++){
            btns[i]= findViewById(R.id.btn01+i);
            btns[i].setImageResource(R.drawable.num01+arr[i]);
            btns[i].setTag(arr[i]);
        }

    }

    //중복없는 랜던값 생성작업 메소드
    void makeRandom(){
        for(int i=0; i<12; i++){
            arr[i]= rnd.nextInt(12);
            for(int k=0; k<i; k++){
                if(arr[i]==arr[k]){
                    i--;
                    break;
                }
            }
        }
    }

    //start 버튼 클릭메소드
    public void clickStart(View v){
        ((ImageView)v).setImageResource(R.drawable.ing);
        for(ImageView b : btns) b.setVisibility(View.VISIBLE);

        tv.setText("숫자를 순서대로 누르세요");
    }

    //이미지 버튼들 클릭메소드
    public void clickBtn(View v){

        int n= (Integer)v.getTag();

        if( n== cnt){
            v.setVisibility(View.INVISIBLE);
            cnt++;

            if(cnt>=12){
                goNextStage();
                cnt=0;
            }
        }
    }

    //다음 스테이지 생성작업 메소드
    void goNextStage(){
        stage++;

        //GameOver..
        if(stage>3){
            layoutMain.setBackgroundResource(R.drawable.bg4);
            tv.setVisibility(View.GONE);
            findViewById(R.id.btn_start).setVisibility(View.GONE);
            return;
        }

        //새로운 랜던값 생성
        makeRandom();

        //stage에 맞게 버튼들에 그림 적용하기
        for(int i=0; i<12; i++){
            if(stage==2){
                btns[i].setImageResource(R.drawable.alpa01+arr[i]);
                tv.setText("알파벳 순서대로 누르세요");
                layoutMain.setBackgroundResource(R.drawable.bg2);
            }
            else if( stage==3){
                btns[i].setImageResource(R.drawable.cha01+arr[i]);
                tv.setText("십이지신 순서대로 누르세요");
                layoutMain.setBackgroundResource(R.drawable.bg3);
            }

            btns[i].setTag(arr[i]);
            btns[i].setVisibility(View.VISIBLE);
        }
    }
}