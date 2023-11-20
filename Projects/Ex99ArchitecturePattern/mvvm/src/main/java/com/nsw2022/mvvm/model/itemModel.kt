package com.nsw2022.mvvm.model

import android.content.Context
import android.content.SharedPreferences

// model 역할 : 데이터를 제어하는 기능 2개를 가진 클래스
class ItemModel constructor(val context: Context) {  // 데이터를 제어하기 위해 Context 능력이 필요한 경우가 많아서 주 생성자로 전달받기

    //1] viewModel 역할의 클래스로 부터 데이터를 전달받아 SharedPreferences 에 데이터를 저장하는 기능
    fun saveData(name:String, email:String){
        context.getSharedPreferences("data", Context.MODE_PRIVATE).edit().apply {
            putString("name", name)
            putString("email", email)
            commit()
        }
    }

    //2] SharedPreferences 에서 데이터를 읽어와서 viewModel 에게 리턴(내보내는)해주는 기능
    fun loadData(): Item{
        val pref: SharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE)
        var name:String = pref.getString("name", "") as String
        var email:String = pref.getString("email", "") as String

        return Item(name, email)
    }
}
