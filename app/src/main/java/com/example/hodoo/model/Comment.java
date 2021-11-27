package com.example.hodoo.model;

import java.util.Date;

public class Comment {

    private String commentId;
    private String comment;
    private Date timestamp;
    private User author;
    private Post post;

    public Comment(String comment, User author, Post post) {
        this.comment = comment;
        this.author = author;
        this.timestamp = new Date();
        this.post = post;
    }

    public void setPost(Post post){
        this.post = post;
    }

    public Post getPost(){
        return post;
    }
    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
