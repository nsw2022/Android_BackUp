package com.nsw2022.ex90kotlinhello

//상속받을 Person 클래스에 이미 name, age 프로퍼티들이 있으므로
//Student 에서는 name과 age를 받아서 Person에 넘겨줘야함
open class Student constructor(name:String,age:Int,var major:String) : Person(name,age){
    init {
        println("create Student instance")
    }

    // override 키워드가 추가되면 open 키워드가 자동 적용됨.
    open override fun show() {
        println("name: $name    age:$age     major:$major")
    }
}