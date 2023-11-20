package com.nsw2022.ex90kotlinhello
//constructor 키워드 생략가능
class AlbaStudent(name:String, age:Int, major:String, var task:String) : Student(name, age, major) {
    init {
        println("create AlbaStudent instance")
    }

    override fun show() {
        //super.show()
        println("name : $name   age: $age   major: $major   task: $task")
    }
}