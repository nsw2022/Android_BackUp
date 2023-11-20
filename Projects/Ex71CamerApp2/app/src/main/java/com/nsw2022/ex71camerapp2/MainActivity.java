package com.nsw2022.ex71camerapp2;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv= findViewById(R.id.iv);
        findViewById(R.id.btn).setOnClickListener(v->{
            Intent intent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            setImageUri(); // 이미지 Uri를 만들어내는 기능 메소드 호출 - 저 아래 설계함...

            // 그냥 카메라앱을 실행하면 섬네일 이미지만 줌.
            // 캡처한 이미지를 파일로 저장될 수 있도록.. 카메라앱에게 가는 Intent 객체에게
            // 추가데이터로.. 파일저장경로 uri를 지정해 주면. 카메라앱이 자동 파일로 저장함!
            if(imgUri != null) intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
            resultLauncher.launch(intent);
        });

        // 외부저장소 사용 퍼미션
        String[] permissions= new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if( checkSelfPermission(permissions[0]) == PackageManager.PERMISSION_DENIED ) requestPermissions(permissions, 10);

    }//onCreate method..

    // 카메라앱이 캡쳐한 사진이 파일로 저장될 경로 Uri 참조변수
    Uri imgUri= null;

    // 이미지 Uri 경로를 만들어내는 기능 메소드 설계
    void setImageUri(){

        // 외부저장소에 저장하는 경우가 가장 일반적임. - 외부저장소는 동적 퍼미션이 필요함 [가장 마지막에 코딩]

        // 외부저장소 중에서 잘 알려진 공공디렉토리의 경로
        File path= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        // 경로가 정해졌으니 파일명 정하기 [ 같은 이름으로 정하면 덮어쓰기가 되므로.. 현재시간을 이용하여 파일명 만들기 ]
        SimpleDateFormat sdf= new SimpleDateFormat("yyyyMMddHHmmss"); //"20220927105530"
        String fileName= "IMG_" + sdf.format( new Date() ) +".jpg";
        File file= new File(path, fileName);

        //여기까지 잘 되었는지 중간 체크
        //new AlertDialog.Builder(this).setMessage( file.getAbsolutePath() ).create().show();

        // 카메라앱에 전달할 이미지의 경로는 Uri임. File객체가 아님.
        // File경로 --> Uri경로  변환
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.N){
            imgUri= Uri.fromFile(file);
        }else{

            // 개발자가 만든 File경로를 Uri로 만든다는 것은 Database화 하여 관리한다는 것임.
            // 그래서 이 File경로로 만들어질 콘텐츠 DB경로인 Uri를 카메라앱에게 공개(제공)하기 위해
            // Content Provider를 만들어야 함. 그 중에서 파일경로에 대한 Provider가 이미 클래스로 제공됨
            // FileProvider

            // 4개 컴포넌트는 AndroidManifest.xml에 반드시 등록해야만 사용가능함.
            // 매니페스트에 등록된 FileProvider를 이용하여 File객체를 Uri로 변환
            imgUri= FileProvider.getUriForFile(this, "com.nsw2022.ex71cameraapp2.FileProvider", file);
        }

        // Uri가 잘 계산되었는지 확인하기 위해 경로 출력
        //new AlertDialog.Builder(this).setMessage( imgUri.toString() ).create().show();
    }


    ActivityResultLauncher<Intent> resultLauncher= registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode() != RESULT_CANCELED ){
                Glide.with(MainActivity.this).load(imgUri).into(iv);
            }
        }
    });

}//MainActivity class..