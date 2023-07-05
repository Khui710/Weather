package com.pxy.miniweather.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.pxy.miniweather.R;
import com.pxy.miniweather.databinding.ActivityLoginBinding;
import com.pxy.miniweather.db.MyDatabase;
import com.pxy.miniweather.db.User;
import com.pxy.miniweather.db.UserDao;
import com.xuexiang.xui.utils.XToastUtils;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });


        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.name.getText().toString();
                String pwd = binding.pwd.getText().toString();
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(pwd)) {
                    XToastUtils.toast("输入有空");
                    return;
                }

                Observable.create(new ObservableOnSubscribe<Integer>() {
                            @SuppressLint("CheckResult")
                            @Override
                            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Throwable {
                                UserDao userDao = MyDatabase.getInstance(LoginActivity.this).userDao();
                                User user = userDao.getUserByName(name);
                                if (user == null) {//用户不存在
                                    emitter.onNext(1);
                                } else {
                                    if (user.pwd.equals(pwd)) {//密码正确
                                        emitter.onNext(2);
                                    } else {//密码错误
                                        emitter.onNext(3);
                                    }
                                }
                            }
                        }).subscribeOn(Schedulers.io()) //子线程
                        .observeOn(AndroidSchedulers.mainThread()) //切换到主线程
                        .subscribe(new Consumer<Integer>() {
                            @Override
                            public void accept(Integer sign) throws Throwable {
                                switch (sign) {
                                    case 1:
                                        XToastUtils.error("用户不存在");
                                        break;
                                    case 2:
                                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                        finish();
                                        break;
                                    case 3:
                                        XToastUtils.error("密码错误");
                                        break;
                                }
                            }
                        });
            }
        });
    }
}