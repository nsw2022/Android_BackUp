package com.nsw2022.ex73camerax;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.common.util.concurrent.ListenableFuture;

import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    PreviewView previewView;
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 액티비티의 영역을 외부 상태표시줄과 아래.. 버튼들 영역까지 확대하는 Flag 적용
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        previewView= findViewById(R.id.preview_view);
        iv=findViewById(R.id.iv);
        findViewById(R.id.fab_iamge_capture).setOnClickListener(v->clickCapture());

        // 퍼미션들 체크
        String[] permissions= null;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P ) permissions= new String[]{Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO};
        else permissions= new String[]{Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE};

        if( checkSelfPermission(permissions[0]) == PackageManager.PERMISSION_DENIED ){
            requestPermissions(permissions, 10);
        }else{
            //프리뷰 시작!
            startPreview();
        }

    }//onCreate method..


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if( requestCode == 10){
            for( int grantResult : grantResults ){
                //퍼미션 중에 하나라도 거부가 있다면 앱 종료!!
                if(grantResult==PackageManager.PERMISSION_DENIED){
                    Toast.makeText(this, "카메라기능 사용불가\n앱을 종료합니다", Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }
            }

            //위에서 return 되지 않았다면 .. 모든 퍼미션이 허용된 것이므로..프리뷰 시작!
            startPreview();
        }

    }

    void startPreview(){

        // 카메라의 조리개가 open/close 되는 순간이 액티비티의 라이프사이클과 같도록 연결하는 작업객체
        ListenableFuture<ProcessCameraProvider> listenableFuture= ProcessCameraProvider.getInstance(this);

        //프리뷰 준비가 가능함을 듣는 리스너 추가
        listenableFuture.addListener(new Runnable() {
            @Override
            public void run() {
                try {
                    //카메라 기능 사용객체 얻어오기
                    ProcessCameraProvider cameraProvider= listenableFuture.get();

                    //프리뷰 객체 생성
                    Preview preview= new Preview.Builder().build();
                    //프리뷰 객체가 사용할 고속버퍼뷰(SurfaceView)를 설정
                    preview.setSurfaceProvider(previewView.getSurfaceProvider());

                    // 디바이스에 있는 카메라 중 하나를 선택
                    CameraSelector cameraSelector= CameraSelector.DEFAULT_BACK_CAMERA;

                    // 기존 연결된 카메라 기능들은 모두 제거
                    cameraProvider.unbindAll();

                    // 이미지 캡쳐객체 생성
                    imageCapture=new ImageCapture.Builder().build();

                    // 라이프사이클에 맞추어 카메라 프리뷰를 제어하도록 요청. + 이미지캡쳐도 연결
                    cameraProvider.bindToLifecycle(MainActivity.this, cameraSelector, preview, imageCapture);



                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, ContextCompat.getMainExecutor(this));


    }

    // 이미지 캡쳐 객체 참조변수 - 객체는 Preview 하는 곳에서 생성함.
    ImageCapture imageCapture = null;


    void clickCapture(){
        if (imageCapture==null) return;

        // 캡처한 사진을 파일로 저장할 파일명 - 날짜를 이용
        String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(System.currentTimeMillis());

        // 사진정보들이 저장된 MediaStore(미디어저장소)의 DB에 얺을 값들(1개의 record)을 가진 객체
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME,fileName); //DB에 특정 column(칸)에 값 넣기
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE,"image/jpeg");
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH,"Pictures/CameraX-Image");

        // 이미지캡처가 찍은 사진을 저장하는 경로를 관리하는 객체
        ImageCapture.OutputFileOptions outputFileOptions = new ImageCapture.OutputFileOptions.Builder(getContentResolver(),MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues).build();

        // 이미지 캡처에게 사진을 취득하라고 명령!!
        imageCapture.takePicture(outputFileOptions, ContextCompat.getMainExecutor(this), new ImageCapture.OnImageSavedCallback() {
            @Override
            public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                Toast.makeText(MainActivity.this, "촬영 성공!", Toast.LENGTH_SHORT).show();

                //저장된 이미지파일의 Uri
                Uri uri = outputFileResults.getSavedUri();
                Glide.with(MainActivity.this).load(uri).into(iv);

            }

            @Override
            public void onError(@NonNull ImageCaptureException exception) {
                Toast.makeText(MainActivity.this, "에러 : "+ exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}