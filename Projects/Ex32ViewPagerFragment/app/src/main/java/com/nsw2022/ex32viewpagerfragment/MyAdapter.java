package com.nsw2022.ex32viewpagerfragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

//기존 아답터와 다르게 Fragment를 만들어서 뷰페이저에게 리턴해주는 아답터
public class MyAdapter extends FragmentStateAdapter {

    //프레그먼트 참조변수 3개 배열객체
    Fragment[] fragments=new Fragment[3];


    public MyAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        fragments[0]=new Page1Fragment();
        fragments[1]=new Page2Fragment();
        fragments[2]=new Page3Fragment();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments[position];
    }

    @Override
    public int getItemCount() {
        return fragments.length;
    }
}
