package com.nsw2022.mvp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nsw2022.mvp.R
import com.nsw2022.mvp.databinding.ActivityMainBinding

// MainContract 인터페이스에서 기술한 view 라면 가져야할 기능들이 설계된 View 인터페이스를 구현
class MainActivity : AppCompatActivity() {
    //2. MVP [ Model - View - Presenter ] - view와 model의 완전분리 특징이 가장 두드러짐
    // 1) Model      - MVC패턴의 model과 같은 역할 : 데이터를 저장하는 클래스, 데이터를 제어하는 코드를 가진 클래스들 [ex. Item 클래스, Person 클래스, Retrofit 작업 클래스, DB작업 클래스...]
    // 2) View       - 사용자 볼 화면을 구현하는 목적의 코드가 작성되는 파일들 [ex. activity_main.xml, MainActivity.kt, Fragment.kt...]
    // 3) Presenter  - 뷰와 모델 사이에서 연결하는 역할, interface 로 역할을 정해놓기에 특정 객체를 만들어 참조하여 결합되는 것을 방지함.

    // ** 주요 제작 특징 : view와 presenter가 해야할 작업들을 미리 interface를 통해 설계해 놓음으로서 작업의 역할 구분이 명확하게 보임.

    // # view 참조변수
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}