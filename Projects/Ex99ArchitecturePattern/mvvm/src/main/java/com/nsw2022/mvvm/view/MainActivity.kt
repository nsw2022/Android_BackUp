package com.nsw2022.mvvm.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.nsw2022.mvvm.R
import com.nsw2022.mvvm.databinding.ActivityMainBinding
import com.nsw2022.mvvm.viewmodel.ItemViewModel

class MainActivity : AppCompatActivity() {

    //3. MVVM [ Model - View - ViewModel ] : view와 model의 데이터를 연결해 놓아서 model의 데이터가 변경되면 view가 보여주는 데이터가 자동 갱신되는 특징.
    // 1) Model      - MVC, MVP의 model과 똑같음. 데이터를 저장하는 클래스나 데이터를 제어하는 코드를 가진 클래스들.
    // 2) View       - 사용자가 볼 화면을 구현하는 목적의 코드가 있는 클래스들.
    // 3) ViewModel  - 뷰와 모델을 연결하는 역할. 뷰의 사용자 이벤트가 있을때 이를 처리하여 model을 제어하는 코드가 있는 파일들.

    // MVVM 을 위해서는 [ dataBinding : 데이터바인딩 ] 기술을 이용하여 개발하는 것이 일반적임.
    // Data Binding 에서는 뷰바인딩과 다르게 화면 레이아웃 XML 파일의 최상위 root 요소가 <layout> 이어야 함.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //1] view 역할
        // DataBindingUtil클래스를 통해 이 MainActivity와 activity_main.xml 레이아웃을 ContentView()로 설정하고 이를 연결하여 제어하는 객체를 생성
        val binding:ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        //2] 연결된 레이아웃 파일안에 만든 변수에 값을 설정
        // viewModel 역할의 클래스 객체를 지정
        val viewModel:ItemViewModel= ItemViewModel(this)
//        binding.viewModel= viewModel

    }

    // ** view는 viewModel을 참조하고. viewModel은 model을 참조하고 있음..

    // MVVM의 장점
    //1. MVP 의 view와 presenter 처럼 1:1로 대응되어 있지 않기에 뷰가 많아져도 뷰모델이 많아지지 않아도 됨
    //2. 사용자 이벤트 처리를 모두 viewModel 역할 클래스에서 하고 있기에 중복되는 코드를 다시 작성하지 않고 재사용 가능함.
    //3. 데이터 바인딩이 되어 있기에 데이터가 변경되었을때 별도의 setText() or notifyDataSetChange()같은 갱신코드가 필요 없이 자동 갱신됨.
    //4. Activity 코드가 가장 간결함.

    // MVVM의 단점
    //1. data binding 레이아웃 설계 작업이 다소 복잡함
    //2. view 처리가 복잡할 수록 결국 viewModel 클래스가 엄청 비대해짐

    // ##### android 는  MVVM패턴에 대한 제작을 보다 쉽게 해주기 위해 별도의 지원 클래스 API 를 만들어서
    // JetPack 으로 제공하고 있음. ##################
    // *** AAC - Android Architecture Component ****
    // * 대표 클래스 - ViewModel, Lifecycle, Repository, Room ***
}