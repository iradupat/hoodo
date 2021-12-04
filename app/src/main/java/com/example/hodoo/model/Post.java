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
    private String image;
    private String description;
    private Date timestamp;
    private User editor;
    private PostStatus status;
    private String location;

    private boolean allowComments;

    public Post(){}
    public Post(PostBuilder postBuilder){
        postId = postBuilder.getPostId();
        image = postBuilder.getImage();
        description = postBuilder.getDescription();
        timestamp = postBuilder.getTimestamp();
        editor = postBuilder.getEditor();
        status = postBuilder.getStatus();
        location = postBuilder.getLocation();
        allowComments = postBuilder.isAllowComments();
    }


    public void setPostId(String postIdIn){
        postId = postIdIn;
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

    public String getImage() {
        return image;
    }


    public void setImage(String image) {
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

    public String getFormattedDate(){
        SimpleDateFormat DateFor = new SimpleDateFormat("dd/MM/yyyy");
        return  DateFor.format(timestamp);

    }

    public String toString(){
        if(status.equals(PostStatus.SEEN)){
            return description+" : "+location+" - "+getFormattedDate();
        }else{
            return description+" : "+getFormattedDate();

        }
    }
}
