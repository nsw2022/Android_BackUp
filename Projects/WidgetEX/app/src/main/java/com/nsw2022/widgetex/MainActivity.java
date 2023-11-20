package com.nsw2022.widgetex;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ImageView iv_over, iv_hearat, iv_comment, iv_send, iv_box, newywork;

    ImageView dialog_Iv, dialog_Iv2 , dialog_ivbtn;

    int index = 0;

    int imgs [] = new int[] {R.drawable.newyork,R.drawable.paris};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv_over=findViewById(R.id.ic_overflow);
        iv_hearat=findViewById(R.id.ic_heart);
        iv_comment=findViewById(R.id.ic_comment);
        iv_send=findViewById(R.id.ic_send);
        iv_box=findViewById(R.id.ic_box);
        newywork=findViewById(R.id.newyork);









        iv_over.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Toast.makeText(getApplication(),"overflow",Toast.LENGTH_SHORT).show();
            }
        });



        iv_hearat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(MainActivity.this,"Heart",Toast.LENGTH_SHORT).show();
            }
        });

        iv_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(MainActivity.this,"Comment",Toast.LENGTH_SHORT).show();
            }
        });

        iv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"Send",Toast.LENGTH_SHORT).show();
            }
        });

        iv_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"Bookmark",Toast.LENGTH_SHORT).show();
            }
        });

        newywork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlerDialog();
            }
        });





    }
    public void showAlerDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setView(R.layout.dialog);

        AlertDialog dialog=builder.create();
        dialog.show();


        dialog_Iv= dialog.findViewById(R.id.dialog_iv);
        dialog_Iv2= dialog.findViewById(R.id.dialog_iv2);
        dialog_ivbtn=dialog.findViewById(R.id.dialog_ivbtn);

        dialog_Iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog_Iv.setImageResource(imgs[index]);

                index++;
                if(index>imgs.length-1){
                    index=0;
                }

            }
        });

        dialog_Iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog_Iv2.setImageResource(imgs[index]);

                index++;
                if(index>imgs.length-1){
                    index=0;
                }

            }
        });

    }
    


}