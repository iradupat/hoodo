package com.example.hodoo.controller;

import com.example.hodoo.model.Post;

import java.util.List;

public interface PostListCallBack {
    public void onComplete(List<Post> posts);
}
