package com.nsw2022.ex30fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MyFragment extends Fragment {

    TextView tv;
    Button btn,btn2;

    // 이 프레그먼트가 생성되어 화면에 보여질때 자동으로 실행되는 메소드
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //이 MyFragment가 보여줄 View를 만들어서 리턴해주는 기능메소드
        View view=inflater.inflate(R.layout.fragment_my,container,false);
        tv=view.findViewById(R.id.tv);
        btn= view.findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv.setText("Nice to meet you");
            }
        });

        btn2=view.findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 이 프레그먼트를 보여주는 MainActivity의 TextView의 글씨를 변경

                // 프레그먼트 클래스 안에서 본인을 보여주는 Activity 를 소환하는 기능메소드
                MainActivity ac = (MainActivity) getActivity();
                ac.tv.setText("Good");

            }
        });




        return view;
    }

}
