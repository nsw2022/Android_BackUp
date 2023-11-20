package com.nsw2022.test

class User {
    var id: Int = 0
    var name: String? = null

}

fun main(){
    var obj = User()
    obj.id=10
    obj.name="kkang"

    println("id :${obj.id}, name: ${obj.name}")
}