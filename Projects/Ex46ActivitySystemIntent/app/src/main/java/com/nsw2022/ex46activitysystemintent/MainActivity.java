package com.nsw2022.ex46activitysystemintent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // #### 시스템용 인텐트를 이용하여 다른앱을 실행해보기 ####

        findViewById(R.id.btn_dial).setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_DIAL);//다이얼 앱 화면의 고정된 액션값

            // 미리 전화번호까지 선택 가능
            Uri uri = Uri.parse("tel:01012345678"); // 전화번호 리소스 정보 객체 생성
            intent.setData(uri);

            startActivity(intent);

        });


        findViewById(R.id.btn_sms).setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SENDTO);

            Uri uri = Uri.parse("smsto:01012345678,01011112222");
            intent.setData(uri);
            intent.putExtra("sms_body","Hello Nice to meet you");

            startActivity(intent);

        });

        findViewById(R.id.btn_web).setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_VIEW); // 액션을 생성자에 설정할 수 있음.
            intent.setData(Uri.parse("http://www.naver.com"));
            startActivity(intent);
        });

        findViewById(R.id.btn_gallery).setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*"); //MIME type

            startActivity(intent);
            // 실무에서는 선택한 사진 결과를 받와야 하기에.. startActivityForResult 여야함..
        });

        findViewById(R.id.btn_camera).setOnClickListener(view -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivity(intent);
            // 실무에서는 캡쳐한 사진 결과를 받와야 하기에.. startActivityForResult 여야함..
        });

    }
}