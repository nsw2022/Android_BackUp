package com.nsw2022.ex77databinding;

import android.view.View;

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

public class User {
    //데이터가 변경되었을때 화면이 자동 갱신되려면 이 데이터를 가진 변수가 관찰가능한 변수 여야만 함.
    public ObservableField<String> name = new ObservableField<>();
    public ObservableInt age=new ObservableInt();

    public User(String name,int age) {
        this.name.set(name);
        this.age.set(age);
    }

    // 이름을 변경하는 기능메소드
    public void changeName(View v){
        this.name.set("ROBIN");
    }
}
