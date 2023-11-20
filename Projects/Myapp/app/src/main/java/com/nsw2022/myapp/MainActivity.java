package com.nsw2022.myapp;

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


    String apiKey = "4d6e42445a746d6437354e65737162";


    RecyclerView recyclerView;
    MyAdapter adapter;
    ArrayList<LibraryItem> libraryItemArrayList =new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler);
        adapter = new MyAdapter(this, libraryItemArrayList);
        recyclerView.setAdapter(adapter);

        findViewById(R.id.btn).setOnClickListener(view -> {

            Thread thread = new Thread(){
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            libraryItemArrayList.clear();
                            adapter.notifyDataSetChanged();
                        }
                    });

                    String address="http://openapi.seoul.go.kr:8088/4d6e42445a746d6437354e65737162/xml/SeoulLibraryTimeInfo/1/1000/";

                    try {
                        URL url = new URL(address);

                        InputStream is = url.openStream();
                        InputStreamReader isr = new InputStreamReader(is);

                        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                        XmlPullParser xpp=factory.newPullParser();

                        xpp.setInput(isr);

                        int eventType = xpp.getEventType();

                        LibraryItem item=null;

                        while (eventType != XmlPullParser.END_DOCUMENT){

                            switch (eventType){
                                case XmlPullParser.START_DOCUMENT:
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(MainActivity.this, "파싱시작", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    break;
                                    //뽑을데이터 //이름 공휴일 도서관번호 주소
                                case XmlPullParser.START_TAG:
                                    String tagName=getName();
                                    if ( tagName.equals("row") ){//아이템단위
                                        xpp.next();
                                        item=new LibraryItem();

                                    }else if(tagName.equals("LBRRY_NAME")){//이름
                                        xpp.next();
                                        String txt=xpp.getText();
                                        item.LBRRY_NAME=txt;
                                    }else if(tagName.equals("FDRM_CLOSE_DATE")){//공휴일
                                        xpp.next();
                                        item.FDRM_CLOSE_DATE=xpp.getText();
                                    }else if(tagName.equals("TEL_NO")){//도서관 번호
                                        xpp.next();
                                        item.TEL_NO=xpp.getText();
                                    }else if(tagName.equals("ADRES")){
                                        xpp.next();
                                        item.ADRES=xpp.getText();
                                    }
                                    break;

                                case XmlPullParser.END_TAG:
                                    if (xpp.getName().equals("row")){
                                        libraryItemArrayList.add(item);
                                    }

                            }

                            eventType=xpp.next();
                        }//while

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "개수 : "+ libraryItemArrayList.size(), Toast.LENGTH_SHORT).show();
                                adapter.notifyDataSetChanged();
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