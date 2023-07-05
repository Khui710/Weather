package com.pxy.miniweather.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.pxy.miniweather.R;
import com.pxy.miniweather.databinding.ActivityRegisterBinding;
import com.pxy.miniweather.db.AttentionCity;
import com.pxy.miniweather.db.CityDao;
import com.pxy.miniweather.db.MyDatabase;
import com.pxy.miniweather.db.User;
import com.pxy.miniweather.db.UserDao;
import com.xuexiang.xui.utils.XToastUtils;
import com.xuexiang.xui.widget.actionbar.TitleBar;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.title.setLeftClickListener(view -> onBackPressed()).setCenterClickListener(v -> XToastUtils.toast("注册"));
        //注册按钮
        binding.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.name.getText().toString();
                String pwd = binding.pwd.getText().toString();
                String phone = binding.phone.getText().toString();
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(pwd) || TextUtils.isEmpty(phone)) {
                    XToastUtils.toast("输入有空");
                    return;
                }
                Observable.create(new ObservableOnSubscribe<Integer>() {
                            @Override
                            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Throwable {
                                UserDao userDao = MyDatabase.getInstance(RegisterActivity.this).userDao();
                                User user = userDao.getUserByName(name);
                                if (user == null) {//用户名查询没有该用户
                                    user = userDao.getUserByPhone(phone);
                                    if (user == null) {//手机号查询没有该用户
                                        userDao.insertUser(new User(name, pwd, phone));
                                        emitter.onNext(1);
                                    } else {//手机号存在
                                        emitter.onNext(2);
                                    }
                                } else {//该用户名存在
                                    emitter.onNext(3);
                                }
                            }
                        }).subscribeOn(Schedulers.io()) //子线程
                        .observeOn(AndroidSchedulers.mainThread()) //切换到主线程
                        .subscribe(new Consumer<Integer>() {
                            @Override
                            public void accept(Integer sign) throws Throwable {
                                switch (sign) {
                                    case 1:
                                        XToastUtils.success("注册成功");
                                        onBackPressed();
                                        break;
                                    case 2:
                                        XToastUtils.error("该手机号已注册");
                                        break;
                                    case 3:
                                        XToastUtils.error("该用户名已注册");
                                        break;
                                }
                            }
                        });
            }
        });
    }
}