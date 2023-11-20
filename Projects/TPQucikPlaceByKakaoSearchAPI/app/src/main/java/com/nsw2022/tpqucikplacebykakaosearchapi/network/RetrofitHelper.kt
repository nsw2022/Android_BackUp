package com.nsw2022.tpqucikplacebykakaosearchapi.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class RetrofitHelper {
    companion object{
        fun getRetrofitInstance(baseUrl:String): Retrofit{
            val retrofit= Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit
        }
    }
}