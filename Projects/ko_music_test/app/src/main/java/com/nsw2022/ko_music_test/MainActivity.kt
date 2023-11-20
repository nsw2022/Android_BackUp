package com.nsw2022.ko_music_test


import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


class MainActivity : AppCompatActivity() {
    private  var mediaPlayer: MediaPlayer? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        MediaPlayer.create(this,R.raw)

    }
}