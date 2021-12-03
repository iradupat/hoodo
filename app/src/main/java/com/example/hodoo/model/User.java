package com.example.hodoo.model;

import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.hodoo.util.IDBuilder;
import com.example.hodoo.util.ModelName;
import com.example.hodoo.util.NameBuilder;

@Entity(tableName = "user")
public class User {
    @PrimaryKey
    private String userId;
    private String userName;


    public User(){
        userId = IDBuilder.createID(ModelName.USER).buildID();
        userName = NameBuilder.createRandomName().allUpperCase().buildName();
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
