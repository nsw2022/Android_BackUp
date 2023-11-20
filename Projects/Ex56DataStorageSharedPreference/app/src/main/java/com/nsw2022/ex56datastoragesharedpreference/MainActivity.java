package com.nsw2022.ex56datastoragesharedpreference;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText etName,etAge;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName=findViewById(R.id.et_name);
        etAge=findViewById(R.id.et_age);

        tv=findViewById(R.id.tv);

        findViewById(R.id.btn_save).setOnClickListener(view -> saveData());
        findViewById(R.id.btn_load).setOnClickListener(view -> loadData());

    }

    void saveData(){
        // 저장할 데이터들
        String name=etName.getText().toString();
        int age=Integer.parseInt(etAge.getText().toString());

        // SharedPreference 를 이용하여 데이터들 자료형별로 저장하기!!
        // "Data.xml" 파일에 데이터를 채그문을 쓰면서 저장해주는 기능을 가진 SharedPreference 객체 소환
        SharedPreferences pref = getSharedPreferences("Data",MODE_PRIVATE);

        //저장 작업 시작!!
        SharedPreferences.Editor editor = pref.edit();// 저장해주는 객체를 리턴해줌

        editor.putString("name",name);//("식별자", "값")
        editor.putInt("age",age);     //("key",value)

        // 저장 완료 명령! - 이거안하면 저장안됨!!
        editor.commit();

        Toast.makeText(this, "저장완료", Toast.LENGTH_SHORT).show();


    }

    void loadData(){

        // "Data.xml"에 저장된 데이터를 읽어들이기 위해 SharedPreference 소환
        SharedPreferences pref = getSharedPreferences("Data",MODE_PRIVATE);

        String name=pref.getString("name","");//두번째 파라미터 : default value [ 저장된 값이 없을 때의 값 ]
        int age=pref.getInt("age",0);

        tv.setText(name + " : " + age);

    }


}