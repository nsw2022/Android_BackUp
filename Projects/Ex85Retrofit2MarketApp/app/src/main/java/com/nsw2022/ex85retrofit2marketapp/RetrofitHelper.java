package com.nsw2022.ex85retrofit2marketapp;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitHelper {

    public static final String BASE_URL="http://tmddn3410.dothome.co.kr";

    // 레트로핏 객체를 만들어서 리턴해주는 기능.
    // Converter 가 여러개일때는 순서가 중요함.
    public static Retrofit getRetrofitInstance(){
        Retrofit.Builder builder= new Retrofit.Builder();
        builder.baseUrl(BASE_URL);
        builder.addConverterFactory(ScalarsConverterFactory.create());
        builder.addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit= builder.build();

        return retrofit;
    }

}
