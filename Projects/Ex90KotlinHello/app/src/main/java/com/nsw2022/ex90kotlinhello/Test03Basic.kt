package com.nsw2022.ex90kotlinhello

import android.util.Log.v
import java.util.ArrayList
import java.util.HashMap
import java.util.HashSet

fun main(){
    //6. 배열 Array 및 컬렉션 Collection
    //6.1 배열 Array - 요소의 개수변경이 불가능한 배열
    //var arr:Array<Int> = arrayOf(10,20,30)
    //데이터 타입의 자동추론기능도 가능함
    var arr= arrayOf(10,20,30)
    //요소값 출력
    println( arr[0] )
    println( arr[1] )
    println( arr[2] )
    //println( arr[3] ) //인덱스번호가 틀리면 Exception 발생
    println()

    //요소값 변경해보기
    arr[0]= 100
    println( arr[0] )
    println()

    //코틀린의 특이점. 배열 요소에 접근할때
    // []기호 대신에 .get() 메소드 이용가능
    println( arr.get(0) )
    println( arr.get(1) )
    println( arr.get(2) )
    println()

    //값 대입도 []연산자 대신에 .set()메소드로 대입 가능
    arr.set(1, 200) //1번방에 200값 대입
    println( arr[0] )
    println( arr[1] )
    println( arr[2] )
    println()

    // 배열의 길이 [자바의 .length]
    println("배열의 요소길이 : ${arr.size}")
    println()

    // 요소값 출력을 한줄씩 쓰기 짜증!! 반복문 이용
    for(i in 0 until 3){
        println( arr[i] )
    }
    println()

    for(i in 0 until arr.size){
        println( arr[i] )
    }
    println()

    // Java의 foreach 문법과 유사함
    for( i in arr){ // i가 요소값
        println(i)
    }
    println()

    // for 문의 in을 인덱스 번호로 얻고싶다면
    for( i in arr.indices ){
        println(i)
    }
    println()

    // 인덱스 + 요소값을 받아오는 for문
    for( (i,v) in arr.withIndex() ){
        println("[ $i ] : $v")
    }

    // 함수형 프로그래밍 언어들의 배열처럼
    // 요소값 각각을 반복적으로 접근할때마다
    // {}의 코드가 실행되도록 하는 forEach 기능 있음.
    // {}안에서는 생략된 변수 it 이 있으며.. it이 요소의 값을 가지고 있음. [배열요소의 자료형으로 자동 지정됨 ]
    arr.forEach {
        println(it)
    }
    println()


    // arrayOf() 배열의 특이점
    // 배열요소의 자료형을 다르게 만들 수 있음. - 자동 추론은 각 요소는 모두 Any타입이 됨.
    var arr2= arrayOf(10, "Hello", true)

    //값 가져오는 것.. 문제없음
    println( arr2[0] )
    println( arr2[1] )
    println( arr2[2] )
    println()

    //요소값 대입
    arr2[0]= 20
    arr2[1]= "bbb"
    arr2[2]= 3.14 //원래 자료형 변경도 됨.
    println()

    // 하지만 요소의 타입이 Any 타입으로 지정된 것이이서 곧바로 연산에 사용은 불가능
    //println( arr2[0] + 5 )  // Any + Int :ERROR
    println( arr2[0] as Int + 5 ) // as 연산자 : 형변환 연산자
    println( arr2[1] as String + "kkk" )
    println( arr2[2] as Double + 5.5 )

    //그래서 보통 배열을 만들때는 타입을 명시하는 것이 선호됨. 같은 자료형만 있어야 하기에..
    var aaa= arrayOf<Int>(10,20,30)

    //<Int> 제네릭 표기법이 보기 싫다면..
    var bbb= intArrayOf(1,2,3)
    var ccc= doubleArrayOf(3.14, 2.22, 5.55)
    var ddd= booleanArrayOf(true, false, true)
    // 기초타입 자료형은 모두 xxxArrayOf() 가 있음.
    //var eee= stringArrayOf() //error

    // 배열 참조변수의 자료형을 먼저 지정하고 대입은 나중에
    var eee:IntArray
    eee= intArrayOf(100,200,300)

    // 배열을 만들때 빈 배열로 ..
    // ex) 5칸짜리 빈 배열.. 요소값의 초기값은 null
    var fff= arrayOfNulls<Int>(5)
    for( t in fff){
        println(t)
    }
    println()

    // nullable 변수에 대한 구분
    var hhh:Array<Int> = arrayOf(10,20)  //요소값에 null 넣으면 에러
    var nnn:Array<Int?> = arrayOf(10,20,null) //요소값에 null 허용
    var mmm:Array<Int>? = arrayOf(10,20) //mmm 배열 참조변수가 null을 참조할 수 있음

    // 배열 : 같은 자료형의 요소들이 여러개 존재
    // 배열은 요소의 개수를 변경할 수 없다!!

    //6.2 컬렉션 : Java의 Collection 과 같은 목저의 클래스들 [ List, Set, Map ]
    // 1) List : 요소가 순서대로 저장, 인덱스번호, 중복데이터 허용
    // 2) Set  : 순서대로 저장안됨. 인덱스번호 없음. 중복데이터 불허
    // 3) Map  : 순서대로 저장 안됨. 인덱스번호 대신에 [키]식별자 사용. 중복데이터 허용

    // 코틀린의 Collection 들은 크게 2가지 분류로 나뉘어짐.
    // 6.2.1 요소의 개수의 추가/삭제 및 값 변경이 불가능한 컬렉션 : listOf(), setOf(), mapOf()
    // 6.2.2 요소의 개수의 추가/삭제 및 값 변경이 가능한 컬렉션   : mutableListOf(), mutableSetOf(), mutableMapOf()

    // 6.2.1 요소의 개수의 추가/삭제 및 값 변경이 불가능한 컬렉션
    // 1) List
    var list: List<Int> = listOf(10,20,30,20) // 중복데이터 허용
    for ( t in list ){
        println(t)
    }
    println()

    //값의 추가/삭제/변경에 대한 기능메소드가 없음.
    //list.add(20)// error
    //list.remove(0)//error
    //list.set(1, 50)//error


    // 2) Set
    val set: Set<Double> = setOf(3.14,5.55,2.22,5.55,1.56)//중복데이터를 넣으면 무시됨
    for (e in set) println(e)
    println()


    // 3) Map
    // 3.1) Pair()객체를 이용한 키-벨류 지정
    val map: Map<String, String> = mapOf(Pair("title","Hello")  , Pair("msg","Nice Meet you") ,
        Pair("No","5"))
    println("요소개수 : ${map.size}")

    for ( (key, value ) in map) {
        println("$key : $value")
    }
    println()



    // 3.2) to 연산자를 이용한 키-벨류 지정
    val map2: Map<String, String> = mapOf("id" to "tmddn3410", "pw" to "1234")
    for ( (k, v) in map2) println("$k : $v")
    println()

    // 6.2.2 요소의 개수의 추가/삭제 및 값 변경이 가능한 컬렉션 : Mutable
    // 1) MutableListOf
    val aaaa: MutableList<Int> = mutableListOf(10,20,30)
    println("요소개수 : ${aaaa.size}")
    aaaa.add(40)
    aaaa.add(0,50)//특정위치 추가되며 뒤로 밀림
    println("요소개수 : ${aaaa.size}")
    //aaaa.set(1,200)// 인덱싱 []방식을 권장
    aaaa[1]= 200 //마치 배열사용하듯이.
    for (e in aaaa) println(e)
    println()

    // MutableSetOf
    val bbbb: MutableSet<Double> = mutableSetOf()
    println("요소갯수 : ${bbbb.size}")
    bbbb.add(5.55)
    bbbb.add(3.14)
    bbbb.add(5.55) //자동무시
    println("요소갯수 : ${bbbb.size}")
    for (e in bbbb) println(e)
    println()


    // mutableMapOf()
    val cccc: MutableMap<String, String> = mutableMapOf("name" to "sam", Pair("tel","01012345678"))
    println("요소의 개수 : ${cccc.size}")
    cccc.put("address","seoul")
    println("요소의 개수 : ${cccc.size}")
    for ( (k,v) in cccc) println("$k,$v")
    println()

    //** 보통 앱개발에서 사용하는 Collection 은 ArrayList 처럼
    // 요소 개수가 변경될 수 있는 경우가 많기에 MutableXXX()를 더 많이 사용함.

    // 6.2.3 Mutable 이 어색하다면.. 자바의 ArrayList, HashSet, HashMap 에 대응하는 클래스가 있음
    val arrList: ArrayList<Any> = arrayListOf(10,"Hello",true)
    for (e in arrList) println(e)
    println()

    // 요소 추가/삭제/변경 기능메소드는 모두 똑같이 존재함.
    arrList.add(20)
    arrList.removeAt(0)
    arrList.forEach{
        println(it)
    }
    println()
    val hashMap: HashMap<String, String> = hashMapOf("apple" to "사과")
    val hashSet: HashSet<Double> = hashSetOf(5.55,4.33)

    // 자바에 익숙한 개발자는 기존의 Collection 클래스를 사용해도 무방함
    // 그렇지 않다면.. MutableXXX 사용을 권함.
    //================================================================================

    // 6.3 2차원 배열!!
    val arrays= arrayOf( arrayOf(10,20,30)  , arrayOf("aa","bb")   , arrayOf(true,false)  )
    for (arr in arrays  ){
        for (e in arr){
            print("$e    ")
        }
        println()
    }
    println()


    //6.4 Null 값 저장을 무시 컬랙션
    val aaaaa: List<String?> = listOf(null,"nice")
    for (e in aaaaa) println(e)
    println()

    val aaaaa2: List<String?> = listOfNotNull(null,"aaa")//null 값은 자동 무시됨
    for (e in aaaaa2) println(e)
    println()

    // setOfNotNull() 은 있음.
    // mapOfNotNul() 은 없음.

}
