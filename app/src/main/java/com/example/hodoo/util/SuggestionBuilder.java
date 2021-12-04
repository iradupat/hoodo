package com.example.hodoo.util;

import com.example.hodoo.controller.firebase.FireBaseController;
import com.example.hodoo.model.Post;
import com.example.hodoo.model.PostSuggestion;
import com.example.hodoo.model.User;

import java.util.Date;

public class SuggestionBuilder {

    private String suggestionId;
    private Post post;
    private User suggestedUser;
    private User suggestingUser;
    private Date timestamp;
    private FireBaseController fireBaseController;

    public SuggestionBuilder(Post postIn, User suggestedUserIn, User suggestingUserIn){
        timestamp = new Date();
        post = postIn;
        suggestedUser = suggestedUserIn;
        suggestingUser = suggestingUserIn;
        fireBaseController = new FireBaseController();
    }

    public PostSuggestion buildSuggestion(boolean exist){

        if(exist){
            return null;
        }
        PostSuggestion suggestion = new PostSuggestion(this);
        return suggestion;

    }


    public SuggestionBuilder addPostId(int lastCount){
        suggestionId = IDBuilder.createID(lastCount).buildID();
        return this;
    }

    public String getSuggestionId() {
        return suggestionId;
    }

    public Post getPost() {
        return post;
    }

    public User getSuggestedUser() {
        return suggestedUser;
    }

    public User getSuggestingUser() {
        return suggestingUser;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
