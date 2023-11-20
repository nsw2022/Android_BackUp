package com.nsw2022.ex43activity3;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv=findViewById(R.id.tv);
        findViewById(R.id.btn).setOnClickListener(view -> {
            // EditActivity를 실행하고 그 액티비티로 부터 결과 Data를 받아보기.

            Intent intent=new Intent(this,EditActivity.class);
            //startActivity(intent);// 이렇게 실행하면 갤과데이터를 받아올수가 없음
            //startActivityForResult(); //이 메소드는 예전버전..
            // 결과를 받아오도록 계약을 맺고 그 결과를 받았을때 반응하는 객체를 가지ㅏ고있는.
            // 액티비티 실행(런칭) 객체 객체를 이용
            activityResultLauncher.launch(intent);
        });
    }

    // 엑티비티를 실행시켜주는 객체 생성 - 멤버변수 위치. <- 반드시
    ActivityResultLauncher<Intent> activityResultLauncher= registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {// 결과주는 Activity가 종료되면 실행되는 메소드
           // 돌려보낸 결과가 Ok 인지 확인
           if (result.getResultCode()==RESULT_OK){
               // 돌려보낸 택배기사(Intent로 부터 Extra 데이터를 얻어오기
               Intent intent = result.getData();

               String title = intent.getStringExtra("title");
               double price = intent.getDoubleExtra("price",0.0);

               tv.setText(title + " : " + price + "$");
           }else{
               Toast.makeText(MainActivity.this, "글작성을 취소 하셨습니다", Toast.LENGTH_SHORT).show();
           }


            // 돌려보낸 택배기사(Intent)로 부터 Extra 데이터를 얻어오기



        }
    });


}