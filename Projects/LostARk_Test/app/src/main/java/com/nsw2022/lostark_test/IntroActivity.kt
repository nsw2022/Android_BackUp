package com.nsw2022.lostark_test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.nsw2022.lostark_test.databinding.ActivityIntroBinding

class IntroActivity : AppCompatActivity() {
    lateinit var binding:ActivityIntroBinding
    val btnBack: Button by lazy { findViewById(R.id.btn_back) }
    val btnStart:Button by lazy { findViewById(R.id.btn_start) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener{onBackPressed()}
        binding.btnStart.setOnClickListener{
            intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
        }


    }
}