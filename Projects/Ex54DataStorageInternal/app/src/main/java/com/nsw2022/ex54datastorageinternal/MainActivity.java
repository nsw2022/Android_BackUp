package com.nsw2022.ex54datastorageinternal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class MainActivity extends AppCompatActivity {

    EditText et;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et=findViewById(R.id.et);
        tv=findViewById(R.id.tv);

        findViewById(R.id.btn_save).setOnClickListener( view ->  {

            // 저장할 데이터
            String data=et.getText().toString();
            et.setText("");

            // 디바이스의 내부 저장소에 위 data를 저장 - "Data.txt" 라는 이름의 파일에 저장
            // 내부저장소의 "data.txt"파일까지 데이터를 보낼 무지개로드(FileOutPutStream) 만들기
            // Stream은 열수있는 기능메소드가 이미 Activity에 존재함.
            try {
                FileOutputStream fos = openFileOutput("Data.txt",MODE_APPEND);
                // fos은 바이트 단위로 데이터를 저장하기에.. 사용하기 불편함..
                // 그래서 문자 단위 스트림으로 변환.. 여기서 더 나아가서 보조문자스트림 쓰면 더 편해짐
                PrintWriter writer=new PrintWriter(fos);

                writer.println(data);// 마치. JAVA의 System.out.println()사용하듯이 파일에 데이터 저장
                writer.flush();
                writer.close();

                Toast.makeText(this, "저장완료", Toast.LENGTH_SHORT).show();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });


        findViewById(R.id.btn_load).setOnClickListener( view ->  {
            //"Data.txt"파일에 저장된 데이터를 읽어오기 위한 무지개로드(FileInputStrem) 열기

            try {
                FileInputStream fis = openFileInput("Data.txt");//바이트 스트림
                //바이트 --> 문자 스트림 변환
                InputStreamReader isr = new InputStreamReader(fis);
                // 문자스트림(isr)은 한번에 한글자만 읽어져서 사용하기 불편함.
                // 한줄씩 읽어들이는 능력을 가진 보조 문자 스트림으로 변환
                BufferedReader reader = new BufferedReader(isr);

                StringBuffer buffer = new StringBuffer();

                while (true){
                    String line = reader.readLine(); //한줄 읽기
                    if (line==null) break;
                    buffer.append(line+"\n");

                }

                tv.setText(buffer.toString());
                reader.close();



            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }
}