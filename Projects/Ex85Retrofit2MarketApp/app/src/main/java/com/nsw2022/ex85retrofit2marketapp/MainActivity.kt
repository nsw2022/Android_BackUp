package com.nsw2022.ex85retrofit2marketapp

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import com.nsw2022.ex85retrofit2marketapp.MarketItem
import com.nsw2022.ex85retrofit2marketapp.MarketAdapter
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import android.content.Intent
import com.nsw2022.ex85retrofit2marketapp.EditActivity
import android.content.pm.PackageManager
import android.view.View
import retrofit2.Retrofit
import com.nsw2022.ex85retrofit2marketapp.RetrofitHelper
import com.nsw2022.ex85retrofit2marketapp.RetrofitService
import android.widget.Toast
import com.nsw2022.ex85retrofit2marketapp.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    var binding: ActivityMainBinding? = null
    var items = ArrayList<MarketItem>()
    var adapter: MarketAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(
            layoutInflater
        )
        setContentView(binding!!.root)

        //리사이클러뷰 아답터연결
        adapter = MarketAdapter(this, items)
        binding!!.recyclerView.adapter = adapter
        //리사이클러뷰에 구분선 꾸미기( ItemDecoration - DividerItemDecoration )
        binding!!.recyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL
            )
        )

        //스와이프 리프레시 뷰를 드래그 하여 서버 데이터를 갱신
        binding!!.swipeRefreshLayout.setOnRefreshListener {
            loadData()
            binding!!.swipeRefreshLayout.isRefreshing = false // 로딩아이콘제거
        }
        binding!!.fabEdit.setOnClickListener { v: View? ->
            startActivity(
                Intent(
                    this,
                    EditActivity::class.java
                )
            )
        }

        //외부저장소에 대한 퍼미션 - 사진업로드를 위해 필요 - 2개를 요청해도 외부메모리사용 요청은 하나만 요청함.
        val permissions = arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_MEDIA_LOCATION
        )
        if (checkSelfPermission(permissions[0]) == PackageManager.PERMISSION_DENIED) {
            requestPermissions(permissions, 100)
        }
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    fun loadData() {
        //테스트로 우선 더미데이터를 추가해보기..
        items.add(MarketItem(1, "aa", "aaa", "aaa", "1000", "", "2022"))
        items.add(MarketItem(2, "bb", "bbb", "bbb", "2000", "", "2022"))

        //서버에서 market 테이블의 DB정보를 가져와서 대량의 데이터(items)에 추가..
        // Retrofit 을 이용하여 데이터 가져오기..
        val retrofit = RetrofitHelper.getRetrofitInstance()
        val retrofitService = retrofit.create(RetrofitService::class.java)
        val call = retrofitService.loadDataFromServer()
        call.enqueue(object : Callback<ArrayList<MarketItem>> {
            override fun onResponse(
                call: Call<ArrayList<MarketItem>>,
                response: Response<ArrayList<MarketItem>>
            ) {

                //기존 데이터들 모두 삭제
                items.clear()
                adapter!!.notifyDataSetChanged()

                //결과 json array를 곧바로 파싱하여 ArrayList<MarketItem> 로 변환된 리스트 받기
                val list = response.body()!!
                for (item in list) {
                    items.add(0, item)
                    adapter!!.notifyItemInserted(0)
                }
            }

            override fun onFailure(call: Call<ArrayList<MarketItem>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "error : " + t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}