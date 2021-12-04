package com.example.hodoo.controller;

import com.example.hodoo.model.User;

import java.util.List;

public interface UserListCallback {
    public void onComplete(List<User> users);

}
