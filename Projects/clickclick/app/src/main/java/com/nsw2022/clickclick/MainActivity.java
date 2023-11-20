package com.nsw2022.clickclick;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    ImageView iv_startIv, number_iv1, number_iv2, number_iv3, number_iv4, number_iv5, number_iv6, number_iv7, number_iv8, number_iv9, number_iv10, number_iv11,  number_iv12;



    LinearLayout str_linearLayout,number_linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv_startIv=findViewById(R.id.iv_startbtn);

        number_iv1=findViewById(R.id.number_iv1);
        number_iv2=findViewById(R.id.number_iv2);
        number_iv3=findViewById(R.id.number_iv3);
        number_iv4=findViewById(R.id.number_iv4);
        number_iv5=findViewById(R.id.number_iv5);
        number_iv6=findViewById(R.id.number_iv6);
        number_iv7=findViewById(R.id.number_iv7);
        number_iv8=findViewById(R.id.number_iv8);
        number_iv9=findViewById(R.id.number_iv9);
        number_iv10=findViewById(R.id.number_iv10);
        number_iv11=findViewById(R.id.number_iv11);
        number_iv12=findViewById(R.id.number_iv12);

        str_linearLayout=findViewById(R.id.gamestart_linearlayout);
        number_linearLayout=findViewById(R.id.number_linearlayout);

        iv_startIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             str_linearLayout.setVisibility(View.GONE);
             number_linearLayout.setVisibility(View.VISIBLE);
            }
        });






    }//////on create

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String tag = view.getTag().toString();
            int picId = Integer.parseInt(tag);

            String tag2 = number_iv1.getTag().toString();
            int numberId=Integer.parseInt(tag2);





        }
    };




}