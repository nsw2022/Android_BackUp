package com.nsw2022.pr3retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitService {
    @GET("/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json")
    Call<Result> getMethodTest(@Query("key") String key, @Query("targetDt") String targetDt);
}
