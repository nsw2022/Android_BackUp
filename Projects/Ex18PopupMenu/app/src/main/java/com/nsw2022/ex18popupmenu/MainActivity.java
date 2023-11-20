package com.nsw2022.ex18popupmenu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btn;
    Button btn02;

    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn= findViewById(R.id.btn);
        btn02= findViewById(R.id.btn02);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "clicked button", Toast.LENGTH_SHORT).show();
            }
        });

        // 버튼이 롱~~클릭되는 반응하는 리스너 객체 생성 및 설정
        btn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //Toast.makeText(MainActivity.this, "long~~~", Toast.LENGTH_SHORT).show();

                // Popup Menu 만들기 - 마치 메뉴판을 가진 양탄자 같은 녀석이어서 원하는 뷰 위치에 메뉴를 보이게 함.
                //PopupMenu popupMenu= new PopupMenu(MainActivity.this, btn);
                PopupMenu popupMenu= new PopupMenu(MainActivity.this, btn02); //메뉴가 btn02에 보여짐
                // res>>menu>>popup.xml 문서를 읽어와서 메뉴객체로 만들어주는 객체를 소환
                MenuInflater menuInflater= getMenuInflater();
                menuInflater.inflate(R.menu.popup, popupMenu.getMenu()); //팝업메뉴 양탄자에 있는 Menu객체에 항목들 추가

                // 팝업메뉴 항목을 클릭했을때 반응하는 리스너 생성 및 설정
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        switch ( menuItem.getItemId() ){
                            case R.id.menu_info:
                                Toast.makeText(MainActivity.this, "Information", Toast.LENGTH_SHORT).show();
                                break;

                            case R.id.menu_delete:
                                Toast.makeText(MainActivity.this, "Delete", Toast.LENGTH_SHORT).show();
                                break;
                        }

                        return false;
                    }
                });

                popupMenu.show();


                // return 값.. false면 long클릭 후에 click리스너도 추가로 발동함
                // 만약 롱클릭만 하고 싶다면 return true.
                return true;
            }
        });


        // 이미지뷰 클릭시에 팝업메뉴가 보이도록.
        iv= findViewById(R.id.iv);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu= new PopupMenu(MainActivity.this, iv);
                getMenuInflater().inflate(R.menu.popup_iv, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.menu_share:
                                break;

                            case R.id.menu_delete2:
                                break;

                            case R.id.menu_modify:
                                break;
                        }
                        return false;
                    }
                });

                popupMenu.show();
            }
        });


    }
}