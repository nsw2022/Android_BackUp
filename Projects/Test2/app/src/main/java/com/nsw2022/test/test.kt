package com.nsw2022.test

fun main(){
    var a="6123457123589612347"
    // 문제 조건대로라면 6123457 과 뒤의 612347 이 붙어서 출력되야함
    var b : MutableList<String> = a.split("6").toMutableList()

    println(b)

}