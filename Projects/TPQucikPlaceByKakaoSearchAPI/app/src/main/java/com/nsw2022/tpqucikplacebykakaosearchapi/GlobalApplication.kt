package com.nsw2022.tpqucikplacebykakaosearchapi

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication :Application() {
    override fun onCreate() {
        super.onCreate()

        //카카오 SDK 초기화 - 플랫폼에서발급된 네이티브 앱키
        KakaoSdk.init(this,"4909b8b026d2bd73b09ac25ac7ec219d")
    }
}