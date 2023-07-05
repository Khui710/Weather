package com.pxy.miniweather.http;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyRetrofit {
    public static Retrofit getWetherRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("https://query.asilu.com/weather/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();//
    }

    public static Retrofit getCityInfoRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("https://zj.v.api.aa1.cn/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();//
    }

}
