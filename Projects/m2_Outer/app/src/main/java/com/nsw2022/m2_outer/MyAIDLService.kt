package com.nsw2022.m2_outer

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
    override fun onBind(intent: Intent): IBinder {
        return object : MyAIdLInterface.Stub(){
            override fun getMaxDuration(): Int {
                return if (player.isPlaying)
                    player.duration
                else 0
            }

            override fun start() {
                if (!player.isPlaying)
                    player = MediaPlayer.create(this@MyAIDLService,R.raw.music)
                    try {
                        player.start()
                    }catch (e: Exception){
                        if (player.isPlaying)
                            player.stop()
                    }
            }
            override fun stop() {
                if (player.isPlaying)
                    player.stop()
            }
        }
    }
}