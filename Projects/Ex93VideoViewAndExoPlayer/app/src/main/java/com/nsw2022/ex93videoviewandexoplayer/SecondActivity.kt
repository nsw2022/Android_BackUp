package com.nsw2022.ex93videoviewandexoplayer

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.nsw2022.ex93videoviewandexoplayer.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    // ExoPlayer Library - VideoView 라이브러리

    val binding: ActivitySecondBinding by lazy { ActivitySecondBinding.inflate(layoutInflater) }

    // 실제 비디오를 플레이하는 플레이어 객체의 참조변수
    lateinit var exoPlayer: ExoPlayer

    // 비디오 uri
    val videoUri:Uri = Uri.parse("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //ExoPlayer 객체 생성해주는 Builder 를 통해 객체 생성
        exoPlayer= ExoPlayer.Builder(this).build()
        //플레이어뷰에 플레이어 연결하기
        binding.pv.player= exoPlayer

        // 비디오 1개 설정하기 ****************
        val mediaItem:MediaItem = MediaItem.fromUri(videoUri)
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
        exoPlayer.play()
        //***********************************************

        // 비디오 여러개를 순차적으로 플레이되도록.
//        val firstUri: Uri = Uri.parse("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4")
//        val secondUri: Uri = Uri.parse("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4")
//
//        val item1:MediaItem = MediaItem.fromUri(firstUri)
//        val item2:MediaItem = MediaItem.fromUri(secondUri)
//        exoPlayer.addMediaItem(item1)
//        exoPlayer.addMediaItem(item2)
//
//        exoPlayer.prepare()
//        exoPlayer.play()
//        exoPlayer.repeatMode= ExoPlayer.REPEAT_MODE_ALL


        //전체화면모드 버튼
        binding.btn.setOnClickListener {

            exoPlayer.pause() //video 일시정지

            val intent:Intent= Intent(this, FullscreenActivity::class.java)
            intent.data= videoUri // video uri 정보를 데이터로 전달

            //현재까지 재생된 위치정보를 추가데이터로 넘겨주기
            val currentPos:Long= exoPlayer.currentPosition
            intent.putExtra("currentPos", currentPos)
            startActivity(intent)
        }
    }

    override fun onPause() {
        super.onPause()
        exoPlayer.pause() //일시정지
    }


}