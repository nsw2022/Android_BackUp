package com.nsw2022.ex83pritice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.nsw2022.ex83pritice.databinding.ActivityMainBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btn.setOnClickListener(v->clickBtn());
        binding.btn2.setOnClickListener(v->clickBtn2());
    }

    void clickBtn2(){
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl("http://tmddn3410.dothome.co.kr/");
        builder.addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit=builder.build();

        RetrofitService retrofitService = retrofit.create(RetrofitService.class);

        //Call<Item> call = retrofitService.getJsonPath("04Retrofit","board.json");
        Call<Item> call= retrofitService.getJsonPath("04Retrofit", "board.json");

        call.enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
                Item item = response.body();
                binding.tv.setText(item.no + " : " + item.title + "\n" + item.msg);
            }

            @Override
            public void onFailure(Call<Item> call, Throwable t) {
                binding.tv.setText("실패 : " + t.getMessage());
            }
        });
    }

    void clickBtn(){
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl("http://tmddn3410.dothome.co.kr/");
        builder.addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit=builder.build();

        RetrofitService retrofitService = retrofit.create(RetrofitService.class);

        Call<Item> call = retrofitService.getJson();

        call.enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
                Item item = response.body();
                binding.tv.setText(item.no + " : " + item.title + " - " + item.msg);

            }

            @Override
            public void onFailure(Call<Item> call, Throwable t) {
                binding.tv.setText("실패 : " + t.getMessage() );
            }
        });





    }

}