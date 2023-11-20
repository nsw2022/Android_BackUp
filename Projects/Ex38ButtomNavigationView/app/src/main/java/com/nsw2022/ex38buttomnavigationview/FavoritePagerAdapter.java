package com.nsw2022.ex38buttomnavigationview;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class FavoritePagerAdapter extends FragmentStateAdapter {

    Fragment[] fragments = new Fragment[2];

    public FavoritePagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);

        fragments[0]=new FavoritePage1Fragment();
        fragments[1]=new FavoritePage2Fragment();
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
