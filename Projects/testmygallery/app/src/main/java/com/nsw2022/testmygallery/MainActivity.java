package com.nsw2022.testmygallery;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import com.nsw2022.testmygallery.databinding.ActivityMainBinding;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    MyAdapter adapter;
    ArrayList<Uri> uriList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        adapter=new MyAdapter(this,uriList);
        binding.recyclerView.setAdapter(adapter);

        binding.btnGallery.setOnClickListener(v->{
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
            intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            resultLauncher.launch(intent);
        });


        binding.btnDelete.setOnClickListener(v->{
            if (uriList.size()==0) return;
            uriList.remove(0);
            adapter.notifyItemRemoved(0);
        });

        binding.btnSave.setOnClickListener(v->Save());

    }

    String image=null;

    void Load(){
        SharedPreferences pref;
    }

    void Save(){

        SharedPreferences pref= getSharedPreferences("Images",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("test",image);


        editor.commit();
    }



 ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode()!=RESULT_CANCELED&&result.getData()!=null){
                // 사진이 파일말고 bitmap으로 리턴해줄때가 있다..?!?! 조건을.. 파일로 저장했는지 물어봐야했었나.. 여쭤보기
                Intent intent = result.getData();
                ClipData clipData =intent.getClipData();
                for (int i=0;i<clipData.getItemCount();i++) {
                    ClipData.Item item=clipData.getItemAt(i);
                    Uri uri = item.getUri();
                    image=uri.toString();
                    uriList.add(uri);
                    adapter.notifyItemInserted(uriList.size()-1);

                }
                // 사용자가 저장한걸 다보여주고 앱에 이사진을 저장할거면 서버에 보내는거아님 shardpreference밖에 안떠오름.. 근데 사진을 저장할꺼면 bit단위로 바꿔줘야함->bitmap
                // 쉐어드프리페어런스 디코드방법이 넘복잡해서.. sqlite 로 변경..

            }else{
                Toast.makeText(MainActivity.this, "아무것도 선택안했습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    });

}
