package com.nsw2022.mvp.presenter

import android.content.ClipData
import android.content.Context
import com.nsw2022.mvp.model.Item

// view와 presenter 역할을 하는 클래스가 가져야할 기능을 정하는 인터페이스
interface MainContract {

    // # view 역할을 하는 클래스가 가져야 할 기능을 기술한 인터페이스
    interface View{
        fun showData(item: Item) //1) model 의 데이터를 화면에 보여주는 기능
        fun getContext(): Context         //2) presenter에서 사용할 수 있는 Context를 리턴해 주는 기능

    }

    // # presenter 영할을 하는 클래스가 가져야 할 기능을 기술한 인터페이스
    interface Presenter{
        //사용자의 이벤트에 따라 처리할 2가지 기능 [ view 역할 클래스의 요청에 의해 실행될 기능들 ]
        fun clickedSave(name:String, email:String)   //1) save 버튼을 클릭했을 때
        fun clickedLoad()    //2) load 버튼을 클릭했을 때.

        //라이프사이클 메소드들모 모두 이곳에서 처리하도록..
    }
}