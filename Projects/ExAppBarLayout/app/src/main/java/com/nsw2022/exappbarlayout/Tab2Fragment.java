package com.nsw2022.exappbarlayout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Tab2Fragment extends Fragment {
    //프레그먼트가 보여줄 뷰를 만들어서 리턴해주는 기능
    TextView tv;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_tab2,container,false);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        MainActivity ma=(MainActivity) getActivity();
        ma.getSupportActionBar().setSubtitle("SHOP");
    }
}
