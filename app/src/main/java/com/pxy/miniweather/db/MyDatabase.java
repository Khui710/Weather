package com.pxy.miniweather.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.xuexiang.citypicker.model.City;

@Database(entities = {AttentionCity.class,User.class}, version = 1,exportSchema = false)
public abstract class MyDatabase extends RoomDatabase
{
    private static final String DATABASE_NAME = "my_db";

    private static MyDatabase databaseInstance;

    public static synchronized MyDatabase getInstance(Context context)
    {
        if(databaseInstance == null)
        {
            databaseInstance = Room
                    .databaseBuilder(context.getApplicationContext(), MyDatabase.class, DATABASE_NAME)
                    .build();
        }
        return databaseInstance;
    }

    public abstract UserDao userDao();
    public abstract CityDao cityDao();
}