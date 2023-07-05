package com.pxy.miniweather.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.pxy.miniweather.R;
import com.pxy.miniweather.adapter.MyPagerAdapter;
import com.pxy.miniweather.databinding.ActivityMainBinding;
import com.pxy.miniweather.db.AttentionCity;
import com.pxy.miniweather.db.CityDao;
import com.pxy.miniweather.db.MyDatabase;
import com.pxy.miniweather.fragment.CityWeatherFragment;
import com.xuexiang.xui.XUI;
import com.xuexiang.xui.utils.StatusBarUtils;
import com.xuexiang.xui.utils.XToastUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private List<Fragment> fragmentList = new ArrayList<>();
    // 获取ViewPager2的页数
    private int pageCount;
    private List<ImageView> dots = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //初始化XUI
        XUI.initTheme(this);
        //设置沉浸式状态栏
        StatusBarUtils.translucent(this);
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.cityManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CityManagementActivity.class));
                finish();
            }
        });

        initData();

        initView();
    }

    private void initData() {
        fragmentList.clear();
        Observable.create(new ObservableOnSubscribe<Object>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<Object> emitter) throws Throwable {

                        CityDao cityDao = MyDatabase.getInstance(MainActivity.this).cityDao();
                        List<AttentionCity> cityList = cityDao.getCityList();
                        //如果城市不存在，添加  存在即弹出提示
                        Log.i("TAG", "subscribe: 获取的数量" + cityList.size());
                        if (cityList.size() == 0) {
                            fragmentList.add(CityWeatherFragment.newInstance("北京"));
                        } else {
                            for (int i = 0; i < cityList.size(); i++) {
                                Log.i("TAG", "subscribe: 添加");
                                fragmentList.add(CityWeatherFragment.newInstance(cityList.get(i).getCity()));
                            }
                        }
                        emitter.onNext(1);
                    }
                }).subscribeOn(Schedulers.io()) //子线程
                .observeOn(AndroidSchedulers.mainThread()) //切换到主线程
                .map(new Function<Object, Object>() {
                    @SuppressLint("CheckResult")
                    @Override
                    public Object apply(Object o) throws Throwable {
                        // 设置ViewPager2 Adapter
                        MyPagerAdapter pagerAdapter = new MyPagerAdapter(MainActivity.this, fragmentList);
                        Log.i("TAG", "accept: 此时的fragment长度" + fragmentList.size());
                        binding.viewPager.setAdapter(pagerAdapter);
                        binding.viewPager.setCurrentItem(fragmentList.size() - 1);
                        pageCount = binding.viewPager.getAdapter().getItemCount();
                        // 创建ImageView数组存储小圆点对象
                        // 循环创建小圆点
                        binding.dotsLayout.removeAllViews();
                        dots = new ArrayList<>(fragmentList.size());
                        dots.clear();
                        for (int i = 0; i < fragmentList.size(); i++) {
                            dots.add(new ImageView(MainActivity.this));
                            dots.get(i).setImageResource(R.drawable.dot_unselected);
                            Log.i("TAG", "accept: 执行" + i);
                            // 设置小圆点布局参数
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                            );
                            params.gravity = Gravity.CENTER;
                            params.setMargins(8, 0, 8, 0); // 设置小圆点之间的距离
                            binding.dotsLayout.addView(dots.get(i), params); // 添加小圆点到容器中
                        }
                        //设置最后一个小圆点选中状态
                        dots.get(fragmentList.size() - 1).setImageResource(R.drawable.dot_selected);
                        return 1;
                    }
                })
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object isExist) throws Throwable {
                        //监听ViewPager2页面切换事件
                        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                            @Override
                            public void onPageSelected(int position) {
                                Log.i("TAG", "onPageSelected: 当前页" + position);
                                Log.i("TAG", "onPageSelected: 当前长度" + dots.size());
//                                Log.i("TAG", "onPageSelected: 总长度"+dots.length);
                                //先将所有小圆点设置为未选中状态
                                for (int i = 0; i < pageCount; i++) {
                                    dots.get(i).setImageResource(R.drawable.dot_unselected);
                                }
                                //再将当前页对应的小圆点设置为选中状态
                                dots.get(position).setImageResource(R.drawable.dot_selected);
                            }
                        });
                    }
                });
    }
    private void initView() {
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
}