package com.nsw2022.kotodo2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var list:MutableList<ItemVO> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivityForResult()
        }


    }

    private fun startActivityForResult() {
        TODO("Not yet implemented")
    }


}