package com.nsw2022.exappbarlayout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Tab1Fragment extends Fragment {
    //프레그먼트가 보여줄 뷰를 만들어서 리턴해주는 기능
    TextView tv;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_tab1,container,false);

        return view;
    }

    // 이 프레그먼트가 화면에 보여질때 자동으로 실행되는 콜백메소드
    @Override
    public void onResume() {
        super.onResume();

        //엑티비티의 sub title 변경
        MainActivity ma = (MainActivity) getActivity();
        ma.getSupportActionBar().setSubtitle("HOME");

    }
}
