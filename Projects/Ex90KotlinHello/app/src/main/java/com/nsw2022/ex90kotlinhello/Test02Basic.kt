package com.nsw2022.ex90kotlinhello

import android.app.Person

fun main (){

    //3. 연산자 특이점..
    // 숫자타입들간의 연산은 자동형변환 수행됨
    println( 10 + 3.14 )

    // 숫자타입이 아닌 자료형과는 연산 안됨
    //println(10 + true) //error
    //println(10 + 'A')  //error

    // 연산자중에 특별한 것은 없고. is 라는 연산자 추가됨
    // 자료형을 체크하는 연산자
    // 통상적으로는 Any 타입일때 사용함
    var obj:Any

    obj= 10
    obj= 3.14
    if( obj is Int ) println("${obj}는 Int입니다.")

    class Person{
        var name:String = "sam"
        var age:Int = 20
    }

    obj= Person() //Kotlin은 new 키워드 없음
    if(obj is Person){
        println("이름: ${obj.name}    나이: ${obj.age}")
    }

    // is의 특이점. 참인 영역안에서는 그 클래스의
    // 멤버가 리스트업 됨.
    var obj2:Any
    obj2= Person()
    if(obj2 is Person){
        obj2.name= "aaa"
    }

    // 결론. java의 instanceof 라는 연산자와 비슷한 기능

    // 비트연산자 기호가 없어짐. [논리연산자 아님 ]
    //println( 7 | 3 ) //error
    println( 7.or(3) ) //OR연산 기능 메소드
    println( 7 or 3)        //OR연산 글씨
    println( 7 and 3 )
    println()

    //////////////////////////////////////////


    //4. 조건문.. [ if, when ] - switch 문법 없음

    //4.1  [if 표현식] 이라고 부름
    if(10>15){
        println("aaa")
    }else{
        println("bbb")
    }

    //차이점.
    var str:String
    if( 10>15 ){
        str= "aaa"
    }else{
        str= "bbb"
    }

    println(" str : $str")

    // 자바의 3항연산자가 없어지고..대신..
    // if 표현식이 그 역할을 함.
    //str= (10>5)? "aaa" : "bbb"
    str= if(10>5) "aaa" else "bbb"
    println(" str : $str")

    str= if(10>5){
        println("참이다!!")
        "aaa"
        "Hello"  //만약 여러줄이면 마지막 값이 결과값
    }else{
        println("거짓이다!!")
        "bbb"
        "Nice"
    }
    println(" str : $str")

    // 4.2 switch문법이 없고 when 문법이 이를 대체함
    var h:Any

    var num:Int= 50

    h=2000
    when(h){
        10-> println("aaa")
        20-> println("bbb")
        "aaa"-> println("Hello")
        true -> println("true")
        //변수값과도 비교 가능함
        num -> println("num변수와 같음")
        // 2개 이상의 조건을 묶을 수 있음.
        30, 40 -> println("ccc")

        //switch문의 default 같은 역할
        else -> { //여러줄일때 중괄호
            println("ddd")
            println("eee")
        }
    }

    // when의 조건을 수식으로 지정할 수 있음.
    h= 85
    when{ // ()가 없음
        //h>=90 && h<=100 -> println("A학점")
        h in 90..100 -> println("A학점")
        h>=80 -> println("B학점")
        h>=70 -> println("C학점")
        h>=60 -> println("D학점")
        else -> println("F학점")
    }

    h= 3.14
    when(h){
        is Int-> println("정수입니다.")
        is Double -> println("실수입니다")
        is Boolean -> println("논리값입니다.")
        else -> println("뭐여??")
    }

    //5. 반복문 : for, while
    // while 문은 사용법이 같음

    // for 문은 문법이 완전 다름
    //for (int i=0; i<5;i++){}//이런문법이없음
    println()
    //0부터 5까지 6번 반복
    for (i in 0..5){//제어변수를 만드는 var 키워드 없음
        println(i)
    }
    println()
    // .. 양 옆의 공백은 무시됨
    for (a in 3  ..  10){
        println(a)
    }
    println()

    // 마지막 숫자 전까지 ..대신 until
    for ( a in 0 until 10){//0..9까지
        println(a)
    }
    println()

    // 2씩 증가
    for ( i in 0 .. 10 step 2 ){
        println(i)
    }
    println()

    // 값의 감소 (downTo)
    for ( i in 10 downTo 0) println(i)
    println()

    // 값의 2씩 감소 ( downTo + step )
    for (i in 10 downTo 0 step 2) println(i)
    println()

    // ** '@'Label 로 반복문의 종료영역 선택하기 **
    for (y in 0..4 ){
        print("$y : ")
        for (x in 0..9){
            if (x==6) break // 안쪽 반복문 정지
            print("$x   ")
        }
        println()
    }
    println()

    kkk@ for (y in 0..4 ){
        print("$y : ")
        for (x in 0..9){
            if (x==6) break@kkk // kkk 라벨의 브레이크
            print("$x   ")
        }
        println()
    }
    println()

    // 반복문에 대한 사용연습은 배열과 함께 추가로 소개..

}