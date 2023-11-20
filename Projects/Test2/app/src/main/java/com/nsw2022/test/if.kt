package com.nsw2022.test

fun main(){

    var arg=10

    val result = if(arg>20){
        println("arg>20")
        10
    }else if (arg>30){
        20
    }else {
        30
    }

    val data: Any=10
    when(data){
        1-> println("arg is 1")
        10, 20-> println("arg")
        30->{

        }

        "hello"-> println("arg is 1")
    }
}