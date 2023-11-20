package com.nsw2022.ex14toastanddialog;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button b1;
    Button b2;

    //다이얼로그에 보여줄 항목들.
    String[] items= new String[]{"Apple", "Banana", "Orange"};
    boolean[] checked= new boolean[]{true, false, true};


    // RadioButton으로 선택한 항목의 index번호
    int checkedItemIndex=1;

    //다이얼로그안에 있는 뷰들( EditText, Button, ImageView)의 참조변수
    EditText dialogEt;
    Button dialogBtn;
    ImageView dialogIv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1= findViewById(R.id.b1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast 객체 생성
//                Toast t= Toast.makeText(MainActivity.this, "Hello toast", Toast.LENGTH_SHORT);
//                //화면에 보이기
//                t.show();

                // Toast를 별도의 참조변수 없이 만들고 바로 show()
                Toast.makeText(MainActivity.this, "Hello Toast", Toast.LENGTH_SHORT).show();

            }
        });


        b2= findViewById(R.id.b2);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // AlertDialog를 만들어주는 건축가(Builder) 객체 생성
                AlertDialog.Builder builder= new AlertDialog.Builder(MainActivity.this);

                // 건축가에게 원하는 다이얼로그의 모양 설계를 요청
                builder.setTitle("다이얼로그");
                builder.setIcon(android.R.drawable.ic_dialog_alert);

                //builder.setMessage("Do you wanna Quit");

                //단순 Text메세지 1개 말고.. 목록형태로 리스트를 보여주는 기능
//                builder.setItems(items, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        //두번째 파라미터 i : 클릭한 항목의 index 번호 .. 0, 1, 2
//
//                        Toast.makeText(MainActivity.this, items[i], Toast.LENGTH_LONG).show();
//
//                    }
//                });

                // 리스트 형태인데 RadioButton이 포함된 기능
//                builder.setSingleChoiceItems(items, checkedItemIndex, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        //두번째 파라미터 i : 선택한 항목의 index번호
//
//                        checkedItemIndex= i;
//
//                    }
//                });

                // Checkbox 버튼으로 다중선택이 가능한 목록 다이얼로그
//                builder.setMultiChoiceItems(items, checked, new DialogInterface.OnMultiChoiceClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
//                        // 두번째 파라미터 i : 클릭된 항목의 index번호
//                        // 세번째 파라미터 b : 클릭된 항목의 true/false 여부 [체크여부]
//
//                        checked[i]= b;
//
//                    }
//                });

                // 메세지 영역을 Custom View 로 마음대로 설정해보기
                // XML언어를 이용해서 View의 배치(layout) 모양을 설계하고..
                // 이를 다이얼로그에 설정
                builder.setView(R.layout.dialog);



//                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        //Toast.makeText(MainActivity.this, "OK", Toast.LENGTH_SHORT).show();
//                        //Toast.makeText(MainActivity.this, items[checkedItemIndex], Toast.LENGTH_SHORT).show();
//
//                        String s="";
//                        for(int k=0; k<checked.length; k++){
//                            if( checked[k] ) s += items[k];
//                        }
//
//                        Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
//
//                    }
//                });
//
//                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        Toast.makeText(MainActivity.this,"CANCEL", Toast.LENGTH_SHORT).show();
//                    }
//                });

                // 위 설정한 모양으로 AlertDialog를 만들어 달라고 요청!
                AlertDialog dialog= builder.create();

                // 다이얼로그의 바깥쪽을 터치했을때 cancel 될 것인지 여부 설정 [default : true]
                dialog.setCanceledOnTouchOutside(false);

                // 디바이스의 뒤로가기버튼을 눌렀을 때도 다이얼로그가 사라지지 않도록. 하고 싶다면
                dialog.setCancelable(false);

                //다이얼로그를 보이기
                dialog.show();

                //다이얼로그 안에 있는 뷰들은 액티비티가 아니라 다이얼로그에게 찾아달라고 해야함.
                dialogEt= dialog.findViewById(R.id.dialog_et);
                dialogBtn=dialog.findViewById(R.id.dialog_btn);
                dialogIv=dialog.findViewById(R.id.dialog_iv);

                dialogBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String s=dialogEt.getText().toString();
                        Toast.makeText(MainActivity.this,s,Toast.LENGTH_LONG).show();

                        // 다이얼로그가 사라지도록 하고 싶다면
                        dialog.dismiss();

                    }
                });

            }
        });


    }
}