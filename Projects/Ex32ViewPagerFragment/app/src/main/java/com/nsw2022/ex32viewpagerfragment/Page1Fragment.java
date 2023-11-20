package com.nsw2022.ex32viewpagerfragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Page1Fragment extends Fragment {

    TextView tv;
    Button btn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_page1, container, false);
        return view;
    }

    //위 onCreateView()메소드를 통해 만들어진 뷰안에 값들을 제어하기위해 자동으로 실행되는 메소드
    //View가 만들어진 후 자동으로 실행되는 메소드
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tv= view.findViewById(R.id.tv);
        btn= view.findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv.setText("Nice to meet you.");
            }
        });


    }
}
