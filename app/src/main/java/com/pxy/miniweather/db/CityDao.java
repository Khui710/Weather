package com.pxy.miniweather.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.xuexiang.citypicker.model.City;

import java.util.List;

@Dao
public interface CityDao {
    @Insert
    void insertCity(AttentionCity City);

    @Delete
    void deleteCity(AttentionCity City);

    @Update
    void updateCity(AttentionCity City);

    @Query("SELECT * FROM city")
    List<AttentionCity> getCityList();

    @Query("SELECT * FROM city WHERE id = :id")
    AttentionCity getCityById(int id);

    @Query("SELECT * FROM city WHERE city = :city")
    AttentionCity getCityByName(String city);

    @Query("SELECT COUNT(*) FROM city;")
    Integer getCityCount();
}
