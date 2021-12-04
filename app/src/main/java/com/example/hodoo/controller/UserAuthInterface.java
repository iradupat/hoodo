package com.example.hodoo.controller;

import com.example.hodoo.model.User;

import java.util.List;

public interface UserAuthInterface {
    public void createUser(User user);
    public void getUsers(UserListCallback callback);
    public void getUserCount(IntCallback callback);
//    public void getUser(UserCallback callback);


}
