package com.nsw2022.ex86firebasestorage;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.nsw2022.ex86firebasestorage.databinding.ActivityMainBinding;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    //Firebase : 서버쪽 작업을 대신 해주는 사이트(플랫폼)

    //이 프로젝트와 파이어베이스를 연동하기
    // Firebase 사이트의 콘솔로 이동하여 [프로젝트 만들기] 통해 연동 - 연동방법은 작업순서대로 따라하면 됨.

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnLoad.setOnClickListener(v->clickLoad());
        binding.btnSelect.setOnClickListener(v->clickSelect());
        binding.btnUpload.setOnClickListener(v->clickUpload());

    }
    void  clickSelect(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        resultLauncher.launch(intent);
    }

    ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_CANCELED) return;

            imgUri=result.getData().getData();
            Glide.with(MainActivity.this).load(imgUri).into(binding.iv);
        }
    });

    Uri imgUri=null;

    void clickUpload(){

        //Firebase Storage 에 파일 업로드 하기

        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();

        //업로드할 파일이 저장될 파일명을 날짜를 이용하여 정하기
        SimpleDateFormat sdf= new SimpleDateFormat("yyyyMMddhhmmss");
        String fileName = "IMG_" + sdf.format(new Date()) + ".png";

        // 저장할 파일위치에 대한 참조객체 얻어오기
        StorageReference imgRef= firebaseStorage.getReference("upload/"+fileName); // 만약 [upload]라는 폴더가 없으면 자동 생성

        // 사진앱에서 선택한 이미지 파일을 넣기(업로드)
        imgRef.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(MainActivity.this, "upload success", Toast.LENGTH_SHORT).show();
            }
        });

    }


    void clickLoad(){
        //Firebase Storage 에 저장되어 있는 이미지 파일을 읽어오기

        // Firebase Storage 관리객체 소환하기
        FirebaseStorage firebaseStorage=FirebaseStorage.getInstance();

        // 최상위(root) 참조객체 얻어오기
        StorageReference rootRef= firebaseStorage.getReference();

        // 읽어올 파일의 참조객체 얻어오기
        StorageReference imgRef=rootRef.child("moana01.jpg");
        imgRef= rootRef.child("photo/moana05.jpg");

        // 파일참조 객체로 부터 파일의 "다운로드 URL" 을 얻어와서 이미지뷰에 보여주기
        if (imgRef != null){
            imgRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {//다운로드 URL이 파라미터로 전달됨
                    Glide.with(MainActivity.this).load(uri).into(binding.iv);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}