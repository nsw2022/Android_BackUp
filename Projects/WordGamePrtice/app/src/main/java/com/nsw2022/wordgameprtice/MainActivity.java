package com.nsw2022.wordgameprtice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    String[] eng = new String[]{
            "apple",
            "banana",
            "city",
            "dog",
            "everyone",
            "friday",
            "good",
            "hope",
            "impossible",
            "january"};

    String[] dat = new String[]{
            "사과",
            "바나나",
            "도시",
            "개",
            "모두",
            "금요일",
            "굳",
            "희망",
            "불가능",
            "6월"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}