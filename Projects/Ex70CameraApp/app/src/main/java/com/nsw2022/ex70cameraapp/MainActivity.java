package com.nsw2022.ex70cameraapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv=findViewById(R.id.iv);

        findViewById(R.id.btn).setOnClickListener(view -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            resultLauncher.launch(intent);

        });
    }

    ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode()!= RESULT_CANCELED){

                Intent intent = result.getData();
                Uri uri = intent.getData();

                // 마시벨로우버전 이상 폰에서는 개발자가 실행한 카메라앱을 통해 캡쳐한 사진이미지를
                // 파일로 저장하지 않고. 섬네일 이미지만 Bitmap 객채로 리턴해 줌.
                if (uri != null){ // 사진이 파일로 저장된 경우..
                    Glide.with(MainActivity.this).load(uri).into(iv);
                    Toast.makeText(MainActivity.this, "uri로 사진정보 취득", Toast.LENGTH_SHORT).show();
                }else{//사진의 섬네일만 Bitmap으로 받아오는 경우..

                    Toast.makeText(MainActivity.this, "bitmap으로 섬네일정보 취득", Toast.LENGTH_SHORT).show();

                    // 섬네일 이미지 데이터는 uri로 오지않고 추가데이터로 Intent를 통해 전달되어 옴.
                    Bundle bun-le = intent.getExtras();
                    Bitmap bm = (Bitmap) bundle.get("data");

                    Glide.with(MainActivity.this).load(bm).into(iv);

                    // 섬네일 이미지는 작은 이미지여서 크게보면 화질이 깨짐.
                    // 그래서 카메라 앱을 실행하면서.. 파일로 저장되도록 하여. Uri를 얻어오도록 코딩..

                }

            }
        }
    });
}