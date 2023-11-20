package com.nsw2022.ex30fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tv;
    Button btn, btn2;

    //Fragment도 View처럼 참조변수를 통해 제어
    MyFragment myFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv=findViewById(R.id.tv);
        btn=findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv.setText("Hello android");
            }
        });

        //이 액티비티가 보여주는 MyFragment가 XML에 배치했기에 이를 찾아와야함
        //액티비티가 프레그먼트까지 일일이 제어하려니 너무 번거로워서 Fragment만 관리하는 매니저를 두었음.
        //프레그먼 관리자를 소환. 하여 매니저에게 Fragment를 찾아달라고 해야함.
        FragmentManager fragmentManager=getSupportFragmentManager();
        myFragment=(MyFragment) fragmentManager.findFragmentById(R.id.myfragment);



        btn2=findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //MyFragment의 TextView 글씨 변경
                myFragment.tv.setText("Have a good day");

            }
        });
    }
}