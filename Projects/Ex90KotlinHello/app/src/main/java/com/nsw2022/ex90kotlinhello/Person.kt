package com.nsw2022.ex90kotlinhello

// 상속해주려면 open 키워드 필요
open class Person constructor(var name:String,var age:Int){
    init {
        println("create Person instance")
    }

    // 오버라이드를 허용하려면 open 키워드 필요
    open fun show(){
        println("name: $name      age: $age")
    }
}