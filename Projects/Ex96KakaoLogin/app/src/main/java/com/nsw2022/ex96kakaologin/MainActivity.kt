package com.nsw2022.ex96kakaologin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk.keyHash
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import com.nsw2022.ex96kakaologin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val binding:ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        var keyHash:String = Utility.getKeyHash(this)
        Log.i("KEY","$keyHash")

        binding.btnLogin.setOnClickListener{clickLogin()}

    }

    private fun clickLogin(){
        // 권장 : 카카오톡 로그인을 먼저 시도하고 불가능할 경우 카카오계정 로그인을 시도.

        // 로그인 요청 결과에 반응 하는 콜백 함수
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (token != null){
                Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
                loadUserInfo() //사용자 정보 읽어오기
            }else{
                Toast.makeText(this, "${error?.message}", Toast.LENGTH_SHORT).show()
            }
        }

        // 디바이스에 카톡이 설치되어 있는지 확인..
        if ( UserApiClient.instance.isKakaoTalkLoginAvailable(this) ){

            // 카카오톡 로그인 요청
            UserApiClient.instance.loginWithKakaoTalk(this, callback = callback )

        }else{

            // 카카오계정으로 로그인 요청
            UserApiClient.instance.loginWithKakaoAccount(this, callback = callback )
        }
    }

    // 로그인 성공했을대 사용자 정보 얻어오는 기능 메소드
    fun loadUserInfo(){

        UserApiClient.instance.me { user, error ->
            if (user != null){
                binding.tvNick.text=user.kakaoAccount?.profile?.nickname
                binding.tvEmail.text=user.kakaoAccount?.email
                binding.tvId.text=user.id.toString()

                Glide.with(this).load(user.kakaoAccount?.profile?.profileImageUrl).into(binding.civ)
            }
        }
    }
}