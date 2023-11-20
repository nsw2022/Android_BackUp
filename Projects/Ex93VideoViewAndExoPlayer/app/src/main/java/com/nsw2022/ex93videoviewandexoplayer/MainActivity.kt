package com.nsw2022.ex93videoviewandexoplayer

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.MediaController
import com.nsw2022.ex93videoviewandexoplayer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // 비디오는 용량이 커서 앱안에 있기보다는 서버에 위치하는 경우가 많음 - 인터넷퍼미션

    val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    // Video Uri - 인터넷에 공개된 sample video url 사용
    var videoUrl= Uri.parse("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4")



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 비디오뷰에 재생/일지정지 등을 할 수 있는 '컨트롤바'를 붙여주는 작업
        binding.vv.setMediaController( MediaController(this) )

        //비디오뷰에 동영상의 uri 설정
        binding.vv.setVideoURI(videoUrl)

        // 동영상파일의 용량이 크기에 로딩하는 시간이 꽤 소요됨. 그렇기에 곧바로 start()하면
        // 동영상이 아직 로딩도지ㅣ 않았기에 실행되지 않을 수 있음.
        // 그래서 동영상의 로딩작업 준비가 끝났다는 것을 듣는 리스너를 이용하여 플레이함
        // 비디오뷰의 동영상을 시작!

        binding.vv.setOnPreparedListener( object : MediaPlayer.OnPreparedListener{
            override fun onPrepared(p0: MediaPlayer?) {
                binding.vv.start()
            }
        })

        //ExoPlayer 라이브러리 연습을 위해 SecondActivity로 이동
        binding.btn.setOnClickListener {
            startActivity(Intent(this,SecondActivity::class.java))
            finish()
        }

    }
}