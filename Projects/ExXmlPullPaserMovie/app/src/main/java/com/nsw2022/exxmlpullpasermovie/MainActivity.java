package com.nsw2022.exxmlpullpasermovie;

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

    // 영화진증위원회 open api : 일간 박스오피스 정보를  XML로 받아와서 이쁘게 보여주기

    // 영화진흥위원회 api 사이트에서 발급받은 key
    String apiKey = "8d2005a80ec441c2f87371c2ff655db0";

    ArrayList<MovieItem> movieItems = new ArrayList<>();
    RecyclerView recyclerView;
    MyAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=findViewById(R.id.recycler);
        adapter=new MyAdapter(this, movieItems);
        recyclerView.setAdapter(adapter);

        findViewById(R.id.btn).setOnClickListener(view -> {
            // 영화진흥위원회 open api 서버에서 오늘의 boxoffice 정보를 xml로 읽어와서 분석하여
            // 리사이클러뷰에 이쁘게 보이게 하기..

            // 인터넷을 이용하려면 허가를 받아야 함. [ 퍼미션 - AndroidManifest.xml에서 작성 ]
            // 네츠워크 작업은 반드시 별도의 Thread가 가 해야함
            Thread thread = new Thread(){
                @Override
                public void run() {

                    // 기존 ArrayList의 데이터를 모두 삭제.. 하고 화면갱신도 해야함.
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            movieItems.clear();
                            adapter.notifyDataSetChanged();
                        }
                    });

                    // 오늘의 날짜 얻어오기
                    Date date = new Date(); // 객체가 만들어지는 순간의 날짜와 시간을 가진 객체
                    //date를 하루 전으로 다시 설정
                    date.setTime( date.getTime() - 1000*60*60*24 );

                    SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");

                    String day=sdf.format(date);//yyyymmdd


                    // 읽어올 데이터를 가진 open api URL sample주소
                    //String address="http://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.xml?key=f5eef3421c602c6cb7ea224104795888&targetDt=20120101";

                    // 읽어올 데이터를 가진 open api URL 주소
                    String address="http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.xml"
                            + "?key="+ apiKey
                            + "&targetDt=" + day;




                    try {
                        // 위 서버 주소와 연겨하는 무지개로드(Stream)을 만들어주는 능력을 가진 해임달(URL) 객체 필요
                        URL url = new URL(address);

                        //해임달에게 무지개로드(InputStream)를 열어달라고 요청
                        InputStream is = url.openStream(); // 바이트 스트림
                        InputStreamReader isr=new InputStreamReader(is);// 바이트 --> 문자 스트림

                        // isr를 이용하면 한문자씩만 읽어짐. 이건 너무 번거로움
                        // 그래서 xml 문서를 일정단위로 넘겨가며 분석하여 읽어주는 분석가 객체 사용
                        // XmlPullParser 객체를 이용

                        // xmlPullParser 객체를 만들어주는 공장 객체가 필요함.
                        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                        XmlPullParser xpp= factory.newPullParser();

                        //xpp(분석가)객체에게 읽어올 데이터와 연결된 StreamReader를 설정
                        xpp.setInput(isr);

                        // 분석 시작!
                        int eventType = xpp.getEventType();

                        MovieItem item=null;



                        while ( eventType != XmlPullParser.END_DOCUMENT){

                            switch (eventType){
                                case XmlPullParser.START_DOCUMENT:
                                    // 별도 Thread는 화면 UI를 건드릴 수 없음. 그래서 ui therad에서 토스트를 보여줘야만 함.
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(MainActivity.this, "파싱시작!", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    break;

                                case XmlPullParser.START_TAG:
                                    String tagName = xpp.getName();//태그이름 얻어오기
                                    if (tagName.equals("dailyBoxOffice")) {// 아이템하나 1단위
                                        item=new MovieItem();// 빈 아이템객체 생성

                                    }else if(tagName.equals("rank")){// 순위
                                        xpp.next();
                                        String txt=xpp.getText();
                                        item.rank=txt;

                                    }else if(tagName.equals("movieNm")){// 영화이름
                                        xpp.next();
                                        item.movieNm=xpp.getText();

                                    }else if (tagName.equals("openDt")){// 개봉일
                                        xpp.next();
                                        item.openDt=xpp.getText();
                                    }else if(tagName.equals("salesAcc")){// 누적관계수
                                        xpp.next();
                                        item.audiAcc=xpp.getText();
                                    }
                                    break;

                                case XmlPullParser.TEXT:
                                    break;

                                case XmlPullParser.END_TAG:
                                    if (xpp.getName().equals("dailyBoxOffice")){
                                        //영화 한 단위가 끝남.. ArrayList에 추가..
                                        movieItems.add(item);

                                    }
                                    break;
                            }





                            // 다음 단위로 이동하여 이벤트 종류를 얻어오기
                            eventType = xpp.next();

                        }//while

                        // 파싱이 종료되었다면.. ArrayList에 데이터가 여러개 존재
                        // 그 데이터를 Recylcerview에 이쁘게 보이면 끝.
                        // 일단 , 여러개가 있다는 것만 확인해보기.. 리스트의 사이즈 출력해보면 확인가능
                        // UI작업은 ui thread에서만 가능
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "개수 : "+ movieItems.size(), Toast.LENGTH_SHORT).show();

                                // 아이템리스트의 개수가 변경되었으니.. 아답터에게 새로 화면을 갱신하도록 공지!! 하면.. 리사이클러뷰가
                                // 아이템들을 새롭게 그려냄
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