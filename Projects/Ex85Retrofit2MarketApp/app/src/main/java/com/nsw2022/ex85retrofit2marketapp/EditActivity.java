package com.nsw2022.ex85retrofit2marketapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.nsw2022.ex85retrofit2marketapp.databinding.ActivityEditBinding;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class EditActivity extends AppCompatActivity {

    ActivityEditBinding binding;

    String imgPath; // 업로드할 이미지파일의 실제주소(절대경로) 참조변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnSelect.setOnClickListener(v->clickSelect());
        binding.btnComplete.setOnClickListener(v->clickComplete());
    }

    void clickSelect(){
        // 사진앱을 실행하여 사진선택
        Intent intent= new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        resultLauncher.launch(intent);
    }

    ActivityResultLauncher<Intent> resultLauncher= registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if( result.getResultCode() != RESULT_CANCELED ){
                Uri uri= result.getData().getData();
                Glide.with(EditActivity.this).load(uri).into(binding.iv);
                // uri (콘텐츠 DB주소) 확인해보기
                //new AlertDialog.Builder(EditActivity.this).setMessage(uri.toString()).show();

                // 레트로핏을 통해 서버에 사진파일을 보내려면 파일의 콘텐츠경로인 uri 가 아니라 실제 경로인
                // 절대주소가 필요함.
                // uri --> 절대주소로 변환하기.. [ 단, 외부저장소에 대한 퍼미션필요 ]
                imgPath= getRealPathFromUri(uri);
                //new AlertDialog.Builder(EditActivity.this).setMessage(imgPath).show();

            }
        }
    });

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


    void clickComplete(){

        // 작성한 데이터들 서버에 업로드 하기

        // 전송할 데이터들 [ name, title, message, price, imgPath ]
        String name= binding.etName.getText().toString();
        String title= binding.etTitle.getText().toString();
        String message= binding.etMsg.getText().toString();
        String price= binding.etPrice.getText().toString();

        // 레트로핏 작업 5단계
        Retrofit retrofit= RetrofitHelper.getRetrofitInstance();
        RetrofitService retrofitService= retrofit.create(RetrofitService.class);

        // 서버에 보낼 데이터들을 택배상자에 포장 [ 파일 박스, 데이터들 박스 ]
        //1) 파일박스
        MultipartBody.Part filePart= null;
        if(imgPath != null){
            File file= new File(imgPath);
            RequestBody requestBody= RequestBody.create(MediaType.parse("image/*"), file);
            filePart= MultipartBody.Part.createFormData("img", file.getName(), requestBody);
        }

        //2) 데이터들 박스
        Map<String, String> dataPart= new HashMap<>();
        dataPart.put("name", name);
        dataPart.put("title", title);
        dataPart.put("msg", message);
        dataPart.put("price", price);

        Call<String> call= retrofitService.postDataToServer(dataPart, filePart);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String s= response.body();
                Toast.makeText(EditActivity.this, ""+s, Toast.LENGTH_SHORT).show();

                //업로드가 완료되었으므로..액티비티를 종료(글 작성화면이 없어짐)
                finish();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(EditActivity.this, "error : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



    }
}