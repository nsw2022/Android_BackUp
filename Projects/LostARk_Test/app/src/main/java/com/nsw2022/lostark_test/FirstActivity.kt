package com.nsw2022.lostark_test

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.widget.Button
import android.widget.ImageView
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide

class FirstActivity : AppCompatActivity() {

    val btnLowa:CardView by lazy { findViewById(R.id.btn_lowa) }
    val btnInven:CardView by lazy { findViewById(R.id.btn_inven) }
    val ivChat:CardView by lazy { findViewById(R.id.iv_chat) }
    val iVcardView:ImageView by lazy { findViewById(R.id.iv_crad_view_mokoko) }
    val ivinvencardview:ImageView by lazy { findViewById(R.id.iv_fristlayout_inven) }

    val webLowa:WebView by lazy { findViewById(R.id.web_lowa) }

    var lowa:String = "https://loawa.com/"
    var inven:String = "https://m.inven.co.kr/lostark/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)

        btnLowa.setOnClickListener {
            intent=Intent(Intent.ACTION_VIEW, Uri.parse(lowa))
            startActivity(intent)
            //weblowa.loadUrl(lowa)error
        }

        btnInven.setOnClickListener {
            intent=Intent(Intent.ACTION_VIEW,Uri.parse(inven))
            startActivity(intent)
        }

        Glide.with(this).load(R.drawable.love_mokoko).into(iVcardView)
        Glide.with(this).load(R.drawable.fisrt_inven).into(ivinvencardview)
        ivChat.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                val intent:Intent=Intent(this@FirstActivity,IntroActivity::class.java)
                startActivity(intent)
            }
        })

    }
}