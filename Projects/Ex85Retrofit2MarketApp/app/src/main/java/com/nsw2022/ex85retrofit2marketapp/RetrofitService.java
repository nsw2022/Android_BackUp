package com.nsw2022.ex85retrofit2marketapp;

import java.util.ArrayList;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface RetrofitService {

    //POST 방식으로 데이터를 보낼때.. @Field 사용 - @FormUrlEncoded 와 함께 써야만 했음.
    //이미지 파일을 보낼때는 @Part 사용 - @Multipart 와 함께 써야만 했음.
    //문제는 @FormUrlEncoded와 @Multipart 를 함께 사용할 수 없음.
    //그래서 @Field처럼 php 에서 $_POST로 받으려면, 마치 GET방식의 @QueryMap처럼 @PartMap 사용
    @Multipart
    @POST("05Retrofit/insertDB.php")
    Call<String> postDataToServer(@PartMap Map<String, String> dataPart,
                                  @Part MultipartBody.Part filePart);

    @GET("05Retrofit/loadDB.php")
    Call<ArrayList<MarketItem>> loadDataFromServer();

}
