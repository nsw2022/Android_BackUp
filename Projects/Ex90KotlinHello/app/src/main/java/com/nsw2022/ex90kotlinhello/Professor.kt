package com.nsw2022.ex90kotlinhello

//보조생성자로 상속받아보는 연습
class Professor : Person{
    var subject:String? =null

    // 상속받았다면 반드시 부모생성자의 호출문을 명시해야 함
    constructor(name:String,age:Int, subject: String) : super(name,age) {
        this.subject=subject
        println("create Professor instance")
    }

    override fun show() {
        println("name: $name     age: $age     subject : $subject")
    }
}