package com.pxy.miniweather.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.pxy.miniweather.R;
import com.pxy.miniweather.adapter.WeatherAdapter;
import com.pxy.miniweather.databinding.FragmentCityWeatherBinding;
import com.pxy.miniweather.entity.JsonRootBean;
import com.pxy.miniweather.entity.Weather;
import com.pxy.miniweather.http.ApiService;
import com.pxy.miniweather.http.MyRetrofit;
import com.xuexiang.citypicker.CityPicker;
import com.xuexiang.citypicker.adapter.OnLocationListener;
import com.xuexiang.citypicker.adapter.OnPickListener;
import com.xuexiang.citypicker.model.City;
import com.xuexiang.xui.utils.XToastUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

    public class CityWeatherFragment extends Fragment {
    private static final String ARG_TITLE = "title";
    private FragmentCityWeatherBinding binding;
    private List<Weather> weather = new ArrayList<>();
    private String city = "";
    private String TAG = "TAG";
    private WeatherAdapter adapter;

    public static CityWeatherFragment newInstance(String title) {
        CityWeatherFragment fragment = new CityWeatherFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCityWeatherBinding.inflate(getLayoutInflater());
        // 获取传递过来的标题
        city = getArguments().getString(ARG_TITLE);

        //初始化适配器
        adapter = new WeatherAdapter(getActivity());
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerview.setAdapter(adapter);

        initView();


        initData(city);
        return binding.getRoot();
    }

    private void initView() {
        binding.cityName.setText(city);
    }

    private void initData(String city) {
        ApiService apiService = MyRetrofit.getWetherRetrofit().create(ApiService.class);
        apiService.getWeather(city)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonRootBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull JsonRootBean resultBaseResponse) {
                        weather = resultBaseResponse.getWeather();
                        Weather current_weather = weather.get(0);
                        binding.cityName.setText(resultBaseResponse.getCity());
                        binding.date.setText(resultBaseResponse.getDate());
                        binding.weather.setText(current_weather.getWeather());
                        binding.updateTime.setText("更新时间：" + resultBaseResponse.getUpdate_time());
                        binding.temp.setText(current_weather.getTemp());
                        binding.wind.setText("风向：" + (current_weather.getWind()));
                        //binding.wind.setText("风力："+current_weather.getIcon1());
                        //设置列表数据
                        adapter.setDatas(weather.subList(1, weather.size() - 1));
                    }

                    @Override
                    public void onError(Throwable e) {
                        // 处理请求失败的情况
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }
}
