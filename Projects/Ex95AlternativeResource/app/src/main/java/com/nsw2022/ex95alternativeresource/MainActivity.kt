package com.nsw2022.ex95alternativeresource

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Alternative Resources [ 대체 리소스 제공 ]

        // 앱이 구동되는 디바이스 특성에 따라 res 리소스 파일이 자동 선택되어 적용되는 기술
        // 앱의 해상도 밀도에 따른 아이콘 이미지 적용 - mipmap-hdpi, -mdpi, xhdpi ....

        //1. 현지화화 - 언어별 Strings.xml

        //2. layout 방향 - land(가로), port(세로) 에 따른 레이아웃의 구조 변경

        //3. theme 에 (night) 모드


    }
}