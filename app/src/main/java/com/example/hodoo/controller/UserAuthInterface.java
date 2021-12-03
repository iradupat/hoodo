package com.example.hodoo.controller;

import com.example.hodoo.model.User;

import java.util.List;

public interface UserAuthInterface {
    public User createUser();
    public List<User> getUsers();
}
