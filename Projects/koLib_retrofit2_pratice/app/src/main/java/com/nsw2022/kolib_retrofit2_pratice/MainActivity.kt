package com.nsw2022.kolib_retrofit2_pratice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class MainActivity : AppCompatActivity() {

    var key:String="4d6e42445a746d6437354e65737162"
    val btn:Button by lazy { findViewById(R.id.btn) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn.setOnClickListener {
            clickBtn() }
    }

    fun clickBtn(){
        var builder=Retrofit.Builder()
        builder.baseUrl("http://openapi.seoul.go.kr:8088/")
        builder.addConverterFactory(GsonConverterFactory.create())
        val retrofit=builder.build()




    }
}