package com.example.hodoo.controller;

import com.example.hodoo.dao.RoomDB;
import com.example.hodoo.model.User;

public interface StoreUserInterface {
    public void storeCredentials(User user, RoomDB db);
    public User getCredentials(RoomDB db);
    public boolean checkIfUserExist(RoomDB db);
}
