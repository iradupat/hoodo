package com.example.hodoo.controller;

import com.example.hodoo.model.Post;

public interface PostInterface {
    public void  getAllPosts(PostListCallBack callBack);
    public void getPost(PostCallback callBack, String postId);
    public void addPost(Post post);
    public void updatePost(Post post);

}
