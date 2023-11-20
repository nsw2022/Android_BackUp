package com.nsw2022.ex90kotlinhello

fun main(){
    // 안드로이드에서 많이 사용되는...
    //1) 이너클래스 & 인터페이스 & 익명클래스
    //2) static 의 대체기능인 companion object[동반객체]
    //3) 늦은 초기화 - lateinit , by lazy

    //1. 이너클래스
    //var obj= AAA.BBB() //이너클래스는 아웃터클래스에서만 생성가능함
    var obj= AAA()
    var obj2= obj.makeBBBInstance()
    obj2.show()

    //2. 인터페이스
    //val c= Clickable()  //에러 : 인터페이스는 곧바로 객체 생성이 불가능
    //Clickable을 구현한 Test클래스 객체 생성
    val t= Test()
    t.onClick()

    //3. 익명클래스
    val a= object : Clickable{
        override fun onClick() {
            println("Anonymous class onClick!!")
            println()
        }
    }
    a.onClick()

    //4. 동반객체[ companion object ] - Java의 static 키워드와 유사한 기능 : 객체생성없이 사용가능한 멤버들 ]
    //println( Sample.n ) //클래스의 일반멤버는 객체를 생성할때만 사용가능
    println( Sample.title ) //클래스와 동반된 객체의 멤버를 클래스것인양 사용
    Sample.title="robin"
    Sample.showTitle()
    println()

    //5.  늦은 초기화 - 클래스의 멤버변수의 초기화를 미룰 수 있는 키워드
    val h= Hello()
    h.onCreate()
    h.show()
}

//5.
class Hello{

    //var name:String //ERROR : 프로퍼티는 선언하면서 값을 초기화 해야만 함

    //만약 나중에 하고 싶다면? [ 안드로이드에서 View들 참조변수들은 onCreate()에서 초기화 이루어져야 함]
    //5.1) lateinit
    lateinit var name:String

    fun onCreate(){
        name="sam"   //이때 초기화
    }

    fun show(){
        println("name : $name")
    }

    // ** lateinit 키워드 사용할때 주의할 점 ***
    //1) lateinit 키워드는 null초기화는 불가능 [즉, String? 타입은 불가능]
    //lateinit var title:String?  //ERROR

    //2) 기초타입 자료형에서는 사용불가능 [ Boolean, Int, Double .... 8개 ]
    //lateinit var age:Int //ERROR

    //3) val 키워드로 만든 읽기전용 변수에는 사용이 불가능
    //lateinit val address:String  //ERROR
    //***********************************************************

    //5.2) val 변수의 늦은 초기화 키워드 [ by lazy ]
    val address:String by lazy { "seoul" } // {}안에 있는 값을 지금 대입하지 않고.. 처음 이 변수가 사용될때 대입해줌.

    val title:String by lazy {
        "Hello title"
        "aaaaa"
        println("aasdfasdf")
        "zzzzzz"
    }

    // by lazy는 lateinit과 다르게 기초타입도 가능함
    val age:Int by lazy { 20 }

    // nullable type 도 가능함
    val message:String? by lazy { "Hello by lazy..." }
    val message2:String? by lazy { null }

    //연산에 의한 초기화도 가능함
    val message3:String? by lazy {
        if(age<20) "미성년자"
        else "성인"
    }

    // 단, by lazy 는 var에는 사용불가

    //당연하게도.. 개발자가 만든 클래스의 객체 생성도 by lazy로 가능
    val person:Person by lazy { Person("sam", 20) }

}


//4.
class Sample{
    var n:Int=10

    //코틀린은 static 키워드 존재하지 않음.
    //객체 생성없이 사용가능한 멤버들 [동반객체 - 클래스설계도와 같이 존재하는 객체]
    companion object{
        var title:String= "sam" //java의 static 변수 같은 역할

        fun showTitle(){        //java의 static 메소드 같은 역할
            println("제목 : $title")
            //n= 50 //error -- 동반객체안에서 클래스의 일반멤버는 사용불가
        }
    }
}


// 인터페이스를 구현할때  ":" 키워드 사용. 상속과 다르게 생성자호출 ()가 없음.
class Test : Clickable{
    override fun onClick() {
        println("clicked!!")
        println()
    }
}

//2. 인터페이스는 특별하게 다른점 없음
interface Clickable{
    //추상메소드
    fun onClick()
}


//1.
class AAA{
    var a:Int= 0

    fun show(){
        println("AAA클래스의 show")
        println()
    }

    //이너클래스를 객체로 만들어서 리턴해주는 기능메소드
    fun makeBBBInstance():BBB{
        return BBB()
    }

    // 이너클래스 [ 자바와 다르게 inner 라는 키워드 필요함 ]
    inner class BBB{

        fun show(){
            println("아웃터 클래스의 멤버변수(프로퍼티) a : $a") //아웃터의 멤버를 내것인양.

            //아웃터 클래스의 멤버메소드도 사용해보기
            this@AAA.show()
        }
    }////////////////////////////////////////////////////

}//AAA class..