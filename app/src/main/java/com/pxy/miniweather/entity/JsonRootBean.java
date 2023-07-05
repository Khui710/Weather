/**
  * Copyright 2023 www.jsonla.com 
  */
package com.pxy.miniweather.entity;

import java.util.List;

/**
 * Auto-generated: 2023-06-14 17:20:9
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class JsonRootBean {

    private String city;
    private String update_time;
    private String date;
    private List<Weather> weather;
    public void setCity(String city) {
        this.city = city;
    }
    public String getCity() {
        return city;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }
    public String getUpdate_time() {
        return update_time;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public String getDate() {
        return date;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }
    public List<Weather> getWeather() {
        return weather;
    }

    @Override
    public String toString() {
        return "JsonRootBean{" +
                "city='" + city + '\'' +
                ", update_time='" + update_time + '\'' +
                ", date='" + date + '\'' +
                ", weather=" + weather +
                '}';
    }
}