package com.nsw2022.ex83retrofit2;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface RetrofitService {

    //1. 단순하게 get 방식으로 json 문자열을 읽어오기
    @GET("04Retrofit/board.json") // 접속하는 파일의 경로를 지정
    Call<Item> getJson();

    //2. 경로의 이름을 위 1번처럼 고정하지 않고 함수의 파라미터로 전달 받아 지정할 수 있음.
    @GET("{folder}/{fileName}") // 접속하는 파일의 경로를 지정
    Call<Item> getJsonToPath(@Path("folder") String path,@Path("fileName") String fileNamem);

    //3. GET 방식으로 데이터를 서버에 전달해보기[ @Query ]
    @GET("04Retrofit/getTest.php")
    Call<Item> getMethodTest(@Query("title") String title, @Query("msg") String msg);

    //4. GET 방식으로 데이터 전달 + 파일명도 파라미터로 받아서 지정 [ @Path , @Query ]
    @GET("04Retrofit/{aaa}")
    Call<Item> getMethodTest2(@Path("aaa")String filename,@Query("title") String title,@Query("msg") String msg);

    //5. GET 방식으로 데이트롤 보낼때 Map Collection을 이용하여 한방에 값들 전달해보기 [ @QueryMap ]
    @GET("04Retrofit/getTest.php")
    Call<Item> getMethodTest3(@QueryMap Map<String,String> datas);

    //6. POST 방식으로 데이터를 전달 [ @body ] - 객체 전달하면 자동으로 json문자열로 변환되어 서버에 전달됨
    @POST("04Retrofit/postTest.php")
    Call<Item> postMethodTest( @Body Item item );

    //7. POST 방식으로 개별 데이터 전달 [ @Field ]
    // 단, @Field를 사용하려면 추가로 반드시 @FormUrlEncoded 와 함께 써야 함.
    @FormUrlEncoded
    @POST("04Retrofit/postTest2.php")
    Call<Item> postMethodTest2(@Field("no") int no,@Field("title") String title,@Field("msg") String msg);

    //8. GET방식으로 json array를 읽어와서 곧바로 ArrayList<Item> 변환
    @GET("04Retrofit/boardArray.json")
    Call<ArrayList<Item>> getBoradArray();

    //9. GET 방식으로 서버로부터 응답을 받되. 그냥 글씨로.. 파싱없이.
    @GET("04Retrofit/boardArray.json")
    Call<String> getPlainText();



}
