package com.nsw2022.ex31fagment2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btn;

    FragmentManager fragmentManager;
    MyFragment myFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //프레그먼트를 관리하는 매니저 소환
        fragmentManager= getSupportFragmentManager();

        btn= findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //MyFragment를 만들어서 id가 container_fragment인 ViewGroup안에 배치하기

                myFragment= new MyFragment();

                //프레그먼트 매니저가 동적으로 MyFragment를 추가하는 작업 시작!
                FragmentTransaction tran= fragmentManager.beginTransaction(); //트랜잭션 : 롤백기능이 있는 프로세스(작업)

                tran.add(R.id.container_fragment, myFragment);

                // 만약 '뒤로가기' 버튼을 눌렀을때 현재 프레그먼트가 없어지고 이전 프레그먼트가 보여지도록 하고싶다면.
                // 프레그먼트들이 백그라운드에 쌓여있어야함.
                tran.addToBackStack(null);

                //반드시 트랜잭션을 끝마쳤다는 명령을 해야만 위 작업이 실행됨
                tran.commit();





            }
        });
    }
}