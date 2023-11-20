package com.nsw2022.ex83retrofit2;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHeleper {

   public static String baseUrl="http://tmddn3410.dothome.co.kr/";

    //Retrofit 객체를 만들어서 리턴해주는 기능메소드 [ 객체 생성없이 사용가능하도록 ]
    public static Retrofit getRetrofitInstance(){
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(baseUrl);
        builder.addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();

        return retrofit;
    }



}
