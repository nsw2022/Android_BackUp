package com.nsw2022.pr3retrofit

import androidx.appcompat.app.AppCompatActivity
import com.nsw2022.pr3retrofit.MyAdapter
import android.os.Bundle
import android.view.View
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.nsw2022.pr3retrofit.RetrofitService
import android.widget.Toast
import com.nsw2022.pr3retrofit.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    var binding: ActivityMainBinding? = null
    var movies = ArrayList<Movie>()
    var adapter: MyAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        binding!!.btn.setOnClickListener { v: View? -> clickBtn() }
    }

    fun clickBtn() {
        binding!!.btn.visibility = View.INVISIBLE
        val key = "f5eef3421c602c6cb7ea224104795888"
        val targetDt = "20221019"
        val builder = Retrofit.Builder()
        builder.baseUrl("http://kobis.or.kr")
        builder.addConverterFactory(GsonConverterFactory.create())
        val retrofit = builder.build()
        val retrofitService = retrofit.create(RetrofitService::class.java)
        val call = retrofitService.getMethodTest(key, targetDt)
        call.enqueue(object : Callback<Result?> {
            override fun onResponse(call: Call<Result?>, response: Response<Result?>) {
                val result = response.body()
                adapter = MyAdapter(this@MainActivity, result!!.boxOfficeResult.dailyBoxOfficeList)
                binding!!.recyclerview.adapter = adapter
            }

            override fun onFailure(call: Call<Result?>, t: Throwable) {
                Toast.makeText(this@MainActivity, "error" + t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}