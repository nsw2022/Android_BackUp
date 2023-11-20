package com.nsw2022.tpqucikplacebykakaosearchapi.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import com.nsw2022.tpqucikplacebykakaosearchapi.databinding.ActivityPlaceUrlactivityBinding

class PlaceURLActivity : AppCompatActivity() {
    val binding:ActivityPlaceUrlactivityBinding by lazy { ActivityPlaceUrlactivityBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.wv.webViewClient = WebViewClient() //현재 웹뷰안에 웹 문서를 열리도록 하는 설정
        binding.wv.webChromeClient = WebChromeClient() // 웹페이지의 다이얼로그 같은 것들이 발동하도록 하는 설정

        binding.wv.settings.javaScriptEnabled=true // 웹뷰에서 js를 실행하도록 설정.

        var placeUrl:String = intent.getStringExtra("place_url") ?: ""
        binding.wv.loadUrl(placeUrl)

    }

    override fun onBackPressed() {
        if(binding.wv.canGoBack()) binding.wv.goBack()
        else super.onBackPressed()
    }
}