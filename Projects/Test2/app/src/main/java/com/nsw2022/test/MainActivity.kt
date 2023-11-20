package com.nsw2022.test

import android.os.Build.VERSION_CODES.M
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

fun main(){
    fun myFun(arg1: Int, arg2: String) = println("arg1 : $arg1, arg2: arg2$")

    //myFun(10)
    myFun(10,"kim")
    myFun(arg2 = "lee", arg1 = 20)

    fun myFun2(arg1 : Int, vararg arg2: Int){
        for (a in arg2){

        }
    }
}