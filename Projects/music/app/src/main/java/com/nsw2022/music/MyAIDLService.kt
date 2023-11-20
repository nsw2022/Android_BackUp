package com.nsw2022.music

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder

class MyAIDLService : Service() {

    lateinit var player: MediaPlayer
    override fun onCreate() {
        super.onCreate()
        player = MediaPlayer()
    }

    override fun onDestroy() {
        player.release()
        super.onDestroy()
    }




}