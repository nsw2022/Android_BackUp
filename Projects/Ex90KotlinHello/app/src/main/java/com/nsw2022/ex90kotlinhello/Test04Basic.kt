package com.nsw2022.ex90kotlinhello

import android.app.ProgressDialog.show
import android.telephony.AccessNetworkConstants

fun main(){
    //7. 함수
    //7.1 함수 선언 및 호출
    show()

    //7.2 함수 파라미터 전달
    output(100, "Hello")
    //7.3 리턴하는 함수
    var n:Int=sum(5,3)
    println("sum 함수의 결과 값 : $n")
    println()

    // 참고** 리턴이 없는 함수의 리턴을 받으면?
    var x=display()
    println(x)
    println()
    //-------------------------------------------------------------------------------------------

    // 함수를 만드는 특이한 모습들...
    // 7.4 함수의 선언의 단순화 : 리턴 키워드 할당 연산자[ = ] 로 바꿀수 있음.
    var data=getData()
    println(data)

    val data2= getData2()
    println(data2)

    val data3= getData3(15)
    println(data3)

    val data4= getData4(5)
    println(data4)
    println()
    //--------------------------------------------------------------------------

    //7.5 익명함수 - 조금 이해가 어려울 수 있음. 지금은 그냥 이런문법이 있구나 정도만..[ 추후 리스너 처리할때 많이 사용될 것임]
    aaa()
    bbb()
    ccc()
    ddd()
    eee()

    fff("android")
    ggg("ios")
    hhh("Hello")
    iii("aaaaaa")

    println( kkk() ) //리턴값을 바로출력
    println( lll() )
    println( mmm() )
    println( nnn() )

    getLength("aaa", eee ) // 익명함수 eee를 전달
    getLength("bbb",fun(){ println("s")})
    getLength("bbb",{  println("s")} )
}
//7.5 익명함수 - 조금 이해가 어려울 수 있음. 지금은 그냥 이런문법이 있구나 정도만..[ 추후 리스너 처리할때 많이 사용될 것임]
//7.5.1 그냥함수
fun aaa(){
    println("aaa")
}

//7.5.2 익명함수 - 함수의 이름이 없음 이름이 없기에 그냥 쓰면 에러
//fun(){}
// 함수를 일반 변수에 대입(저장) 할 수 있으며 저장하면 변수이름이 함수이름이 됨!!
var bbb=fun(){
    println("bbb")
}

//7.5.3 익명함수를 저장하는 변수에 자료형 표기해 보기
//함수의 자료형 모습 : [ ()->리턴타입 ]
var ccc:()->Unit = fun(){
    println("ccc")
}

//7.5.4 익명함수를 좀 축약형으로 쓰고 싶다면.. 즉, fun() 글씨가 꼭 필요할가?
val ddd:()->Unit= {
    println("ddd")
}

//7.5.5 익명함수를 저장하는 변수의 자료형을 명시하지 않고 자동추론하도록.
val eee = {
    println("eee")
}

//7.5.6 파라미터를 받는 익명함수
val fff= fun(s:String){
    println("글자수 : ${s.length}")
}

// 7.5.7 변수의 자료형을 명시
val ggg:(String)->Unit= fun(s:String){
    println("글자수 : ${s.length}")
}

//7.5.8 익명함수를 축약형으로.. fun() 생략. 대신{} 안에 파라미터 변수명 표시->
val hhh:(String)->Unit = {
    s-> println("글자수 : ${s.length}")
}

//7.5.9 만약 익명함수의 축약형 사용할때 파라미터가 1개라면. 생략가능함. [ 자동으로 it 이라는 변수가 지정됨 ]
val iii:(String)->Unit = {
    it->println("글자수 : ${it.length}")
}


//변수의 자료형을 자동추론시켜버리면 파라미터의 존재여부를 확인할수 없다고 판단해버림
// 그래서 it도 사용 불가능해짐
//val iii2={
//    println(it.length)//error
//}

val iii3={
        s:String->println(s.length)
}

//7.5.10 리턴타입 있는 익명함수
val kkk= fun():Int{
    return 10
}

//7.5.11 익명함수의 자료형 명시해보기
val lll:()->Int = fun():Int{
    return 20
}

//7.5.12 익명함수의 축약형
val mmm:()->Int ={
    20//fun()키워드를 생략하는 축약을 했다면 return 키워드도 생략해야만 함.
}

//7.5.13 만약{} 영역안에 여러줄이 있다면.. 마지막 줄이 리턴값임
val nnn:()->Int={
    30
    40
    println("중간 실행문")
    50
}

//7.5.14 파라미터와 리턴이 모두 있는 익명함수
val ooo:(Int,Int)->Int =fun(a:Int, b:Int):Int{
    return a+b
}

val ppp:(Int,Int)->Int= { a,b-> a+b }

val rrr:(String)->Int={
    it.length
}

//** 익명함수는 '고차함수' 라는 곳에서 많이 활용됨.
// 고차함수 : 파라미터(매개변수)로 다른 함수를 전달받는 함수
fun getLength(s: String,aaa:()->Unit){
    aaa()  //전달받은 함수를 대신 호출해줌.
}

// 7.4 함수의 선언의 단순화 : 리턴 키워드 할당 연산자[ = ] 로 바꿀수 있음.
// 7.4.1 기본적인 return 사용 함수
fun getData():String{
    return "Hello"
}

//7.4.2 return 키워드를 할당연산자로 바꾸어 함수 선언
fun getData2():String = "Hello"

//7.4.3 조금 더 복잡한 return 함수
fun getData3(n:Int):String{
    if (n<10) return "Good"
    else return "bad"
}

//7.4.4 조금 더 복잡한 return 함수 할당 연산자로 단순화
fun getData4(n:Int):String = if (n<10) "Good" else "Bad"


//참고** 리턴타입 명시가 없는 함수.. void가 아니라 Unit 이라는 자료형
fun display(){
    println("display...")
}


//7.3 리턴하는 함수
// 리턴타입을 함수명 앞에 쓰는게 아니라 함수명() 뒤에 : 후에 리턴 타입을 명시
fun sum(a:Int,b:Int):Int {
    return a+b
}


//7.2 파라미터 전달 받는 함수선언
// 파라미터변수에 var 키워드 사용 금지, [default 로 val 로 됨]
fun output(a:Int,b:String){
    println(a)
    //a=50//error
    println(b)
    println()
}

//7.1 함수 선언
fun show(){
    println("show function")
    println()
}