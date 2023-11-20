package com.nsw2022.ex90kotlinhello

import kotlin.math.hypot

fun main() {
    //Scope function : apply, let, run, also ... with

    // 어떤 객체의 멤버사용을 여러개 하고 싶을때..
    val crew=Crew()
    crew.name="sam"
    crew.age=20
    crew.address="seoul"
    crew.show()

    // 한객체의 멤버들을 접근할때 마다.. 객체명 쓰는거 짜증
    // 그래서 등장한 scope function
    var crew2=Crew()
    crew2.apply {
        // 이영역안에서는 this가 키워드가 crew2 , this 키워드는 생략이가능
        this.name = "robbin"
        age=25
        address="seoul"
        show()
        // 리턴값이 this 객체임
    }

    // 위처럼 영역을 묶어서 한객체의 멤버들을 사용하게 하여
    // 개발자가 다른 객체의 멤버를 사용하는 실수를 줄여주는 문법
    //1) 영역안에서 this 키워드로 본인을 참조하는 scope : apply, run
    //2) 영역안에서 it 키워드로 본인을 참조하는 scope : also, let

    val crew3=Crew()
    crew3.apply {
        name = "kim"
        age = 30
        address = "aa"
        show()
    }

    val crew4=Crew()
    crew4.let {
        //it 키워드는 생략 불가
        it.name = "hong"
        it.age = 35
        it.address = "bbb"
        it.show()
    }
}

class Crew{
    var name:String?=null
    var age:Int?=null
    var address:String?=null

    fun show(){
        println("$name : $age  -  $address")
    }
}