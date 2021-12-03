package com.example.hodoo.controller;

import com.example.hodoo.dao.RoomDB;
import com.example.hodoo.model.User;

public abstract class RoomProxy {



    public void storeCredentials(User user, RoomDB db){
        db.userDao().insert(user);
    }
    public User getCredentials(RoomDB db){
        User user = db.userDao().getAll().get(0);
        return user;
    }
}
