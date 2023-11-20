package com.example.worldgame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.worldgame.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val binding : ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    var questions:MutableList<String> = mutableListOf("자동차","사과","코끼리","소금","1월","동물","사랑","나이","주소","은행")
    var answers:MutableMap<String, String> = mutableMapOf(
        "자동차" to "CAR",
        "사과" to "APPLE",
        "코끼리" to "ELEPHANT",
        "소금" to "SALT",
        "1월" to "JANUARY",
        "동물" to "ANIMAL",
        "사랑" to "LOVE",
        "나이" to "AGE",
        "주소" to "ADDRESS",
        "은행" to "BANK",
    )

    var num:Int= 0
    var score:Int= 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        startGame()

        binding.btn.setOnClickListener { clickBtn() }
        binding.btnNext.setOnClickListener { clickNext() }
    }

    private fun clickBtn(){
        val key= questions[num]
        binding.tvResult.text =
            if(binding.et.text.toString().equals( answers[key], true)) {
                score += 10
                "정답"
            }else {
                "오답"
            }

        binding.btn.visibility= View.INVISIBLE
        binding.btnNext.isEnabled= true

        if(num==9) binding.btnNext.text="FINISH"
    }

    private fun clickNext(){
        num++
        if(num>9) {
            finishGame()
            return
        }

        binding.tvNum.text= "${num+1}/10"
        binding.tvQuestion.text= questions[num]
        binding.tvResult.text= "결과"
        binding.et.setText("")

        binding.btn.visibility= View.VISIBLE
        binding.btnNext.isEnabled= false
    }

    private fun startGame(){
        questions.shuffle()
        num=0

        binding.tvQuestion.text= questions[num]
        binding.tvResult.text= "결과"
        binding.tvNum.text= "${num+1}/10"
        binding.et.setText("")

        binding.btn.visibility= View.VISIBLE
        binding.btnNext.isEnabled= false
        binding.btnNext.text="NEXT"

        binding.layoutGameOver.visibility= View.GONE
        score=0
    }

    private fun finishGame(){
        binding.layoutGameOver.visibility= View.VISIBLE
        binding.tvScore.text= "$score"

        binding.btnRestart.setOnClickListener { startGame() }
    }

}