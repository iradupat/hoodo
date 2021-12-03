package com.example.hodoo.util;

import android.net.Uri;

import com.example.hodoo.model.PostStatus;
import com.example.hodoo.model.User;

import java.util.Date;

public class PostBuilder {
    private String postId;
    private Uri image;
    private String description;
    private Date timestamp;
    private User editor;
    private PostStatus status;
    private String location;
    private boolean allowComments;



    public PostBuilder(Uri image, PostStatus status, User editor){
        this.image = image;
        this.status =  status;
        this.editor = editor;
        allowComments = true;
        timestamp = new Date();
        postId = IDBuilder.createID(ModelName.POST).setIDLength(8).buildID();
        if(status.equals(PostStatus.SEEN)){
            addLocation();
        }

    }

    public boolean isAllowComments(){
        return allowComments;
    }
    public PostBuilder allowComments(boolean allow){

        this.allowComments = allow;

        return this;
    }

    public void addLocation(){
         // call a class to locate the user here

    }

    public PostBuilder addDescription(String description){
        this.description = description;
        return this;
    }



    public Date getTimestamp() {
        return timestamp;
    }

    public PostStatus getStatus() {
        return status;
    }

    public String getPostId() {
        return postId;
    }

    public String getDescription() {
        return description;
    }

    public Uri getImage() {
        return image;
    }

    public String getLocation() {
        return location;
    }

    public User getEditor() {
        return editor;
    }
}
