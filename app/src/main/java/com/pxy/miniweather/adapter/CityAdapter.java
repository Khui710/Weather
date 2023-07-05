package com.pxy.miniweather.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.pxy.miniweather.R;
import com.pxy.miniweather.activity.CityManagementActivity;
import com.pxy.miniweather.db.AttentionCity;
import com.pxy.miniweather.db.CityDao;
import com.pxy.miniweather.db.MyDatabase;
import com.pxy.miniweather.entity.CityInfo;
import com.pxy.miniweather.entity.JsonRootBean;
import com.pxy.miniweather.entity.Weather;
import com.pxy.miniweather.http.ApiService;
import com.pxy.miniweather.http.MyRetrofit;
import com.xuexiang.xui.utils.XToastUtils;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.Holder> {
    private List<AttentionCity> cityList = new ArrayList<>();
    private Context context;

    public CityAdapter(Context context) {
        this.context = context;
    }

    public void setDatas(List<AttentionCity> cityList) {
        this.cityList = cityList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CityAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_city, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CityAdapter.Holder holder, @SuppressLint("RecyclerView") int position) {
        AttentionCity city = cityList.get(position);
        holder.cityName.setText(city.getCity());
        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new MaterialDialog.Builder(context)
                        .content("确定要删除此城市信息吗？")
                        .positiveText("确认")
                        .negativeText("取消")
                        .onPositive((dialog, which) -> {
                            // 执行确认操作
                            //先在数据库删除此城市
                            Observable.create(new ObservableOnSubscribe<Boolean>() {
                                        @Override
                                        public void subscribe(@io.reactivex.rxjava3.annotations.NonNull ObservableEmitter<Boolean> emitter) throws Throwable {
                                            CityDao cityDao = MyDatabase.getInstance(context).cityDao();
                                            cityDao.deleteCity(city);
                                        }
                                    }).subscribeOn(Schedulers.io()) //子线程
                                    .observeOn(AndroidSchedulers.mainThread()) //切换到主线程
                                    .subscribe(new Consumer<Boolean>() {
                                        @Override
                                        public void accept(Boolean isExist) throws Throwable {
                                        }
                                    });

                            //再在当前adapter删除此城市
                            cityList.remove(cityList.get(position));
                            //通知列表刷新
                            notifyDataSetChanged();
                        })
                        .onNegative((dialog, which) -> dialog.dismiss())
                        .show();

                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return cityList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView cityName, imgTitle;
        CardView cardView;
        ImageView img;

        public Holder(@NonNull View itemView) {
            super(itemView);
            cityName = itemView.findViewById(R.id.city_name);
            imgTitle = itemView.findViewById(R.id.imgTitle);
            cardView = itemView.findViewById(R.id.cardView);
            img = itemView.findViewById(R.id.img);
        }
    }


}
