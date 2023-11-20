package com.nsw2022.ex91kotlinrecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    //뷰 참조변수들은 한번 참조함녀 다른 뷰객체로 바꾼적이 없음.
    val recyclerView:RecyclerView by lazy { findViewById(R.id.recycler_view) }

    // 대량의 데이터
    var items:MutableList<Item> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 대량의 데이터들을 추가 [ 실무에서는 서버에 json 데이터데이터 파싱해야 하지만.. ]
        items.add( Item("NEW YORK","hello new york",R.drawable.newyork))
        items.add( Item("PARIS","hello paris",R.drawable.paris))
        items.add( Item("SYDNEY","hello sydney",R.drawable.sydney))
        items.add( Item("NEW YORK","hello new york",R.drawable.newyork))
        items.add( Item("PARIS","hello paris",R.drawable.paris))
        items.add( Item("SYDNEY","hello sydney",R.drawable.sydney))
        items.add( Item("NEW YORK","hello new york",R.drawable.newyork))
        items.add( Item("PARIS","hello paris",R.drawable.paris))
        items.add( Item("SYDNEY","hello sydney",R.drawable.sydney))

        //리사이클러뷰에 이미 아답터 프로퍼티(멤버변수)가 있어서 별도의 아답터 참조변수 없이 그냥 생성하여 대입해주면 끝.
        recyclerView.adapter = MyAdapter(this,items)

        //리사이클러의 아이템 뷰들의 배치방법을 정하는 매니저 설정
        recyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)

    }

    override fun onResume() {
        super.onResume()

        // 보통 이때 새롭게 홤녀데이터 갱신하는 작업을 많이함
        // adapter nullable type 이여서 ?. 연산자 필요
        recyclerView.adapter?.notifyDataSetChanged()

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.aaa-> Toast.makeText(this, "aaa", Toast.LENGTH_SHORT).show()
            R.id.bbb-> Toast.makeText(this, "bbb", Toast.LENGTH_SHORT).show()
            R.id.ccc-> Toast.makeText(this, "ccc", Toast.LENGTH_SHORT).show()
            R.id.ddd-> Toast.makeText(this, "ddd", Toast.LENGTH_SHORT).show()
        }

        return super.onOptionsItemSelected(item)
    }

}