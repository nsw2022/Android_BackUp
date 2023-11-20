package com.nsw2022.ex69galleryappphotopick;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

public class MainActivity extends AppCompatActivity {

    PhotoView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv=findViewById(R.id.iv);
        findViewById(R.id.btn).setOnClickListener(view -> {
            //갤러리앱 or Photo 앱
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*"); //미매타입
            //startActivity(intent); //이렇게 실행하면 선택된 사전정보를 돌려받을수 없음.

            //갤러리앱을 실행하여 사용자가 선택한 사진의 경로정보(Uri) 다시 가져와야하기에
            //"결과를 받기위한 액티비티실행"을 해야함. 이를 해주는 중간책 같은 객체가 존재함
            // ActivityResultLauncher
            resultLauncher.launch(intent);


        });
    }//onCreate

    // 결과를 받기위한 액티비티를 실행시켜주는 객체 등록 및 생성
    ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            // 갤러리앱을 실행한 사용자가 사진을 선택하지 않고 돌아왔을 수도 있어서 확인

            if (result.getResultCode() != RESULT_CANCELED){
                // 결과를 가지고 돌아온 Intent 객체를 소환
                Intent intent = result.getData();
                // Intent에게 선택한 사진경로 Uri 데이터를 받기
                Uri uri = intent.getData();
                // 이미지 load library 사용 : Glide or Picasso
                Toast.makeText(MainActivity.this, ""+uri, Toast.LENGTH_SHORT).show();
                Glide.with(MainActivity.this).load(uri).into(iv);



            }else {

            }


        }
    });

}