package com.example.hodoo.controller.firebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.hodoo.controller.BooleanCallBack;
import com.example.hodoo.controller.MapMessageCallback;
import com.example.hodoo.controller.PostListCallBack;
import com.example.hodoo.controller.PostCallback;
import com.example.hodoo.controller.IntCallback;
import com.example.hodoo.model.Comment;
import com.example.hodoo.model.Message;
import com.example.hodoo.model.Post;
import com.example.hodoo.model.PostSuggestion;
import com.example.hodoo.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class FireBaseProxy {

    private FirebaseDatabase firebase;


    public FireBaseProxy(){

        firebase = FirebaseDatabase.getInstance();
    }

    public void checkIfSuggestionExist(BooleanCallBack callBack, Post post, User suggestedUser){

        DatabaseReference suggestionRef = firebase.getReference().child("Hoodo").child("suggestions");

        suggestionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean exist = false;
                for(DataSnapshot snapshot1: snapshot.getChildren()){
                    PostSuggestion suggestion = snapshot1.getValue(PostSuggestion.class);
                    if(suggestion.getPost().equals(post) && suggestion.getSuggestedUser().equals(suggestedUser)){
                        exist = true;
                    }
                }
                callBack.onComplete(exist);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
    public void giveSuggestion(PostSuggestion suggestion){
        firebase.getReference().child("Hoodo").child("suggestions").push().setValue(suggestion);

    }
    public List<PostSuggestion> suggestedPosts(User user){
        DatabaseReference suggestionRef = firebase.getReference().child("Hoodo").child("suggestions");
        List<PostSuggestion> suggestions = new ArrayList<>();
        suggestionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1: snapshot.getChildren()){
                    PostSuggestion suggestion = snapshot1.getValue(PostSuggestion.class);
                    if(suggestion.getSuggestedUser().equals(user)){
                        suggestions.add(suggestion);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return suggestions;
    }
    public void getPostSuggestionCount(IntCallback callback){
        DatabaseReference suggestionRef = firebase.getReference().child("Hoodo").child("suggestions");

        suggestionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
              int  count = (int)snapshot.getChildrenCount();
              callback.onComplete(count);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    public List<Comment> getComments(Post post){
        List<Comment> comments = new ArrayList<>();
        DatabaseReference commentRef = firebase.getReference().child("Hoodo").child("comments");
        commentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1: snapshot.getChildren()){
                    Comment comment = snapshot1.getValue(Comment.class);
                    if(comment.getPost().equals(post)){
                        comments.add(comment);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return comments;
    }


    public void addComment(Comment comment){
        firebase.getReference().child("Hoodo").child("comments").push().setValue(comment);

    }
    public int getCommentCount(){
        DatabaseReference commentRef = firebase.getReference().child("Hoodo").child("comments");
        final int[] count = {0};
        commentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                count[0] = (int)snapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return count[0];
    }

    public void getMessageCount(IntCallback callback){
        DatabaseReference messageRef = firebase.getReference().child("Hoodo").child("messages");

        messageRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int count = (int)snapshot.getChildrenCount();
                callback.onComplete(count);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void createMessage(Message message){
        firebase.getReference().child("Hoodo").child("messages").push().setValue(message);

    }


    public void getInboxMessages(MapMessageCallback callback, User sender, User receiver){
        DatabaseReference messageRef = firebase.getReference().child("Hoodo").child("messages");

        messageRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Message> messagesSent  = new ArrayList<>();
                List<Message> messagesReceived  = new ArrayList<>();
                Map<String, List<Message>> messageExchanged = new LinkedHashMap<>();
                for(DataSnapshot snapshot1: snapshot.getChildren()){
                    Message message = snapshot1.getValue(Message.class);
                    if(message.getReceiver().equals(receiver) && message.getSender().equals(sender)){
                        messagesSent.add(message);
                    }else if(message.getReceiver().equals(sender) && message.getSender().equals(receiver)){
                        messagesReceived.add(message);
                    }
                }
                messageExchanged.put("Sent", messagesSent);
                messageExchanged.put("Received", messagesReceived);
                callback.onComplete(messageExchanged);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public  List<User> getUsers(){
        DatabaseReference userRef = firebase.getReference().child("Hoodo").child("users");
        List<User> users = new ArrayList<>();
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1 : snapshot.getChildren() ){
                    User user = snapshot1.getValue(User.class);
                    users.add(user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return users;
    }

    public User createUser(){
        User user = new User();
        firebase.getReference().child("Hoodo").child("users").push().setValue(user);

        return  user;
    }

    public void updatePost(Post post){

    }

    public void getPost(PostCallback callBack, String postId) {
        getAllPosts(new PostListCallBack() {
            @Override
            public void onComplete(List<Post> posts) {
                Post post = null;
                for (Post p : posts){
                    if(p.getPostId().equals(postId))
                    {
                        post = p;
                    }
                }
                callBack.onComplete(post);

            }
        });
    }


    public void  getAllPosts(PostListCallBack callBack) {
        firebase = FirebaseDatabase.getInstance();
        List<Post> posts = new ArrayList<>();
        DatabaseReference postsRef = firebase.getReference().child("Hoodo").child("posts");
        postsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1: snapshot.getChildren()) {
//                    System.out.println(snapshot1.getKey());
                    Post post = snapshot1.getValue(Post.class);
                    if(post.getPostId().endsWith("0") || post.getPostId() == null){
                        post.setPostId(snapshot1.getKey());
                    }
                    posts.add(post);
                }
                callBack.onComplete(posts);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void addPost(Post post) {
        firebase.getReference().child("Hoodo").child("posts").push().setValue(post, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                System.out.println("The ref"+ref);
            }
        });

    }
}
