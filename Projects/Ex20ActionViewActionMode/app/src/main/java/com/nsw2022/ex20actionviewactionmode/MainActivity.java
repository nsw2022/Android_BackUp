package com.nsw2022.ex20actionviewactionmode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // MenuItem 안에 있는 EditText 참조변수
    EditText actionViewEt;
    Button btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn= findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //액션모드 시작하기 : 액션바위치에 메뉴를 새로이 보여주는 기능
                startActionMode(new ActionMode.Callback() {

                    // 액션모드가 처음 실행될때 1번 호출되는 메소드 - 이 곳에서 액션모드의 메뉴 만듦
                    @Override
                    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                        //액션모드 메뉴를 만드는 방법은 옵션메뉴 만드는 방법과 동일
                        getMenuInflater().inflate(R.menu.actionmode, menu);

                        // 액션모드에 추가로 줄 수 있는 설정들
                        actionMode.setTitle("action mode");
                        actionMode.setSubtitle("This is action mode");

                        // 배경색상 변경은 theme.xml 에서 테마로 스타일을 지정해야함.
                        // [ res/values/themes.xml 작업 ]
                        //리턴값이 true 여야만 액션모드가 시작됨.
                        return true;
                    }

                    // 액션모드가 실행될때 마다 호출되는 메소드
                    @Override
                    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {

                        return false;
                    }

                    // 액션모드의 메뉴아이템이 클릭될때 호출되는 메소드
                    @Override
                    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {

                        switch (menuItem.getItemId()){
                            case R.id.menu_aa:
                                Toast.makeText(MainActivity.this, "SHARE", Toast.LENGTH_SHORT).show();
                                break;

                            case R.id.menu_bb:
                                Toast.makeText(MainActivity.this, "MAP", Toast.LENGTH_SHORT).show();
                                break;

                            case R.id.menu_cc:
                                Toast.makeText(MainActivity.this, "CALEMDAR", Toast.LENGTH_SHORT).show();
                                break;
                        }

                        return false;
                    }

                    // 액션모드가 종료될때 호출되는 메소드드
                    @Override
                    public void onDestroyActionMode(ActionMode actionMode) {

                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.option, menu);

        // ActionView를 가지고 있는 MenuItem을 찾아오기
        MenuItem menuItem= menu.findItem(R.id.menu_action);
        actionViewEt= menuItem.getActionView().findViewById(R.id.actionview_et);

        // EditText의 소프트키보드 중에서 작성 완료버튼(Search모양 버튼)을 클릭하는 것을 듣는 리스너 생성 및 설정
        actionViewEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                //키패드 중에서 어떤 버튼을 눌렀는지 .. 를 식별하는 식별자 id - 두번째 파라미터 i
                if( i == EditorInfo.IME_ACTION_SEARCH){
                    String s= actionViewEt.getText().toString();
                    Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
                }

                return false;
            }
        });



        return super.onCreateOptionsMenu(menu);
    }
}