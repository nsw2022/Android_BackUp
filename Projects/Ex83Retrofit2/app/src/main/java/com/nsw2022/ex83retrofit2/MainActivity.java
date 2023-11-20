package com.nsw2022.ex83retrofit2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;

import android.os.Bundle;
import android.telephony.mbms.DownloadRequest;

import com.nsw2022.ex83retrofit2.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class MainActivity extends AppCompatActivity {

    // HTTP 통신 작업을 위한 Library
    // 1. OkHttp    - 처음 개발된 라이브러리 oracle 회사가 보유
    // 2. Retrofit  - OkHttp를 기반으로 개량한 라이브러리 - sqareup 회사가 보유 [현재 가장 많이 사용됨] - 현재는 version 2
    // 3. Volley    - Google 에서 제작...하여서 많은 기대를 받았고 사용되어 왔으나 업데이트 지원정책에 혼선이 발생함.[ 현재 사용은 조금 애매함 ]

    // 3개중에서 현업에서 가장 많이 사용되는 Retrofit2 라이브러리를 사용.
    // Retrofit 라이브러리의 특징 : 기본적으로 json 데이터를 주고받는데 특화되어 있음.
    // 어노테이션 @ 많이 사용.

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btn.setOnClickListener(v->clickBtn());
        binding.btn2.setOnClickListener(v->clickBtn2());
        binding.btn3.setOnClickListener(v->clickBtn3());
        binding.btn4.setOnClickListener(v->clickBtn4());
        binding.btn5.setOnClickListener(v->clickBtn5());
        binding.btn6.setOnClickListener(v->clickBtn6());
        binding.btn7.setOnClickListener(v->clickBtn7());
        binding.btn8.setOnClickListener(v->clickBtn8());
        binding.btn9.setOnClickListener(v->clickBtn9());
    }

    void clickBtn9(){
        // 서버 응답결과를 그냥 Text로 받아오기. json문자열이 아닐때는 반드시 필요한 기술
        // json이 아니므로 GsonConverter를 사용하지 않음.
        // 단순하게 글씨를 결과를 받는 Converter : ScalarsConverter [ 라이브러리 추가 필요 ]

            Retrofit.Builder builder= new Retrofit.Builder();
            builder.baseUrl("http://tmddn3410.dothome.co.kr/");
            builder.addConverterFactory(ScalarsConverterFactory.create());
            Retrofit retrofit = builder.build();

            RetrofitService retrofitService = retrofit.create(RetrofitService.class);
            Call<String> call =retrofitService.getPlainText();
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    String s = response.body();
                    binding.tv.setText(s);

                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    binding.tv.setText("error :" + t.getMessage());
                }
            });

    }

    void clickBtn8(){
        // 서버의 응답 데이터가 json array 일때 자동 파싱하여 ArrayList로 곧바로 받기

        Retrofit retrofit = RetrofitHeleper.getRetrofitInstance();
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);
        Call<ArrayList<Item>> call = retrofitService.getBoradArray();
        call.enqueue(new Callback<ArrayList<Item>>() {
            @Override
            public void onResponse(Call<ArrayList<Item>> call, Response<ArrayList<Item>> response) {
                ArrayList<Item> items = response.body();
                StringBuffer buffer = new StringBuffer();
                for ( Item item : items ){
                    buffer.append(item.no + " : " + item.title + " - " + item.msg + "\n");
                }

                binding.tv.setText(buffer.toString());

            }

            @Override
            public void onFailure(Call<ArrayList<Item>> call, Throwable t) {

            }
        });

    }

    void clickBtn7(){
        //POST 방식으로 데이터를 따로따로 보내기... : 마치 GET 방식의 @Query처럼
        int no=2;
        String title="bbb";
        String message="Nice to meet you";

        Retrofit retrofit= RetrofitHeleper.getRetrofitInstance();
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);
        Call<Item> call= retrofitService.postMethodTest2(no, title, message);
        call.enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
                Item item= response.body();
                binding.tv.setText(item.no +" : " + item.title +"\n"+ item.msg);
            }

            @Override
            public void onFailure(Call<Item> call, Throwable t) {
                binding.tv.setText("error : " + t.getMessage());
            }
        });



    }

    void clickBtn6(){
        //POST 방식으로 데이터 보내기 - 보통은 보낼데이터를 객체로 만들어져 있는 경우가 많음 - [ Retrofit이 자동으로 객체를 json문자열로 변화하여 POST 함. ]
        Item item = new Item(1,"aaa","Hello world");

        Retrofit retrofit = RetrofitHeleper.getRetrofitInstance();
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);

        // 서버에 보낼 데이터들을 가진 객체를 그냥 통으로 보내기
        Call<Item> call = retrofitService.postMethodTest(item);
        call.enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
                Item i = response.body();
                binding.tv.setText(i.no+" : " + i.title + "\n" + i.msg);
            }

            @Override
            public void onFailure(Call<Item> call, Throwable t) {
                binding.tv.setText("error" + t.getMessage());
            }
        });


    }

    void clickBtn5(){
        // GET 방식 데이터들을 한방에 보내기.. [ Map 컬랙션 이용 ]

        // 서버에 전달할 데이터들을 하나의 Map으로 묶기/
        HashMap<String,String> datas= new HashMap<>();
        datas.put("title","Nice");
        datas.put("msg","Have a good day");

        // 1)Retrofit 객체 생성 - RetrofitHelper 이용
        Retrofit retrofit = RetrofitHeleper.getRetrofitInstance();

        //2,3) 추상메소드 설계 및 인터페이스 객체 생성
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);

        //4)추상메소드 호출
        Call<Item> call = retrofitService.getMethodTest3(datas);

        //5) 작업시작 및 응답 듣기
        call.enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
                Item item = response.body();
                binding.tv.setText(item.title+" : "+item.msg);
            }

            @Override
            public void onFailure(Call<Item> call, Throwable t) {
                binding.tv.setText("error :"+t.getMessage());
            }
        });


    }

    void clickBtn4(){
        //GET 방식 데이터 전달 + 파일경로도 지정 [ 2번 + 3번 예제 ]

        String title="Good";
        String message="Good morning";

        // 1) Retrofit 객체 생성 - 항상 4줄씩 쓰기가 번거로움. 그래서 별도 클래스에 기능으로 만들기.
        Retrofit retrofit = RetrofitHeleper.getRetrofitInstance();

        // 2,3) 인터페이스 안에 추상메소드 설계 및 객체 생성
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);

        // 4)기능메소드 호출
        Call<Item> call = retrofitService.getMethodTest2("getTest.php",title,message);

        //5 작업시작
        call.enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
                Item item = response.body();
                binding.tv.setText(item.title + "\n" + item.msg );
            }

            @Override
            public void onFailure(Call<Item> call, Throwable t) {
                binding.tv.setText("error : "+t.getMessage());
            }
        });


    }

    void clickBtn3(){
        // GET 방식으로 데이터를 서버에 전달해보기

        //서버에 보낼 데이터들
        String title="안녕하세요.";
        String message="만나서 반가워요~";

        //1) Retrofit 객체 생성
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl("http://tmddn3410.dothome.co.kr");
        builder.addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();

        // 2) RetrofitService 인터페이스 설계 [ 원하는 GET 작업 추상메소드 설계 ]
        //    getMethodTest();

        // 3) RetrofitService 인터페이스 객체 생성
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);

        // 4) 서비스객체의 추상메소드 호출하면서 서버에 보낼 데이터를 파라미터로 전달
        Call<Item> call = retrofitService.getMethodTest(title,message);

        // 5) 네트워크 작업 지삭 및 응답 받도록 callback 객체 등록
        call.enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
                Item item = response.body();
                binding.tv.setText(item.title+" - "+item.msg);
            }

            @Override
            public void onFailure(Call<Item> call, Throwable t) {
                binding.tv.setText("error : " + t.getMessage());
            }
        });


    }

    void clickBtn2(){
        // 경로의 이름을 파라미터로 전달하여 데이터 가져오기

        //1) Retrofit객체 생성
        Retrofit.Builder builder= new Retrofit.Builder();
        builder.baseUrl("http://mrhi2022.dothome.co.kr");
        builder.addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit= builder.build();

        //2) RetrofitService 인터페이스 설계 [ 원하는 GET, POST 동작을 수행하는 추상메소드 설계 ]
        //   getJsonToPath() 메소드 설계

        //3) RetrofitService 인터페이스 객체 생성
        RetrofitService retrofitService= retrofit.create(RetrofitService.class);

        //4) 2)단계에서 설계한 추상메소드를 호출
        Call<Item> call= retrofitService.getJsonToPath("04Retrofit", "board.json");

        //5) 네트워크 작업 시작!
        call.enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
                // 응답된 json을 Gson을 이용하여 Item객체로 자동 파싱한 결과 받기
                Item item= response.body();
                binding.tv.setText(item.no +" : " + item.title + "\n" + item.msg);
            }

            @Override
            public void onFailure(Call<Item> call, Throwable t) {
                binding.tv.setText( "실패 : " + t.getMessage() );
            }
        });
    }



    void clickBtn(){
        // 단순하게 GET방식으로 json문자열을 읽어오기

        // Retrofit Library를 이용하여 서버에서 json 데이터를 읽어와서 Item 객체로 곧바로 파싱

        //1. Retrofit 객체 생성
        Retrofit.Builder builder= new Retrofit.Builder();
        builder.baseUrl("http://tmddn3410.dothome.co.kr/");
        builder.addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit= builder.build();

        //2. Service 인터페이스 설계 [ 원하는 GET, POST 동작을 하는 추상메소드 설계 ]
        //   통신이 이루어졌으면 하는 방법과 경로를 지정해주는 코드

        //3. 2단계작업에서 설계한 RetrofitService 인터페이스를 객체로 생성
        RetrofitService retrofitService= retrofit.create(RetrofitService.class);

        //4. 위에서 만든 서비스객체의 추상메소들 호출하여 실제 서버작업을 수행하는 코드를 가진
        //   Call 이라는 객체를 리턴받기
        Call<Item> call= retrofitService.getJson();

        //5. 위 4단계에서 리턴받은 call 객체에게 네트워크 작업을 수행하도록 요청!
        call.enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
                //파라미터로 전달된 응답객체(response)로부터 GSON 라이브러리에 의해 자동으로
                //Item객체로 변환되어 있는 데이터 값 얻어오기
                Item item= response.body();
                binding.tv.setText( item.no +" : " + item.title +" - " + item.msg );
            }

            @Override
            public void onFailure(Call<Item> call, Throwable t) {
                binding.tv.setText("실패 : " + t.getMessage() );
            }
        });
    }

}