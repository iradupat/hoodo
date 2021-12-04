package com.example.hodoo.controller.room;

import com.example.hodoo.dao.RoomDB;
import com.example.hodoo.model.User;

public abstract class RoomProxy {



    public void storeCredentials(User user, RoomDB db){
        db.userDao().deleteAll();
        db.userDao().insert(user);
    }
    public User getCredentials(RoomDB db){
        User user = db.userDao().getAll().get(0);
        return user;
    }
    public boolean checkIfUserExist(RoomDB db){
        User user = db.userDao().getAll().get(0);
        if(user == null){
            return  false;
        }else{
            return true;
        }
    }
}
