package com.nsw2022.ex57datastoragesqlitedatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText etName;
    EditText etAge;
    EditText etEmail;

    // SQLiteDatabase 객체 참조변수
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName= findViewById(R.id.et_name);
        etAge= findViewById(R.id.et_age);
        etEmail= findViewById(R.id.et_email);

        findViewById(R.id.btn_insert).setOnClickListener(v->clickInsert());
        findViewById(R.id.btn_update).setOnClickListener(v->clickUpdate());
        findViewById(R.id.btn_delete).setOnClickListener(v->clickDelete());
        findViewById(R.id.btn_select_all).setOnClickListener(v->clickSelectAll());
        findViewById(R.id.btn_select_by_name).setOnClickListener(v->clickSelectByName());

        // "data.db" 라는 이름으로 데이터베이스 파일 열기 또는 생성
        // 액티비티클래스에 이미 이 기능이 메소드로 존재함.
        // 이 메소드를 실행하면 "data.db"를 제어할 수 있는 능력이 있는 객체(SQLiteDatabase)를 리턴함.
        db= openOrCreateDatabase("data.db", MODE_PRIVATE, null);

        // 만들어진 DB 파일에 테이블(표)을 생성하는 작업 수행
        // SQL 언어를 이용해서 원하는 명령을 Database 에 실행!
        db.execSQL("CREATE TABLE IF NOT EXISTS member(num INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(20), age INTEGER, email TEXT)");

    }

    void clickInsert(){

        //저장할 Data 들을 EditText로 부터 받아오기
        String name= etName.getText().toString();
        int age= Integer.parseInt(etAge.getText().toString()) ;
        String email= etEmail.getText().toString();

        //데이터베이스 "data.db"파일 안에 "member"라는 이름의 테이블에 값들을 삽입(저장)
        db.execSQL("INSERT INTO member(name, age, email) VALUES(?,?,?)", new String[]{name,age+"",email});

        etName.setText("");
        etAge.setText("");
        etEmail.setText("");
    }

    void clickUpdate(){
        // 해당 이름을 가진 데이터의 age를 40으로, email 은 "kk@kk.com"으로 변경해보기
        String name= etName.getText().toString();
        db.execSQL("UPDATE member SET age=40, email='kk@kk.com' WHERE name=?",new String[]{name});

    }

    void clickDelete(){
        // 해당 이름을 가진 데이터 한줄을 모두 삭제
        String name=etName.getText().toString();
        db.execSQL("DELETE FROM member WHERE name=?",new String[]{name});

    }

    void clickSelectAll(){
        // "member" 테이블의 모든 레코드를(한줄-row) 가져오기

        // SELECT 검색조건에 따른 결과표를 가리키는 Cursor 객체가 리턴됨
        Cursor cursor=db.rawQuery("SELECT * FROM member",null);
        if(cursor==null) return;

        // 총 레코드 수(데이터의 행(row)의 개수)
        int cnt=cursor.getCount();
        //Toast.makeText(this, ""+cnt, Toast.LENGTH_SHORT).show();

        StringBuffer buffer = new StringBuffer();

        //결과표의 커서를 첫 레코드로 이동
        cursor.moveToFirst();
        for (int i=0;i<cnt;i++){
            int num=cursor.getInt(0);//0번칸의 int값 얻어오기
            String name=cursor.getString(1);
            int age=cursor.getInt(2);
            String email=cursor.getString(3);

            buffer.append(num+" : "+ name + "  "+ age + "  "+ "  "+ email + "\n");

            cursor.moveToNext();

        }//for
        Toast.makeText(this, buffer.toString(), Toast.LENGTH_SHORT).show();

    }

    void clickSelectByName(){
        //"member"테이블에서 특정 이름의 한줄(row-record) 데이터 검색하여 가져오기
        String name=etName.getText().toString();

        Cursor cursor=db.rawQuery("SELECT name,age FROM member WHERE name=?",new String[]{"robin"});
        if(cursor==null) return;

        StringBuffer buffer = new StringBuffer();

        while ( cursor.moveToNext() ){

            String n=cursor.getString(0);
            int age=cursor.getInt(1);

            buffer.append(n + " : " + age + "\n");

        }//while

        new AlertDialog.Builder(this).setMessage(buffer.toString()).create().show();

    }

}