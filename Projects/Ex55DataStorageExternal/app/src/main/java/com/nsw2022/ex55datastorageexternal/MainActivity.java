package com.nsw2022.ex55datastorageexternal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.Permissions;

public class MainActivity extends AppCompatActivity {

    EditText et;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et= findViewById(R.id.et);
        tv= findViewById(R.id.tv);

        findViewById(R.id.btn_save).setOnClickListener(v->saveData());
        findViewById(R.id.btn_load).setOnClickListener(v->loadData());

        // 외부메모리안에 개발자가 원하는 특정 폴더 위치에 저장하기 - 앱을 삭제해도 파일을 그대로 유지됨.
        findViewById(R.id.btn).setOnClickListener(v->clickBtn());
    }

    // 외부저장소의 특정 폴더에 파일 저장하는 기능
    void saveData2(){

        // 외부저장소 사용가능 여부 체크
        String state = Environment.getExternalStorageState();
        if ( state.equals(Environment.MEDIA_MOUNTED) ){

            //연결되어 있으니 파일 저장

            // Sdcard의 특정위치 저장
            File path=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            if(path!=null) tv.setText(path.getPath());

            File file = new File(path, "aaa.txt");

            try {
                FileWriter fileWriter = new FileWriter(file,true);
                PrintWriter writer = new PrintWriter(fileWriter); // 보조 문자 스트림

                writer.println(et.getText().toString());
                writer.flush();
                writer.close();

                et.setText("");

                Toast.makeText(this, "다운로드 폴더에 저장완료", Toast.LENGTH_SHORT).show();


            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    void clickBtn(){
        //외부 메모리의 특정 디렉토리에 파일 저장하려면.. 반드시 사용자에게 허가를 받는 작업이 필요함.
        // 동적 퍼미션 요청 [ AndroidManifest.xml ] - 다이얼로그로 허가/거부 를 선택하도록 요청.

        // 이미 퍼미션 허가 한적이 있는지 확인
        int checkResult = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);

        // 현재 퍼미션이 거부상태인가..
        if(checkResult== PackageManager.PERMISSION_DENIED){
            // 사용자에게 퍼미션을 요청하는 다이얼로그를 보여줘야 함. - 직접 다이얼로그를 만들지 않고..
            // 퍼미션을 요청하는 다이얼로그를 보여주는 기능 메소드 호출!!
            String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
            requestPermissions(permissions,10);

        }else {
            saveData2();
        }

    }

    // requestPermissions() 메소드의 호출을 통해 보여진 다이얼로그에서 [ 허가/거부 ] 를 선택했을때
    // 자동으로 발동하는 콜백메소드드


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode==10){
            if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "외부저장소 저장 가능", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "외부저장소 저장 금지. 기능 사용 불가", Toast.LENGTH_SHORT).show();
            }
        }
    }

    void saveData(){

        // 외부메모리(SDcard, USB)가 있는가?
        String state= Environment.getExternalStorageState(); //저장소 연결상태값 문자열을 리턴함

        //외장메모리상태(state)가 연결(mounted)되어 있지 않는가?
        if(  !state.equals(Environment.MEDIA_MOUNTED)  ){
            Snackbar.make(tv, "SDcard is not mounted", Snackbar.LENGTH_SHORT).show();
            return;
        }

        // 저장할 데이터를 EditText로 부터 가져오기
        String data= et.getText().toString();
        et.setText("");

        //외부저장소에 "Data.txt"라는 이름의 파일이 저장될 파일의 경로(폴더/디렉토리) 부터 준비
        File path; //외부메모리 영역의 경로를 관리하는 객체 참조변수

        // 안드로이드 마시멜로우버전(api 23버전) 이상에서는 SD카드안에 아무 곳에나 파일을 저장할 수 없음.
        // 오로지 앱에게 할당된 영역에만 저장 가능함. [ 앱이 삭제되면 파일도 같이 제거됨 ]

        // 앱에게 할당된 영역(storage/emulated/0/Android/data/앱 패키지명/files/)안에 "MyDir"폴더 경로 얻어오기
        File[] paths= getExternalFilesDirs("MyDir"); //"MyDir"폴더명
        path= paths[0];
        tv.setText(path.getPath()); //경로 확인해보기

        // 위 경로에 "Data.txt" 라는 이름의 파일을 만들기 위해 File객체 생성
        File file= new File(path, "Data.txt");

        try {
            // "Data.txt"까지 연결되는 문자스트림 생성
            FileWriter fw= new FileWriter(file, true);
            PrintWriter writer= new PrintWriter(fw); //문자스트림 --> 보조문자스트림

            writer.println(data);
            writer.flush();
            writer.close();

            Snackbar.make(tv, "저장완료", Snackbar.LENGTH_SHORT).show();

            //소프트 키보드를 안보이도록..
            InputMethodManager imm= (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow( getCurrentFocus().getWindowToken(), 0  );

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    void loadData(){

        // SD카드를 읽을 수 있는 상태인지 확인
        String state= Environment.getExternalStorageState();
        if( state.equals(Environment.MEDIA_MOUNTED) || state.equals(Environment.MEDIA_MOUNTED_READ_ONLY) ){

            // 읽을 수 있는 상태..

            //파일이 저장된 경로 얻어오기
            File[] paths= getExternalFilesDirs("MyDir");
            File path= paths[0];

            File file= new File(path,"Data.txt");

            try {
                FileReader fr= new FileReader(file);
                BufferedReader reader= new BufferedReader(fr);

                StringBuffer buffer= new StringBuffer();

                while(true){
                    String line= reader.readLine();
                    if(line==null) break;

                    buffer.append(line+"\n");
                }

                tv.setText( buffer.toString() );
                reader.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }


}