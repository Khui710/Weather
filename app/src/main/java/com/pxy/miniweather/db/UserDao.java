package com.pxy.miniweather.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface UserDao {
    @Insert
    void insertUser(User User);

    @Delete
    void deleteUser(User User);

    @Update
    void updateUser(User User);

    @Query("SELECT * FROM user")
    List<User> getUserList();

    @Query("SELECT * FROM user WHERE id = :id")
    User getUserById(int id);

    @Query("SELECT * FROM user WHERE name = :name")
    User getUserByName(String name);

    @Query("SELECT * FROM user WHERE phone = :phone")
    User getUserByPhone(String phone);
}
