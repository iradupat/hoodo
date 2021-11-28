package com.example.hodoo.controller;

import com.example.hodoo.model.Comment;
import com.example.hodoo.model.Post;

import java.util.List;

public interface CommentInterface {
    public List<Comment> getComments(Post post);
    public void addComment(Comment comment);
    public int getCommentCount();
}
