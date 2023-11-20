package com.nsw2022.lostark_test

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.BitmapFactory.decodeResource
import android.graphics.drawable.Drawable
import android.graphics.drawable.Icon
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.load.engine.Resource
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class MainActivity : AppCompatActivity() {
    lateinit var ViewPagerAdapter: ViewPagerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewPager:ViewPager2 = findViewById(R.id.view_pager)
        val tabLayout:TabLayout = findViewById(R.id.tab_layout)

        // FragmentStateAdapter 생성
        val viewPagerAdapter = ViewPagerAdapter(this)

        // ViewPager2의 adapter 설정
        viewPager.adapter = viewPagerAdapter


        // ###### TabLayout과 ViewPager2를 연결
        // 1. 탭메뉴의 이름을 리스트로 생성해둔다.
        val tabTitles = listOf<String>("첫번째", "두번째", "세번째")



        val tabIconArray = arrayOf(
            R.drawable.ic_baseline_people_24,
            R.drawable.ic_baseline_chat_24,
            R.drawable.ic_baseline_miscellaneous_services_24
        )

        // 2. TabLayout과 ViewPager2를 연결하고, TabItem의 메뉴명을 설정한다.
        //TabLayoutMediator(tabLayout, viewPager, {tab, position -> tab.text = tabTitles[position]}).attach()
        TabLayoutMediator(tabLayout, viewPager, object :TabLayoutMediator.TabConfigurationStrategy{
            override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {
                tab.icon=getDrawable(tabIconArray[position])
            }
        }).attach()

    }

}