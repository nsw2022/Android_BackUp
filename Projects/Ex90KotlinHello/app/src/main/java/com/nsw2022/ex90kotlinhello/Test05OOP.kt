package com.nsw2022.ex90kotlinhello


fun main(){
    //1. class 선언 및 객체생성
    // 객체 생성 [ 매우 특이함! - new 키워드 없음 ]
    var obj:MyClass= MyClass()
    obj.show()

    //별도의 파일로 class 객체 생성
    val obj2=MyKotlinClass()
    obj2.show()

    //2. 생성자 [ 많이 당황스러움 ]
    // 코틀린의 생성자는 2가지 종류가 있음. [ 주생성자, 보조생성자 ]
    //2.1 주생성자
    val s:Simple = Simple()
    val s2:Simple2= Simple2(100, 200)
    //val s3:Simple2 = Simple2( ) // error - overloading [ 주생성자는 overloading 불가능 ]

    //2.2 보조생성자 - overloading
    val ss:Simple3 = Simple3()
    val ss2:Simple3 = Simple3(50)

    //2.3 주생성자 + 보조생성자
    val ssss:Simple4= Simple4()
    println()
    val ssss2:Simple4=Simple4(100)//보조생성자로 오버로딩
    println()

    //2.4
    val sssss:Simple5 = Simple5()

}

//2.4  참고로.. 주 생성자의 constructor 키워드는 생략이 가능함
class Simple5 (){
    init {
        println("Simple5 primary constructor")
        println()
    }
}

//2.3
class Simple4 constructor(){
    init {
        println("Simple4 init")
    }

    // 주 생성자와 함께 보조생성자를 사용한다면
    // 보조생성자에서 반드시 명시적으로 부모생성자를 호출해야함
    constructor(num:Int) : this() {
        println("Simple4 num: $num")
    }
}

//2.2 보조생성자만있는 클래스
class Simple3 {
    constructor(){
        println("Simple3 secondary constructor")
        println()
    }
    //보조 생성자 오버로딩, 파라미터에 곧바로 var 키워드를 붙여 멤버를 만들 수 있는 것은 주 생성자만 가능함.
    constructor(num:Int){
        println("Simple3 secondary constructor : $num")
        println()
    }
}

// 2.1.2 주 생성자에 파라미터 전달
// 파라미터에 var, val 키워드를 붙이면 파라미터면서 멤버변수까지 됨.
class Simple2 constructor(num:Int, var num2:Int){

    // 멤버변수 위치에서 주 생성자의 매개변수를 바로 사용가능함
    var num3:Int=num

    init {
        println("num : $num")
    }

    fun show(){
        //println("Show num: $num") // num인식 불가 생성자의 파라미터는 init 영역에서만 인식이 가능
        println("Show num: $num2")// num2는 멤버변수여서 인식 가능
    }
}

//2.1 주생성자 연습 - 클래스명 옆에 constructor()를 붙힘.
class Simple constructor(){
    // 객체의 주생성자가 호출될 때 자동으로 초기화를 위해 실행되는 영역
    init {
        println("Simple primary constructor!")
        println()
    }
}

//1.
class MyClass{
    //1) 멤버변수 - [ Property :프로퍼티 ] 라고 부름 - 반드시 초기화 해야 함.
    var a:Int= 10

    //2) 멤버함수 - [ Method : 메소드 ]
    fun show(){
        println(" show : $a")
        println()
    }
}