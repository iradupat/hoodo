package com.example.hodoo.model;

import com.example.hodoo.util.SuggestionBuilder;

import java.util.Date;

public class PostSuggestion {
    private String suggestionId;
    private Post post;
    private User suggestedUser;
    private User suggestingUser;
    private Date timestamp;


    public  PostSuggestion(SuggestionBuilder builder){
        suggestionId = builder.getSuggestionId();
        post = builder.getPost();
        suggestedUser = builder.getSuggestedUser();
        suggestingUser = builder.getSuggestingUser();
        timestamp = builder.getTimestamp();
    }

    public String getSuggestionId(){
        return suggestionId;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getSuggestedUser() {
        return suggestedUser;
    }

    public void setSuggestedUser(User suggestedUser) {
        this.suggestedUser = suggestedUser;
    }

    public User getSuggestingUser() {
        return suggestingUser;
    }

    public void setSuggestingUser(User suggestingUser) {
        this.suggestingUser = suggestingUser;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String toString(){
        return suggestionId+" - "+post.getPostId();
    }
}
