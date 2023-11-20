package com.nsw2022.ex90kotlinhello

fun main(){
    // 코틀린의 class 상속
    val obj:Second = Second()
    obj.a=10    //상속받은 멤버를 내것인양 사용하는 기술
    obj.b=200
    obj.show()

    // 업캐스팅 : 부모참조변수로 자식객체 참조가 가능하다
    var f:First=Second() //up casting
    f.show()//자식객체의 오버라이드 메소드사용가능
    //f.aaa()//참조는 되지만 자식고유의 기능사용은 불가능
    println()

    //자식 참조변수를 만들어야만 자식객체 고유기능 사용 가능
    //var s:Second=f // error : 자식참조변수에 부모객체를 줬다고 오인함.
    var s:Second=f as Second // as연산자 : 형변환 연산자 [ 다운캐스팅 ]
    s.aaa()
    s.show()
    println("------------------------------------------------------")
    println()

    // 상속에 대한 마무리 연습
    // [ 일반, 학생, 교수, 근로장학생 ] 클래스 설계
    var p=Person("sam",20)
    p.show()
    println()

    var stu=Student("robin",22,"android")
    stu.show()
    println()

    val pro=Professor("park",45,"mobile optimization")
    pro.show()

    val alba= AlbaStudent("hong",27,"ios","pc management")
    alba.show()
}

//First 상속받을 클래스 [ 상속키워드 ":" ]
// 반드시 상속해주는 클래스명 옆에 ()를 통해 생성자를 호출해줘야함
class Second() : First(){
    var b:Int = 20

    // 상속받은 show() 메소드의 기능이 맘에 들지 않아
    // 새로 만들어 내는 : MethodOverride
    // 코틀린은 명시적으로 오버라이드 메소드임을 표기해야 만함. override키워트 추가
    // 오버라이드할 메소드가 open 되어 있어야만 오버라이드가 가능함
    override fun show(){
        //println("a : $a")//부모의 멤버 출력
        //부모의 멤버출력은 부모의 기능을 이용하는 것을 권장
        super.show()
        println("b : $b")
        println()

    }

    fun aaa(){
        println("Second aaa method")
    }
}

//상속을 해준 부모 클래스 - 상속해주는 클래스는 반드시 open 키워드가 필요함
open class First(){
    var a:Int=10

    // 오버라이드를 허용하려면 open 키워드 필요
    open fun show(){
        println("First a : $a")
    }
}