package com.example.hodoo.controller.room;

import com.example.hodoo.dao.RoomDB;
import com.example.hodoo.model.User;

import java.util.List;

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
        List<User> users = db.userDao().getAll();

        if(users.size()==0){
            return  false;
        }else{
            return true;
        }
    }
}
