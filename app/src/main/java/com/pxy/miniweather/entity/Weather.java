package com.pxy.miniweather.entity;

public class Weather {

    private String date;
    private String icon1;
    private String icon2;
    private String weather;
    private String wind;
    private String temp;
    public void setDate(String date) {
        this.date = date;
    }
    public String getDate() {
        return date;
    }

    public void setIcon1(String icon1) {
        this.icon1 = icon1;
    }
    public String getIcon1() {
        return icon1;
    }

    public void setIcon2(String icon2) {
        this.icon2 = icon2;
    }
    public String getIcon2() {
        return icon2;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }
    public String getWeather() {
        return weather;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }
    public String getWind() {
        return wind;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }
    public String getTemp() {
        return temp;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "date='" + date + '\'' +
                ", icon1='" + icon1 + '\'' +
                ", icon2='" + icon2 + '\'' +
                ", weather='" + weather + '\'' +
                ", wind='" + wind + '\'' +
                ", temp='" + temp + '\'' +
                '}';
    }
}