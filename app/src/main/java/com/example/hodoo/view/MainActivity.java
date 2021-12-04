package com.example.hodoo.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.example.hodoo.R;
import com.example.hodoo.controller.FactoryController;
import com.example.hodoo.controller.IntCallback;
import com.example.hodoo.controller.PostInterface;
import com.example.hodoo.controller.PostListCallBack;
import com.example.hodoo.controller.StoreUserInterface;
import com.example.hodoo.controller.UserAuthInterface;
import com.example.hodoo.controller.firebase.FireBaseController;
import com.example.hodoo.controller.room.RoomController;
import com.example.hodoo.dao.RoomDB;
import com.example.hodoo.model.Post;
import com.example.hodoo.model.PostStatus;
import com.example.hodoo.model.User;
import com.example.hodoo.util.PostBuilder;
import com.example.hodoo.util.UserLocation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


/**
 * Authors : David, Chris, Tyson and Patrick
 *
 */
public class MainActivity extends AppCompatActivity {
    private TextView profileBtn;
    private Button createPostBtn, messagesBtn, signOutBtn;
    private PostInterface controller;
    private UserAuthInterface userController;
    private StoreUserInterface roomDbStoreUser;
    private RoomDB db;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userController = FactoryController.registerUserController("FIREBASE_DB");
        controller = FactoryController.createPostController("FIREBASE_DB");
        roomDbStoreUser = FactoryController.createStoreUserController("ROOM_DB");
        db = RoomDB.getInstance(this);



        // create user or load user ID
        loadUserData();

        // load all the posts
        controller.getAllPosts(new PostListCallBack() {
            @Override
            public void onComplete(List<Post> posts) {
                for(Post p : posts){
                    listPost(p);

                }
            }
        });


        profileBtn = (TextView) findViewById(R.id.home_profile);
        createPostBtn = (Button) findViewById(R.id.home_create_post);
        messagesBtn = (Button) findViewById(R.id.home_messages);


        messagesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent contactsList = new Intent(MainActivity.this, ContactListActivity.class);
                startActivity(contactsList);
            }
        });
        createPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent postMenuIntent = new Intent(MainActivity.this, PostMenuActivity.class);
                postMenuIntent.putExtra("userId", user.getUserId());
                startActivity(postMenuIntent);

            }
        });

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profileViewIntent = new Intent(MainActivity.this, ProfileActivity.class);
                profileViewIntent.putExtra("userId", user.getUserId());
                startActivity(profileViewIntent);
            }
        });
    }


    public void loadUserData(){
        // check if ther is a user in the local DB
        if(roomDbStoreUser.checkIfUserExist(db) == true){
            user = roomDbStoreUser.getCredentials(db);
        }else{
            // create an account if not
            userController.getUserCount(new IntCallback() {
                @Override
                public void onComplete(int value) {
                    user = new User(value);
                    userController.createUser(user);
                    roomDbStoreUser.storeCredentials(user, db);
                }
            });
        }
    }

    public void listPost(Post post){
        try{
        View layout = getLayoutInflater().inflate(R.layout.dog_card, null, false);
        ImageView img = layout.findViewById(R.id.dog_card_img);
        TextView statusText = layout.findViewById(R.id.dog_card_status);
        TextView descriptionText = layout.findViewById(R.id.dog_card_desc);

        descriptionText.setText(post.toString());
        statusText.setText(post.getStatus().getString());
        Glide.with(this).load(post.getImage()).into(img);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent postDetailsIntent = new Intent(MainActivity.this, PostDetailActivity.class);
                postDetailsIntent.putExtra("postId", post.getPostId());
                startActivity(postDetailsIntent);
            }
        });
        ((LinearLayout)findViewById(R.id.home_list_posts)).addView(layout);

        }catch (Exception e){

        }
    }



}