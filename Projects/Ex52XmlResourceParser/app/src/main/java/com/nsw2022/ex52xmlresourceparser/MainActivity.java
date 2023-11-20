package com.nsw2022.ex52xmlresourceparser;


import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv= findViewById(R.id.tv);
        findViewById(R.id.btn).setOnClickListener(view->{
            // res 폴더 안에 있는 xml 문서를 읽어와서 분석하여 TextView에 이쁘게 보이기~

            // res폴더 창고관리자 소환
            Resources res= getResources();

            // 창고관리자로부터 파서객체 얻어오기
            XmlResourceParser xrp= res.getXml(R.xml.movies);

            // xml 파서에게 분석 작업 요청.
            try {
                xrp.next();
                int eventType= xrp.getEventType();

                StringBuffer buffer= new StringBuffer();

                // 커서를 이동하면서 문서의 끝이 아니면 {} 안에 영역을 실행
                while( eventType != XmlResourceParser.END_DOCUMENT){

                    switch ( eventType ){
                        case XmlResourceParser.START_DOCUMENT:
                            buffer.append("xml 파싱을 시작합니다..\n\n");
                            break;

                        case XmlResourceParser.START_TAG:
                            String tagName= xrp.getName(); //태그문의 이름 얻어오기

                            if(tagName.equals("item")){

                            }else if(tagName.equals("no")){
                                buffer.append("번호:");
                            }else if(tagName.equals("title")){
                                buffer.append("제목:");
                            }else if(tagName.equals("genre")){
                                buffer.append("장르");
                            }

                            break;

                        case XmlResourceParser.TEXT:
                            String txt= xrp.getText();
                            buffer.append(txt);
                            break;

                        case XmlResourceParser.END_TAG:
                            String tagName2= xrp.getName();
                            if(tagName2.equals("item")){
                                buffer.append("-----------------\n");
                            }else if(tagName2.equals("no")){
                                buffer.append("\n");
                            }else if(tagName2.equals("title")){
                                buffer.append("\n");
                            }else if(tagName2.equals("genre")){
                                buffer.append("\n");
                            }

                            break;

                        case XmlResourceParser.END_DOCUMENT:
                            break;
                    }

                    xrp.next();
                    eventType= xrp.getEventType();
                }//while

                buffer.append("\n\n 파싱을 완료하였습니다.\n");
                tv.setText( buffer.toString() );

            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }


        });
    }
}