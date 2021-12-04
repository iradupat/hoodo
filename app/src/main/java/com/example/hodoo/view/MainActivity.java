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
import com.example.hodoo.controller.PostInterface;
import com.example.hodoo.controller.PostListCallBack;
import com.example.hodoo.controller.firebase.FireBaseController;
import com.example.hodoo.controller.room.RoomController;
import com.example.hodoo.dao.RoomDB;
import com.example.hodoo.model.Post;
import com.example.hodoo.model.PostStatus;
import com.example.hodoo.model.User;
import com.example.hodoo.util.PostBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView profileBtn;
    private Button createPostBtn, messagesBtn, signOutBtn;
    private PostInterface controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        controller = FactoryController.createPostController("FIREBASE_DB");
//        RoomController roomController = new RoomController();
//        RoomDB db = RoomDB.getInstance(this);

        System.out.println("Sawa here it is"+controller);
        controller.getAllPosts(new PostListCallBack() {
            @Override
            public void onComplete(List<Post> posts) {
                for(Post p : posts){
                    listPost(p);

                }
            }
        });
//        for(int i=0; i<10; i++){
//            Post post = new PostBuilder("https://image.com/img.jpg",PostStatus.SEEN,new User()).buildPost();
//        }





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
                startActivity(postMenuIntent);
            }
        });

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profileViewIntent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(profileViewIntent);
            }
        });
    }


    public void loadUserData(){

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