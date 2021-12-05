package com.example.hodoo.controller;

import com.example.hodoo.model.Post;
import com.example.hodoo.model.PostSuggestion;
import com.example.hodoo.model.User;

import java.util.List;

public interface PostSuggestionInterface {

    public void giveSuggestion(PostSuggestion suggestion);
    public void suggestedPosts(PostListCallBack callBack, User user);
    public void getPostSuggestionCount(IntCallback callback);
    public void checkIfSuggestionExist(BooleanCallBack callBack, Post post, User suggestedUser);
}
