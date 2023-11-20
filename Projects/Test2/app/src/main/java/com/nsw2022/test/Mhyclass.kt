package com.nsw2022.test

class Mhyclass {
    val data: Int
    var data2: String
    init {
        data=10
        data2="Kim"
    }
    lateinit var data3: String

    val data4: Int by lazy {
        println("lazy.....")
        10
    }



}