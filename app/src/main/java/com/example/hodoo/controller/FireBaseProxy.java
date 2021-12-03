package com.example.hodoo.controller;

import com.example.hodoo.model.Comment;
import com.example.hodoo.model.Message;
import com.example.hodoo.model.Post;
import com.example.hodoo.model.PostSuggestion;
import com.example.hodoo.model.User;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public abstract class FireBaseProxy {

    private FirebaseAuth firebase;


    public FireBaseProxy(){
        firebase = FirebaseAuth.getInstance();
    }

    public boolean checkIfSuggestionExist(Post post, User suggestedUser){

        return false;
    }
    public void giveSuggestion(PostSuggestion suggestion){

    }
    public List<PostSuggestion> suggestedPosts(User user){
        return null;
    }
    public int getPostSuggestionCount(){
        return  0;
    }

    public List<Comment> getComments(Post post){
        return null;
    }
    public void addComment(Comment comment){

    }
    public int getCommentCount(){
        return 0;
    }

    public int getMessageCount(){
        return 0;
    }
    public void createMessage(Message message){

    }
    public List<Message> getInboxMessages(User sender, User receiver){
        return null;
    }
    public  List<User> getUsers(){

        return null;
    }

    public User createUser(){

        return  null;
    }

    public void updatePost(Post post){

    }

    public Post getPost(String postId) {
        return null;
    }


    public List<Post> getAllPosts() {
        return null;
    }


    public void addPost(Post post) {
//        firebase
//
    }
}
