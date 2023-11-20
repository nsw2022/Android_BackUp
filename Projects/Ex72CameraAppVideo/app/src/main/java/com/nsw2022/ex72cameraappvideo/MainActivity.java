package com.nsw2022.ex72cameraappvideo;

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
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.MediaController;
import android.widget.VideoView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    VideoView vv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vv=findViewById(R.id.vv);
        findViewById(R.id.btn).setOnClickListener(view -> {

            Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            setVideoUri();
            if (videoUri != null) intent.putExtra(MediaStore.EXTRA_OUTPUT, videoUri);
            resultLauncher.launch(intent);

        });

        String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (checkSelfPermission(permissions[0])== PackageManager.PERMISSION_DENIED)requestPermissions(permissions,10);

    }//onCreate

    // 저장될 비디오 경로 Uri를 만들어주는 기능 매소드 설계

    void setVideoUri(){
        // 외부저장소에 공용저장 폴더 경로 - 앱삭제해도 지워지지 않는 영역
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);

        // 같은이름의 파일로 만들어지면.. 덮어쓰기가 되므로.. 현재 날짜를 이용하여 파일명을 만들기
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String fileName = "VIDEO_" + sdf.format(new Date()) + ".mp4";
        File file = new File(path,fileName);

        // 여기까지 잘 되었는지 중간 체크
        //new AlertDialog.Builder(this).setMessage(file.getAbsolutePath()).create().show();

        // File --> Uri
        if (Build.VERSION.SDK_INT<Build.VERSION_CODES.N){
            videoUri=Uri.fromFile(file);
        }else{
            videoUri = FileProvider.getUriForFile(this,"com.nsw2022.ex72cameraappvideo.FileProvider",file);
        }

        //체크목적
        //new AlertDialog.Builder(this).setMessage(videoUri.toString()).create().show();
    }

    // 저장될 비디오 경로 Uri
    Uri videoUri = null;

    ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode()!=RESULT_CANCELED){
                vv.setVideoURI(videoUri);

                MediaController mediaController = new MediaController(MainActivity.this);
                mediaController.setAnchorView(vv);

                vv.setMediaController(mediaController);

                // 비디오뷰는 워낙 로딩하는데 시간이 걸리기에 바로 paly를 시작하면 안나올 수 있음.
                vv.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        // 로딩이 준비가 끝나면.. 실행되는 영역
                        vv.start();
                    }
                });
            }
        }
    });
}