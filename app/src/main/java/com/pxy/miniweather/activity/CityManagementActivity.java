package com.pxy.miniweather.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.pxy.miniweather.R;
import com.pxy.miniweather.adapter.CityAdapter;
import com.pxy.miniweather.databinding.ActivityCityManagementBinding;
import com.pxy.miniweather.db.AttentionCity;
import com.pxy.miniweather.db.CityDao;
import com.pxy.miniweather.db.MyDatabase;
import com.xuexiang.citypicker.CityPicker;
import com.xuexiang.citypicker.adapter.OnLocationListener;
import com.xuexiang.citypicker.adapter.OnPickListener;
import com.xuexiang.citypicker.model.City;
import com.xuexiang.xui.XUI;
import com.xuexiang.xui.utils.XToastUtils;
import com.xuexiang.xui.widget.actionbar.TitleBar;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CityManagementActivity extends AppCompatActivity {
    private ActivityCityManagementBinding binding;

    private List<AttentionCity> cityList = new ArrayList<>();
    private CityAdapter adapter;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //初始化XUI
        XUI.initTheme(this);
        super.onCreate(savedInstanceState);
        binding = ActivityCityManagementBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.title.setLeftClickListener(view -> onBackPressed()).setCenterClickListener(v -> XToastUtils.toast("城市管理")).addAction(new TitleBar.ImageAction(R.drawable.icon_add) {
            @Override
            public void performAction(View view) {
                pickCity();
            }
        });

        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CityAdapter(this);
        binding.recyclerview.setAdapter(adapter);
        initData();
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@androidx.annotation.NonNull Message msg) {
                if (msg.what == 0X10001) {
                    adapter.setDatas(cityList);
                }
                return false;
            }
        });
    }

    private void initData() {
        //获取存储的城市数据
        Observable.create(new ObservableOnSubscribe<Object>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<Object> emitter) throws Throwable {
                        cityList.clear();
                        CityDao cityDao = MyDatabase.getInstance(CityManagementActivity.this).cityDao();
                        cityList = cityDao.getCityList();
                        //没有存储的城市，就添加默认北京
                        if (cityList.size() == 0) {
                            cityList.add(new AttentionCity("北京"));
                        }
                        emitter.onNext(1);
                    }
                }).subscribeOn(Schedulers.io()) //子线程
                .observeOn(AndroidSchedulers.mainThread()) //切换到主线程
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Throwable {
                        handler.sendEmptyMessage(0X10001);
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    //选择城市
    private void pickCity() {
        CityPicker.from(this)
//                .enableAnimation(mEnableAnimation)
//                .setAnimationStyle(mAnim)
                .setLocatedCity(null)
//                .setHotCities(mHotCities)
                .setOnPickListener(new OnPickListener() {
                    @Override
                    public void onPick(int position, City data) {
                        Observable.create(new ObservableOnSubscribe<Integer>() {
                                    @Override
                                    public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Throwable {
                                        CityDao cityDao = MyDatabase.getInstance(CityManagementActivity.this).cityDao();
                                        Log.i("TAG", "subscribe: 长度"+cityDao.getCityCount());
                                       if (cityDao.getCityCount()>=8){
                                           emitter.onNext(1);
                                           return;
                                       }
                                        AttentionCity city = cityDao.getCityByName(data.getName());
                                        //如果城市不存在，添加  存在即弹出提示
                                        if (city == null) {
                                            cityDao.insertCity(new AttentionCity(data.getName()));
                                            emitter.onNext(2);
                                        } else {
                                            emitter.onNext(3);
                                        }
                                    }
                                }).subscribeOn(Schedulers.io()) //子线程
                                .observeOn(AndroidSchedulers.mainThread()) //切换到主线程
                                .subscribe(new Consumer<Integer>() {
                                    @Override
                                    public void accept(Integer info) throws Throwable {
                                        switch (info){
                                            case 1:
                                                XToastUtils.toast("最多添加8个城市，请删除再添加");
                                                break;
                                            case 2:
                                                XToastUtils.toast("添加成功");
                                                break;
                                            case 3:
                                                XToastUtils.toast("该城市已经存在");
                                                break;
                                        }
                                        initData();
                                    }
                                });

                    }

                    @Override
                    public void onCancel() {
                        XToastUtils.toast("取消选择");
                    }

                    @Override
                    public void onLocate(final OnLocationListener locationListener) {

                    }
                })
                .show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(CityManagementActivity.this,MainActivity.class));
    }
}