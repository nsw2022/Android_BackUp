package com.nsw2022.ex92naversearchapi


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface NaverApiRetrofitService {

    // 우선 Naver의 검색 API에 json 결과를 파싱하지 않고 그냥 문자열로 받아오는 추상메소드 작성
    @GET("/v1/search/shop.json")
    fun searchDataToString(@Query("query") query : String,
                           @Header("X-Naver-Client-Id") clientId:String,
                           @Header("X-Naver-Client-Secret") clientSecret:String) : Call<String>

    // 검색결과 json 형식을 NaverSearchResponse 객체로 자동 파싱하여 받아오는 기능 추상메소드 작성
    // Header 에 값이 고정적이며 여러개라면..
    @Headers("X-Naver-Client-Id: NLXJ9ugyS2Z2IpJLK4rF" ,"X-Naver-Client-Secret: Thb3vbC3mT")
    @GET("/v1/search/shop.json?display=50")
    fun searchData(@Query("query") query : String):Call<NaverSearchResponse>
}