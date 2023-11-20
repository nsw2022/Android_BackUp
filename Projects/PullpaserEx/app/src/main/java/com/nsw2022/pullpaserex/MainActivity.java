package com.nsw2022.pullpaserex;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    String apiKey = "8d2005a80ec441c2f87371c2ff655db0";

    ArrayList<MovieItem> movieItems = new ArrayList<>();
    RecyclerView recyclerView;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=findViewById(R.id.recycler);
        adapter=new MyAdapter(movieItems,this);
        recyclerView.setAdapter(adapter);



        findViewById(R.id.btn).setOnClickListener(view -> {
            Thread thread =new Thread();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    movieItems.clear();
                    adapter.notifyDataSetChanged();
                }
            });

            Date date = new Date();

            date.setTime( date.getTime() - 1000*60*60*24 );

            SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");

            String day=sdf.format(date);

            String address="http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.xml"
                    + "?key="+ apiKey
                    + "&targetDt=" + day;

            try {
                URL url = new URL(address);

                InputStream is = url.openStream();
                InputStreamReader isr = new InputStreamReader(is);

                XmlPullParserFactory factory=XmlPullParserFactory.newInstance();
                XmlPullParser xpp = factory.newPullParser();

                xpp.setInput(isr);

                int eventType = xpp.getEventType();

                MovieItem item=null;

                while (eventType != XmlPullParser.END_DOCUMENT){

                    switch (eventType){
                        case XmlPullParser.START_DOCUMENT:
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainActivity.this, "파싱시작!", Toast.LENGTH_SHORT).show();
                                }
                            });
                    }





                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }


        });






    }
}