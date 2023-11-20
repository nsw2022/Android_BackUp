package com.nsw2022.ex15soundpool;

import androidx.appcompat.app.AppCompatActivity;

import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnStart, btnAgain, btnSelect;

    // 사운드 풀 참조변수
    SoundPool sp;
    // 등록된 사운드 음원들의 식별번호 저장 변수
    int sdStart, sdAgain, sdSelect;

    int sdMusic;
    Button btnMusic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 사운드풀(음악플레이어)객체 생성하여 음원들을 등록 - 효과음 용(9초 이상되는 음원은 자동 종료됨)

        //사운드풀객체를 만들어주는 Builder 이용
        SoundPool.Builder builder=new SoundPool.Builder();
        builder.setMaxStreams(10);//동시에 플레이 가능한 음원의 수 - 10개 넘어가면 우선순위가 낮은 소리는 안들림
        sp= builder.build();

        //음원들 등록 하면.. 자동으로 음원 들을 구별하는 식별번호(id)가 리턴됨
        sdStart = sp.load(this,R.raw.s_sijak,0);//음원을 등록할 때 우선순위는 모두 0으로
        sdAgain = sp.load(this,R.raw.s_again,0);
        sdSelect = sp.load(this,R.raw.s_select,0);

        sdMusic=sp.load(this,R.raw.kalimba,0);

        btnStart=findViewById(R.id.btn_start);
        btnAgain=findViewById(R.id.btn_again);
        btnSelect=findViewById(R.id.btn_select);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp.play(sdStart,0.8f,0.8f,3,0,1);
            }
        });

        btnAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp.play(sdAgain,0.8f,0.8f,2,0,1);
            }
        });

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp.play(sdSelect,0.8f,0.8f,1,0,1);
            }
        });

        btnMusic=findViewById(R.id.btn_music);
        btnMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //효과음 용이므로 9초 이상되면 자동 멈춤. 음악은 MediaPlayer라는 별도의 클래스를 이용함
               sp.play(sdMusic,0.8f,0.8f,4,0,1);
            }
        });


    }
}