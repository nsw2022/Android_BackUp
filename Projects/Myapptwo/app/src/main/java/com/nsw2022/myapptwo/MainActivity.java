package com.nsw2022.myapptwo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MyAdapter myAdapter;
    ArrayList<LibraryItem>libraryItems= new ArrayList<>();

    String address="http://openapi.seoul.go.kr:8088/4d6e42445a746d6437354e65737162/xml/SeoulLibraryTimeInfo/1/1000/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=findViewById(R.id.recycler);
        myAdapter = new MyAdapter(this,libraryItems);
        recyclerView.setAdapter(myAdapter);

        findViewById(R.id.btn).setOnClickListener(view -> {

            Thread thread = new Thread(){
                @Override
                public void run() {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            libraryItems.clear();
                            myAdapter.notifyDataSetChanged();
                        }
                    });

                    try {
                        URL url = new URL(address);

                        InputStream is = url.openStream();
                        InputStreamReader isr = new InputStreamReader(is);

                        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                        XmlPullParser xpp = factory.newPullParser();

                        xpp.setInput(isr);

                        int eventType = xpp.getEventType();

                        LibraryItem item=null;

                        while (eventType != XmlPullParser.END_DOCUMENT){

                            switch (eventType){

                                case XmlPullParser.START_DOCUMENT:
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(MainActivity.this, "파싱 테스트", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    break;
                                // 도서관이름, 구주소, 상세주소, 번호, 공휴일
                                case XmlPullParser.START_TAG:
                                    String tagName=xpp.getName();
                                    if (tagName.equals("row")){
                                        item=new LibraryItem();

                                    }else if(tagName.equals("LBRRY_NAME")){
                                        xpp.next();
                                        item.LBRRY_NAME=xpp.getText();
                                    }else if (tagName.equals("CODE_VALUE")){
                                        xpp.next();
                                        item.CODE_VALUE=xpp.getText();
                                    }else if (tagName.equals("ADRES")){
                                        xpp.next();
                                        item.ADRES=xpp.getText();
                                    }else if (tagName.equals("FDRM_CLOSE_DATE")){
                                        xpp.next();
                                        item.FDRM_CLOSE_DATE=xpp.getText();
                                   }else if (tagName.equals("TEL_NO")) {
                                        xpp.next();
                                        item.TEL_NO = xpp.getText();
                                    }
                                    break;

                                case XmlPullParser.END_TAG:
                                    if (xpp.getName().equals("row")){
                                        if (item.CODE_VALUE.equals("강동구"))
                                        libraryItems.add(item);
                                    }
                                    break;

                            }// switch
                            eventType = xpp.next();
                        }// while
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "개수 : "+libraryItems.size(), Toast.LENGTH_SHORT).show();
                                myAdapter.notifyDataSetChanged();
                            }
                        });

                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (XmlPullParserException e) {
                        e.printStackTrace();
                    }


                }
            };

            thread.start();

        });
    }
}