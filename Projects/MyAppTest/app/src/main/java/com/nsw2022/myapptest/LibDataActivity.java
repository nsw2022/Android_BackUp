package com.nsw2022.myapptest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
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

public class LibDataActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecycelAdapter Adapter;
    ArrayList<RecycelItem> libraryItems = new ArrayList<>();

    Spinner Home_spinner;
    String[] gu_arrays;
    String gu=null;
    String address = "http://openapi.seoul.go.kr:8088/4d6e42445a746d6437354e65737162/xml/SeoulLibraryTimeInfo/1/1000/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lib_data);

        recyclerView = findViewById(R.id.recycler);
        Adapter = new RecycelAdapter(this, libraryItems);
        recyclerView.setAdapter(Adapter);

        gu_arrays=getResources().getStringArray(R.array.city);
        Home_spinner=findViewById(R.id.spinner);
        Home_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                gu = gu_arrays[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        findViewById(R.id.btn).setOnClickListener(view -> {

            Thread thread = new Thread() {
                @Override
                public void run() {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            libraryItems.clear();
                            Adapter.notifyDataSetChanged();
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

                        RecycelItem item = null;

                        while (eventType != XmlPullParser.END_DOCUMENT) {

                            switch (eventType) {

                                case XmlPullParser.START_DOCUMENT:
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(LibDataActivity.this, "파싱 테스트", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    break;
                                // 도서관이름, 구주소, 상세주소, 번호, 공휴일
                                case XmlPullParser.START_TAG:
                                    String tagName = xpp.getName();
                                    if (tagName.equals("row")) {
                                        item = new RecycelItem();

                                    } else if (tagName.equals("LBRRY_NAME")) {
                                        xpp.next();
                                        item.LBRRY_NAME = xpp.getText();
                                    } else if (tagName.equals("CODE_VALUE")) {
                                        xpp.next();
                                        item.CODE_VALUE = xpp.getText();
                                    } else if (tagName.equals("ADRES")) {
                                        xpp.next();
                                        item.ADRES = xpp.getText();
                                    } else if (tagName.equals("FDRM_CLOSE_DATE")) {
                                        xpp.next();
                                        item.FDRM_CLOSE_DATE = xpp.getText();
                                    } else if (tagName.equals("TEL_NO")) {
                                        xpp.next();
                                        item.TEL_NO = xpp.getText();
                                    }
                                    break;

                                case XmlPullParser.END_TAG:
                                    if (xpp.getName().equals("row")) {
                                        if (item.CODE_VALUE.equals(gu))
                                        libraryItems.add(item);
                                    }
                                    break;

                            }// switch
                            eventType = xpp.next();
                        }// while
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LibDataActivity.this, "개수 : " + libraryItems.size(), Toast.LENGTH_SHORT).show();
                                Adapter.notifyDataSetChanged();
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

        findViewById(R.id.btn_home).setOnClickListener(v->{
            Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);
        });

    }//onCreate
}


