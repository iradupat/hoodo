package com.example.hodoo.controller;

import com.example.hodoo.model.Post;
import com.example.hodoo.model.PostSuggestion;
import com.example.hodoo.model.User;

import java.util.List;

public interface PostSuggestionInterface {

    public void giveSuggestion(PostSuggestion suggestion);
    public List<PostSuggestion> suggestedPosts(User user);
    public int getPostSuggestionCount();
    public boolean checkIfSuggestionExist(Post post, User suggestedUser);
}
