package com.nsw2022.ex91kotlinrecyclerview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class IntroActivity : AppCompatActivity() {

    // var btn:Button = findViewbyId(R.id.btn) 초기화 안하면 에러! 맴버변수에서는 findeView 를 할수가 없음
    var btn:Button?=null

    // 늦은초기화 lateinit 을 통해 지금 초기화 하지 않도록
    lateinit var btn2:Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        // btn 참조하기
        btn=findViewById(R.id.btn)
        // 버튼 클릭이벤트 처리를 위한 리스너 설정. Button? nullable type 은 멤버접근할때
        // nullsafe연산자 필요 [?.]
        btn?.setOnClickListener( object : View.OnClickListener {
            override fun onClick(p0: View?) {
                // MainActivity 실행
                val intent:Intent = Intent(this@IntroActivity, MainActivity::class.java)
                startActivity(intent)
            }
        } )

        //btn2번참조하기
        btn2=findViewById(R.id.btn2)
        // 버튼2에 대한 클릭이벤트 처리 리스너를 람다식보다 조금더 축약한 SAM 변환 처리
        //btn2.setOnClickListener ({ v -> finish() }) //람다식(익명함수)
        btn2.setOnClickListener { finish() }  //SAM변환

    }//onCreate


}