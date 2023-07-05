package com.pxy.miniweather.entity;

import java.util.List;

//城市信息
public class CityInfo {
    private String code;
    private List<CityImage> data;
    public void setCode(String code) {
        this.code = code;
    }
    public String getCode() {
        return code;
    }

    public void setData(List<CityImage> data) {
        this.data = data;
    }
    public List<CityImage> getData() {
        return data;
    }

    @Override
    public String toString() {
        return "CityInfo{" +
                "code='" + code + '\'' +
                ", data=" + data +
                '}';
    }
}
