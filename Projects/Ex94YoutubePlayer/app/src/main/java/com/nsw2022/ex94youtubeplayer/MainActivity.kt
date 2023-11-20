package com.nsw2022.ex94youtubeplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.google.android.youtube.player.*
import com.nsw2022.ex94youtubeplayer.databinding.ActivityMainBinding

//youtube 동영상 전용 플레이어 라이브러리.[ 구글 개발자 사이트를 참조하여 라이브러리 적용 ]

class MainActivity : AppCompatActivity() {

    // Youtube 동영상 전용 플레이어 라이브러리. [구글 개발자 사이트를 참조하여여 라이러리 적용 ]

    val binding:ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // YoutubePlayerFragment 를 찾아오기 [ supportFragmentManager는 유튜브 프레그먼트 못 찻음 ]
        val youtubeFragment: YouTubePlayerFragment = fragmentManager.findFragmentById(R.id.youtube_fragment) as YouTubePlayerFragment

        // 유튜브프레그먼트의 초기화 작업.
        // 첫번째 파라미터 String: 프레그먼트가 여러개 있을때 구별하는 식별글씨
        youtubeFragment.initialize("aaa", object : YouTubePlayer.OnInitializedListener{
            override fun onInitializationSuccess(
                p0: YouTubePlayer.Provider?,
                p1: YouTubePlayer?,
                p2: Boolean
            ) {
                p1?.loadVideo("_DlWf1L2q5U") //유뷰트 동영상 식별 ID. [ 유뷰트 url 뒤에 ?v= 다음에 있는 글씨 ]
            }

            override fun onInitializationFailure(
                p0: YouTubePlayer.Provider?,
                p1: YouTubeInitializationResult?
            ) {
                Toast.makeText(this@MainActivity, "fail", Toast.LENGTH_SHORT).show()
            }
        })


        val youtubeFragment2: YouTubePlayerFragment = fragmentManager.findFragmentById(R.id.youtube_fragment2) as YouTubePlayerFragment

        // 유튜브프레그먼트의 초기화 작업.
        // 첫번째 파라미터 String: 프레그먼트가 여러개 있을때 구별하는 식별글씨
        youtubeFragment2.initialize("bbb", object : YouTubePlayer.OnInitializedListener{
            override fun onInitializationSuccess(
                p0: YouTubePlayer.Provider?,
                p1: YouTubePlayer?,
                p2: Boolean
            ) {
                p1?.cueVideo("0nheM1eXgJQ") //유뷰트 동영상 식별 ID. [ 유뷰트 url 뒤에 ?v= 다음에 있는 글씨 ]
            }

            override fun onInitializationFailure(
                p0: YouTubePlayer.Provider?,
                p1: YouTubeInitializationResult?
            ) {
                Toast.makeText(this@MainActivity, "fail", Toast.LENGTH_SHORT).show()
            }
        })

        binding.thumb.initialize("xxx", object : YouTubeThumbnailView.OnInitializedListener{
            override fun onInitializationSuccess(
                p0: YouTubeThumbnailView?,
                p1: YouTubeThumbnailLoader?
            ) {
                p1?.setVideo("alLs9S4pwo0")
            }

            override fun onInitializationFailure(
                p0: YouTubeThumbnailView?,
                p1: YouTubeInitializationResult?
            ) {
                TODO("Not yet implemented")
            }

        })




    }
}