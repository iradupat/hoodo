package com.example.hodoo.controller;

import com.example.hodoo.model.Post;

import java.util.List;

public interface PostInterface {
    public Post getPost(String postId);
    public List<Post> getAllPosts();
    public void addPost(Post post);
    public void updatePost(Post post);

}
