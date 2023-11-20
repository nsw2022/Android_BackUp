package com.nsw2022.ex83pritice;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RetrofitService {
    @GET("04Retrofit/board.json")
    Call<Item> getJson();

    @GET("{folder}/{fileName}")
    Call<Item> getJsonPath(@Path("folder") String path,@Path("fileName") String fileName);
}
