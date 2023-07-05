package com.pxy.miniweather.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pxy.miniweather.R;
import com.pxy.miniweather.entity.Weather;

import java.util.ArrayList;
import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.Holder> {
    private List<Weather> weatherList = new ArrayList<>();
    private Context context;

    public WeatherAdapter(Context context) {
        this.context = context;
    }

    public void setDatas(List<Weather> weatherList) {
        this.weatherList = weatherList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WeatherAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_weather, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherAdapter.Holder holder, int position) {
        Weather weather = weatherList.get(position);
        holder.date.setText(weather.getDate());
        holder.weather.setText(weather.getWeather());
        holder.temp.setText(weather.getTemp());
        holder.wind.setText(weather.getWind());
    }

    @Override
    public int getItemCount() {
        return weatherList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView date, temp, weather, wind;

        public Holder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            temp = itemView.findViewById(R.id.temp);
            weather = itemView.findViewById(R.id.weather);
            wind = itemView.findViewById(R.id.wind);
        }
    }
}
