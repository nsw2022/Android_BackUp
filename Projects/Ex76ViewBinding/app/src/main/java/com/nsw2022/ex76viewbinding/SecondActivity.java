package com.nsw2022.ex76viewbinding;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.nsw2022.ex76viewbinding.databinding.ActivitySecondBinding;

public class SecondActivity extends AppCompatActivity {

    ActivitySecondBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_second);
        binding=ActivitySecondBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btn.setOnClickListener(view -> binding.iv.setImageResource(R.drawable.sydney));
    }
}