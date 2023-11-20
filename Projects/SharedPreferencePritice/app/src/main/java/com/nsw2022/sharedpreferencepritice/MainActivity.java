package com.nsw2022.sharedpreferencepritice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.nsw2022.sharedpreferencepritice.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnSave.setOnClickListener(v->saveData());
        binding.btnLoad.setOnClickListener(v->loadData());

    }//onCrate

    void saveData(){
        String name = binding.etName.getText().toString();
        int age = Integer.parseInt(binding.etAge.getText().toString());

        SharedPreferences pref = getSharedPreferences("Data",MODE_PRIVATE);

        SharedPreferences.Editor editor = pref.edit();

        editor.putString("name",name);
        editor.putInt("age",age);

        editor.commit();

        Toast.makeText(this, "저장완료", Toast.LENGTH_SHORT).show();

    }
    void loadData(){
        SharedPreferences pref = getSharedPreferences("Data",MODE_PRIVATE);

        String name=pref.getString("name","");
        int age=pref.getInt("age",0);

        binding.tv.setText(name + " : " + age);

    }

}