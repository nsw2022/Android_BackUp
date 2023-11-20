package com.nsw2022.testkotiln.section

data class User(val no: Int,val name: String)

fun main() {
    val user1=User(1,"kim")
    val user2=User(1,"kim")
    println("${user1.equals(user2)}")
    println("${user1.toString()}")

    val (no, name) = user1
    println("$no,$name")
}