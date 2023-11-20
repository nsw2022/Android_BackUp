package com.nsw2022.zzz_viewpagerulex;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;


public class MyFagmentAdapter extends FragmentStateAdapter {

    Fragment[] fragment=new Fragment[5];

    public MyFagmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        fragment[0]=new Page1Fragment();
        fragment[1]=new Page2Fragment();
        fragment[2]=new Page3Fragment();
        fragment[3]=new Page4Fragment();
        fragment[4]=new Page5Fragment();

    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragment[position];
    }

    @Override
    public int getItemCount() {
        return fragment.length;
    }
}
