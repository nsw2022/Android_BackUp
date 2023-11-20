package com.nsw2022.ex93videoviewandexoplayer

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.nsw2022.ex93videoviewandexoplayer.databinding.ActivityFullscreenBinding

class FullscreenActivity : AppCompatActivity() {

    lateinit var binding: ActivityFullscreenBinding

    lateinit var exoPlayer:ExoPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityFullscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        exoPlayer= ExoPlayer.Builder(this).build()
        binding.pv.player= exoPlayer

        // Intent로 부터 전달받은 데이터(video uri)와 추가데이터(재생위치) 얻어오기
        val videoUri: Uri? = intent.data
        var currentPos: Long = intent.getLongExtra("currentPos", 0)

        val mediaItem:MediaItem = MediaItem.fromUri(videoUri!!)
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()

        //재생 위치로 seekbar 이동
        exoPlayer.seekTo(currentPos)
        exoPlayer.play()

        exoPlayer.repeatMode= ExoPlayer.REPEAT_MODE_ALL
    }

    override fun onPause() {
        super.onPause()
        exoPlayer.pause()//일시정지
    }
}