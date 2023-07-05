package com.pxy.miniweather.db;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "user")
public class User {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id", typeAffinity = ColumnInfo.INTEGER)
    public int id;
    @ColumnInfo(name = "name", typeAffinity = ColumnInfo.TEXT)
    public String name;
    @ColumnInfo(name = "pwd", typeAffinity = ColumnInfo.TEXT)
    public String pwd;
    @ColumnInfo(name = "phone", typeAffinity = ColumnInfo.TEXT)
    public String phone;

    public User() {
    }

    @Ignore
    public User(int id, String name, String pwd, String phone) {
        this.id = id;
        this.name = name;
        this.pwd = pwd;
        this.phone = phone;
    }

    @Ignore
    public User(String name, String pwd, String phone) {
        this.name = name;
        this.pwd = pwd;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getPwd() {
        return pwd;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
