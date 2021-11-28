package com.example.hodoo.util;

import com.example.hodoo.model.Comment;
import com.example.hodoo.model.Post;
import com.example.hodoo.model.User;

public class CommentBuilder {
    private String comment;
    private User author;
    private Post post;

    public CommentBuilder(String commentIn, User authorIn, Post postIn){
        comment = commentIn;
        author = authorIn;
        post = postIn;
    }

    public String getComment() {
        return comment;
    }

    public User getAuthor() {
        return author;
    }

    public Post getPost() {
        return post;
    }

    public Comment addComment(Post post, User author){
        if(post.isAllowComments()){
            Comment comment = new Comment(this);
            return comment;
        }
        return null;
    }
}
