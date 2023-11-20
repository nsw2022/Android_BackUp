package com.nsw2022.lostark_test

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragmentActivity: FragmentActivity):FragmentStateAdapter(fragmentActivity) {

    val fragmentList = listOf<Fragment>(TalkFragment(),ListFragment(), ConfigFragment())



    // Adapter 가 가지고 있는 data set 안에서의 전체 아이템 수 리턴
    override fun getItemCount(): Int {

        return fragmentList.size
    }

    // 특정 포지션에 연결된 새로운 Fragment 를 제공하는 기능을 가진 메소드
    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
}