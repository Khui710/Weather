package com.pxy.miniweather.http;

import com.pxy.miniweather.entity.CityInfo;
import com.pxy.miniweather.entity.JsonRootBean;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("baidu")
    Observable<JsonRootBean> getWeather(@Query("city") String city);

    @GET("so-baidu-img")
    Observable<CityInfo> getCityImage(@Query("msg") String city, @Query("page") Integer page);

}
