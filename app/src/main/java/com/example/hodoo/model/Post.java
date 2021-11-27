package com.example.hodoo.model;

import android.net.Uri;

import com.example.hodoo.util.PostBuilder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Post {
    private String postId;
    private Uri image;
    private String description;
    private Date timestamp;
    private User editor;
    private PostStatus status;
    private String location;

    private boolean allowComments;


    private Post(PostBuilder postBuilder){

    }


    public boolean isAllowComments() {
        return allowComments;
    }

    public void setAllowComments(boolean allowComments) {
        this.allowComments = allowComments;
    }

    public String getPostId(){
        return  postId;
    }

    public Date getTimestamp(){
        return  timestamp;
    }

    public Uri getImage() {
        return image;
    }


    public void setImage(Uri image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getEditor() {
        return editor;
    }

    public void setEditor(User editor) {
        this.editor = editor;
    }

    public PostStatus getStatus() {
        return status;
    }

    public void setStatus(PostStatus status) {
        this.status = status;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
