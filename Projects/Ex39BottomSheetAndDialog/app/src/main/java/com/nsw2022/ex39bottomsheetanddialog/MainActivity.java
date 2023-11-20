package com.nsw2022.ex39bottomsheetanddialog;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class MainActivity extends AppCompatActivity {

    Button button;
    View bottomSheet; // BottomSheet 역할로 만든 LinearLayout 참조변수

    //BottomSheet로 만든 뷰를 열거나 닫을 수 있는 능력을 가진 행동(Behavior)객체
    BottomSheetBehavior bottomSheetBehavior;

    Button btn2;
    BottomSheetDialog bottomSheetDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button=findViewById(R.id.btn);
        bottomSheet=findViewById(R.id.bs);
        //bottomSheetBehavior 객체 꺼내오기
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // BottomSheet 확장시키기 [ 열기 ]
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
            }
        });

        btn2=findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 다이얼로그 객체 생성
                bottomSheetDialog=new BottomSheetDialog(MainActivity.this);

                // 다이얼로그가 보여줄 화면 View를 XML에 설계하여 설정
                bottomSheetDialog.setContentView(R.layout.bottomsheet_dialog);

                // 다이얼로그 바깥쪽 영역을 클릭했을때 캔슬 안되도록
                bottomSheetDialog.setCanceledOnTouchOutside(false);

                // 다이얼로그 보이기
                bottomSheetDialog.show();

                Button btn= bottomSheetDialog.findViewById(R.id.bsd_btn);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bottomSheetDialog.dismiss();
                    }
                });

            }
        });

        //BottomSheetDialog의 배경에 라운드 효과를 주고싶다면. theme.xml 에서 설정해야함.

    }
}