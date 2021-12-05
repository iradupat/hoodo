package com.example.hodoo.model;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.hodoo.util.IDBuilder;
import com.example.hodoo.util.ModelName;
import com.example.hodoo.util.NameBuilder;

@Entity(tableName = "user")
public class User {
    @PrimaryKey
    @NonNull
    private String userId;
    private String userName;
    private String language;
    private String token;



    public User(){}
    public User(int lastCount){
        userId = IDBuilder.createID(lastCount).buildID();
        userName = NameBuilder.createRandomName().allUpperCase().buildName();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void  setLanguage(String lang){
        language = lang;
    }

    public String getLanguage(){
        return language;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId){
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String toString(){
        return this.userName;
    }
}
