package com.nsw2022.ex80httprequestdb;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.net.Uri;
import android.os.Bundle;

import com.nsw2022.ex80httprequestdb.databinding.ActivityBoardBinding;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class BoardActivity extends AppCompatActivity {

    ActivityBoardBinding binding;

    ArrayList<BoardItem> boardItems = new ArrayList<>();
    BoardAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityBoardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loadData();

        adapter=new BoardAdapter(this,boardItems);
        binding.recyclerView.setAdapter(adapter);

    }

    void loadData(){
        // 우선 테스트 목적으로 더미데이터 작성
       // boardItems.add(new BoardItem(5,"aaa","AAAAAAA","2022-10") );

        //서버에서 Data를 가져오기.
        new Thread(){
            @Override
            public void run() {
                String serverUrl="http://tmddn3410.dothome.co.kr/03AndroidHttp/selectDB.php";

                try {
                    URL url = new URL(serverUrl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setDoInput(true);
                    connection.setUseCaches(false);

                    InputStream is = connection.getInputStream();
                    InputStreamReader isr= new InputStreamReader(is);
                    BufferedReader reader = new BufferedReader(isr);
                    StringBuffer buffer = new StringBuffer();
                    while (true){
                        String line = reader.readLine();
                        if (line==null) break;
                        buffer.append(line+"\n");
                    }

                     //우선잘읽어왔는지 확인
//                    runOnUiThread(()->{
//                        new AlertDialog.Builder(BoardActivity.this).setMessage(buffer.toString()).show();
//                    });

                    // csv형식으로 전달된 데이터를 분석(parse)
                    String s= buffer.toString();

                    // 우선 여러줄(record)로 데이터가 왔기에.. 게시글들 분리..
                    String[] rows = s.split("&");//"&"문자를 기준으로 문자열을 분리하여 배열로 리턴

                    for( String row : rows) {
                        // 한줄 row 안에서 다시 "," 구분자를 통해 필드 값들을 다시 분리..
                        String[] datas = row.split(",");
                        if (datas.length == 4) {
                            int no = Integer.parseInt(datas[0]);
                            String title = datas[1];
                            String msg = datas[2];
                            String date = datas[3];

                            //대량의 데이터에 추가하기위해 BoardItem객체로 생성
                            BoardItem item = new BoardItem(no, title, msg, date);
                            //ArrayList에 추가..
                            boardItems.add(item);
                        }

                    }//for

                    // 리사이클러뷰가 보여줄 데이터의 개수가 변경되면.. 이를 아답터에게
                    // 공지해야 리사이클러뷰가 갱신됨.
                    runOnUiThread(()->adapter.notifyDataSetChanged() );


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }.start();
    }
}