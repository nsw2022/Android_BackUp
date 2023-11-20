package com.nsw2022.ex17contextmenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn=findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(MainActivity.this, "cliked button",Toast.LENGTH_SHORT).show();



            }
        });

        // 액티비티에게 btn객체를 ContextMenu로 등록
        this.registerForContextMenu(btn);



    }

    // ContextMenu로 등록된 뷰(btn)가 롱~~클릭되면
    // 컨텍스트메뉴를 만드는 작업을 수행하는 메소드가 자동으로 발동함


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        // res>>menu>>context.mal 문서를 읽어와서 menu객체로 생성하여 추가해주는 기능을 가진
        // MenuInflater객체를 소환하여 메뉴아이템을 추가
        MenuInflater menuInflater= getMenuInflater();
        menuInflater.inflate(R.menu.context,menu);

        super.onCreateContextMenu(menu, v, menuInfo);

    }

    // Context Menu의 항목(MenuIteam)을 선택했을때 자동으로 발동하는 콜백메소드

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        //선택된 메뉴항목의 id를 얻어오기
        int id=item.getItemId();
        switch (id){
            case R.id.menu_save:
                Toast.makeText(this,"SAVE",Toast.LENGTH_SHORT).show();
                break;

            case R.id.menu_delete:
                Toast.makeText(this,"DELETE",Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onContextItemSelected(item);
    }
}