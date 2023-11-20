package com.nsw2022.ex90kotlinhello

fun main(){
    // NullPointerException [NPE] 에 대한 앱의 버그를 문법적으로 막아주기 위한 Null safety 관련문법들

    // 코틀린은 null 값을 저장할 수 있는 타입을 명시적으로 구분하여 사용하도록 함.
    //var s1:String=null//ERROR  -  non nullable 변수
    var s2:String? = null // Ok - nullable
    println(s2)

    // Nullable 변수를 사용할 때 특이한 멤버 접근 연산자
    var str1:String = "Hello"  // non nullable type
    var str2:String? = "Nice" // nullable type

    // 위 두타입의 차이(String, String?)의 차이
    println("글자수: " + str1.length) // ok     - 반드시 객체가 있다고 확신
    //println("글자수: " + str2.length) // error  - String? 타입은 혹시 null 일수도 있기에.. 실제 객체를 참조하는지 여부와 상관없이 . 접근을 불허함

    // 해결방법? - null 인지를 확인하고 사용하기
    if (str2 !=null){
        // 이 영역안에서는 str2가 null 일수 없기에 . 연산자 접근을 허용
        println(str2.length)
    }

    // 근데 매번 String? 타입을 사용할때 마다 if 문을 쓰는 것은 매우 번거로움

    //1) ?. 연산자 - null safe 연산자
    println("글자수 : " + str2?.length) //null이 아닐때만 멤버에 접근. 만약 null 이면 . 멤버를 사용하지 않습니다

    str2=null
    println("글자수 : " + str2?.length) // null이 출력

    // 근데.. 결과가 null 이라는 것이 조금 마음에 들지 않음..
    // 그래서 null 일때는 글자수의 결과값으로 -1 을 주고 싶음
    val len:Int = if (str2!=null) str2.length else -1
    println("글자수 : $len")

    // 해결은 되지만 if else 문이 번거로워 보임
    // ?: 엘비스[Elvis] 연산자
    val len2= str2?.length ?: -1 // ?: 앞에 값이 null 이면 뒤의 값이 결과
    println(len2)
    println()

    // Java 처럼 NPE 발생해도 괜찮다고...하고싶다면
    // 3) !! non- null asserted 연산자
    var ss:String? = "Hello"
    //println("글자수 : " + ss.length) //ERROR
    println("글자수 : " + ss!!.length) //NULL 이라고 주장하는 방식

    // 진짜 null 을 참조하면..
    var sss:String? = null
    //println("글자수 : " + sss!!.length) //문법적 에러는 아니지만 실행하면 NPE 발생

    // 4) 안전한 형변환 연산자 as?
    val mmm:MMM?=MMM() // 제대로된 casting

    //var zzz:ZZZ?=MMM() // 에러 - 상관없는 클래스의 객체를 참조하는 것은 불가능

    //억지로 형변환 하여 참조시켜 보기기
    //var zzz:ZZZ?= MMM() as ZZZ? // 문법적 에러가 없어짐 하지만 실행하면 casting 예외가 발생함. 다운됨

    // 문법적 에허가 표시되지 않자 개발자가 형변환을 실수할 여지가 있음.
    // 안전한 형변환 연산자를 권함
    var zzz:ZZZ? = MMM() as? ZZZ? // 형변환 불가하면 null 로 결과를 줌줌

}
class MMM{
    var a=10
}

class ZZZ{
    var a=20
}