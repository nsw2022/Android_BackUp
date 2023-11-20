package com.nsw2022.ex89firebasecloudmessagepush;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.nsw2022.ex89firebasecloudmessagepush.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    // Firebase 와 연동

    ActivityMainBinding binding;
   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btn.setOnClickListener(v->clickBtn());
        binding.btn2.setOnClickListener(v->clickBtn2());
        binding.btn3.setOnClickListener(v->clickBtn3());
    }

    void clickBtn2(){
            FirebaseMessaging.getInstance().subscribeToTopic("FCM").addOnCompleteListener(new OnCompleteListener<Void>() {//주제 정하는거 ""뭘와도 상관없
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "FCM 주제 구독 하셨습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    
    void clickBtn3(){
       FirebaseMessaging.getInstance().unsubscribeFromTopic("FCM").addOnCompleteListener( task->{
           if (task.isSuccessful()) Toast.makeText(this, "FCM 주제 구독을을 취소하셨습니다.", Toast.LENGTH_SHORT).show();
       } );
    }

    void clickBtn(){
        //FCM 서버에 등록된 ID 토큰값 받기
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if(task.isSuccessful() ){
                    // 토큰내과
                    String token = task.getResult();
                    // 토큰값 확인 - 실무에서는 웹서버에 이 토큰 값을 전송
                    Log.i("TOKEN",token);
                    Toast.makeText(MainActivity.this, ""+token, Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}