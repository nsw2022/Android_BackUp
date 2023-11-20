package com.nsw2022.ex08framelayout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    Button btnkor, btnjap, btnchi;
    View layoutkor, layoutJap, layoutChi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnkor=findViewById(R.id.btn_kor);
        btnjap=findViewById(R.id.btn_jap);
        btnchi=findViewById(R.id.btn_chi);

        layoutkor=findViewById(R.id.layout_korea);
        layoutJap=findViewById(R.id.layout_japan);
        layoutChi=findViewById(R.id.layout_china);

        btnkor.setOnClickListener(listener);
        btnjap.setOnClickListener(listener);
        btnchi.setOnClickListener(listener);



    }//Oncrate Method

   //클릭되는 것에 반응하는 리스너 객체 생성 및 참조변수에 대입 - 멤버변수
   View.OnClickListener listener = new View.OnClickListener() {
       @Override
       public void onClick(View view) {

           layoutkor.setVisibility(View.GONE);
           layoutJap.setVisibility(View.GONE);
           layoutChi.setVisibility(View.GONE);

           //클릭한 버튼의 id를 찾아오기
           int id=view.getId();

           switch (id){
               case R.id.btn_kor:
                   layoutkor.setVisibility(View.VISIBLE);
                   break;

               case R.id.btn_jap:
                   layoutJap.setVisibility(View.VISIBLE);
                   break;

               case R.id.btn_chi:
                   layoutChi.setVisibility(View.VISIBLE);
                   break;

           }


       }
   } ;


}//Main