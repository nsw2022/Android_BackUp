package com.nsw2022.testkotiln.ex08

typealias MyFunType = (Int) ->Boolean

fun main(){
    var some1 = {no: Int -> println("somm1..$no")}
    some1(10)

    some1 = {no -> println("some1... ")}

    var some2={arg1: Int, arg2: Int-> println("some2....$arg1, $arg2")
        arg1*arg2
    }
    var  some4: MyFunType = {arg -> true}
}

