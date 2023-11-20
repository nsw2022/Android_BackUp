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
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        resultLauncher.launch(intent);
    }

    ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() != RESULT_CANCELED){
                Intent intent = result.getData();

                Uri uri = intent.getData();
                Glide.with(MainActivity.this).load(uri).into(binding.iv);
                imgPath=getRealPathFromUri(uri);
                new AlertDialog.Builder(MainActivity.this).setMessage(imgPath).create().show();


            }
        }
    });

    String imgPath=null;

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
        if (imgPath==null) return;

        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl("http://tmddn3410.dothome.co.kr/");
        builder.addConverterFactory(ScalarsConverterFactory.create());
        Retrofit retrofit = builder.build();

        RetrofitService retrofitService = retrofit.create(RetrofitService.class);

        File file = new File(imgPath);

        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"),file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("img",file.getName(),requestBody);

        Call<String> call = retrofitService.sendImg(part);

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