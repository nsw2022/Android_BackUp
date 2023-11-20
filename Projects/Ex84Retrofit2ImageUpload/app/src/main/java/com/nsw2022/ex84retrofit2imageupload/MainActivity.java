package com.nsw2022.ex84retrofit2imageupload;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.nsw2022.ex84retrofit2imageupload.databinding.ActivityMainBinding;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnSelect.setOnClickListener(v->clickSelect());
        binding.btnSend.setOnClickListener(v->clickSend());

    }

    void clickSelect(){
        // 사진앱 or 갤러리앱 통해 사진을 선택하도록..
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        resultLauncher.launch(intent);
    }

    //사진앱을 실행하고 결과를 받아오도록 중간에 계약을 맺은 하청업체(브로커)
    ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() != RESULT_CANCELED){
                //선택된 사진이미지를 uri 데이터를 가지고 돌아온 Intent 를 불러들이기
                Intent intent = result.getData();
                // 인텐트가 가지고온 선택된 사진의 uri를 얻어오기
                Uri uri = intent.getData();

                // 이미지뷰에 선택된 uri의 사진이 보여지도록 Glide 이용
                Glide.with(MainActivity.this).load(uri).into(binding.iv);

                // 선택된 이미지를 Retrofit을 이요하여 서버에 보내려면..
                // 이미지의 실제 경로주소가 필요함. 근데 uri는 실제주소가 아니라 콘텐츠 주소(DB 식별 주소)임
                // uri 주소는 어떻게 생겼는지 확인해보기 -- 경로가 실제 폴더이름이 아님.
                //new AlertDialog.Builder(MainActivity.this).setMessage(uri.toString()).create().show();

                // Uri 를 절대주소(실제주소)로 변경해주는 기능을 통해 변환.. [ 직접 만들어야 함 ]
                imgPath=getRealPathFromUri(uri);
                // 제대로 되었는지 확인
                new AlertDialog.Builder(MainActivity.this).setMessage(imgPath).create().show();


            }
        }
    });


    // 선택된 이미지의 절대주소를 저장할 String 변수
    String imgPath=null;

    //Uri -- > 절대경로로 바꿔서 리턴시켜주는 메소드
    String getRealPathFromUri(Uri uri){
        String[] proj= {MediaStore.Images.Media.DATA};
        CursorLoader loader= new CursorLoader(this, uri, proj, null, null, null);
        Cursor cursor= loader.loadInBackground();
        int column_index= cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result= cursor.getString(column_index);
        cursor.close();
        return  result;
    }


    void clickSend(){
        if(imgPath==null) return;

        //Retrofit을 이용하여 선택된 사진을 서버로 보내기..

        //1) Retrofit 객체 생성 - 서버로부터 응답은 String으로 받기
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl("http://tmddn3410.dothome.co.kr/");
        builder.addConverterFactory(ScalarsConverterFactory.create());
        Retrofit retrofit = builder.build();

        // 2,3) RetrofitService 인터페이스와 추상메소드 설계 및 객체 생성
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);


        //4) 추상메소드 호출 및 파일을 보내야 하기에 파일을 전송하는 택배상자(MultiPartBody.Part) 포장하기
        File file = new File(imgPath);
        //파일을 1차 포장하는 비닐팩(RequestBody)으로 감싸기..
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"),file);
        //택배상자에(MultipartBody.Part) 파일을 담은 비닐팩(RequestBody)을 넣기.
        MultipartBody.Part part = MultipartBody.Part.createFormData("img",file.getName(),requestBody); // 식별자 , 파일명, 요청Body(비닐팩)

        Call<String> call =retrofitService.sendImage(part);

        // 5) 네츠워크 작업 시작
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String s = response.body();
                new AlertDialog.Builder(MainActivity.this).setMessage(s).create().show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                new AlertDialog.Builder(MainActivity.this).setMessage(t.getMessage()).create().show();
            }
        });






    }
}