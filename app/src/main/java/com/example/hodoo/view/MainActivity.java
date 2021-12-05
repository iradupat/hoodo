package com.example.hodoo.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
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
import com.example.hodoo.controller.PostSuggestionInterface;
import com.example.hodoo.controller.StoreUserInterface;
import com.example.hodoo.controller.UserAuthInterface;
import com.example.hodoo.dao.RoomDB;
import com.example.hodoo.model.Post;
import com.example.hodoo.model.User;
import com.example.hodoo.util.UserLocation;

import java.util.List;
import java.util.Locale;


/**
 * Authors : David, Chris, Tyson and Patrick
 *
 */
public class MainActivity extends AppCompatActivity {
    private TextView profileBtn, suggestedBtn, allBtn;
    private Button createPostBtn, messagesBtn, signOutBtn;
    private PostInterface controller;
    private UserAuthInterface userController;
    private PostSuggestionInterface suggestionInterface;
    private StoreUserInterface roomDbStoreUser;
    private RoomDB db;
    private User user;
    private RecyclerView recyclerView;
    private int langCode= 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);
        userController = FactoryController.registerUserController("FIREBASE_DB");
        controller = FactoryController.createPostController("FIREBASE_DB");
        roomDbStoreUser = FactoryController.createStoreUserController("ROOM_DB");
        db = RoomDB.getInstance(this);
        suggestionInterface = FactoryController.createPostSuggestionController("FIREBASE_DB");

        loadUserData();
        setContentView(R.layout.activity_main);



        // create user or load user ID


        // load all the posts

        controller.getAllPosts(new PostListCallBack() {

            @Override
            public void onComplete(List<Post> posts) {
                listPostHandler(posts);
            }
        });


        ImageView flag = (ImageView)findViewById(R.id.home_lang);
        profileBtn = (TextView) findViewById(R.id.home_profile);
        createPostBtn = (Button) findViewById(R.id.home_create_post);
        messagesBtn = (Button) findViewById(R.id.home_messages);
        allBtn = findViewById(R.id.home_all_posts);
        suggestedBtn = findViewById(R.id.home_suggested);


        allBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.getAllPosts(new PostListCallBack() {
                    @Override
                    public void onComplete(List<Post> posts) {
                        listPostHandler(posts);
                    }
                });
            }
        });

        suggestedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                suggestionInterface.suggestedPosts(new PostListCallBack() {
                    @Override
                    public void onComplete(List<Post> posts) {
                      listPostHandler(posts);
                    }
                },user);
            }
        });


        if(user.getLanguage().equals("fr")){
            changeLanguage("fr", flag);
            int imageResource = getResources().getIdentifier("@drawable/france", null, getPackageName());

            Drawable res = getResources().getDrawable(imageResource);
            flag.setImageDrawable(res);
        }else{
            changeLanguage("en", flag);
            int imageResource = getResources().getIdentifier("@drawable/uk", null, getPackageName());

            Drawable res = getResources().getDrawable(imageResource);
            flag.setImageDrawable(res);
        }

        flag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(user.getLanguage().equals("fr")) {

                    changeLanguage("en", flag);
                }else{

                    changeLanguage("fr", flag);

                }
                Intent intent = getIntent();
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                finish();
                startActivity(intent);
            }
        });

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


    public void listPostHandler(List<Post> posts){
        recyclerView = findViewById(R.id.home_list_posts);

        PostAdapter adapter = new PostAdapter(MainActivity.this,posts);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setAdapter(adapter);
    }
    public void changeLanguage(String lng, ImageView flag){

        if(lng.toLowerCase().equals("fr")){
            int imageResource = getResources().getIdentifier("@drawable/france", null, getPackageName());
            user.setLanguage("fr");
            Drawable res = getResources().getDrawable(imageResource);
            flag.setImageDrawable(res);
        }else{
            int imageResource = getResources().getIdentifier("@drawable/uk", null, getPackageName());
            user.setLanguage("en");
            Drawable res = getResources().getDrawable(imageResource);
            flag.setImageDrawable(res);
        }
        roomDbStoreUser.updateUser(db,user);
//        roomDbStoreUser.storeCredentials(user, db);
        switchLan(lng);

    }

    public void switchLan(String lng){
        Resources rs = getResources();
        DisplayMetrics met = rs.getDisplayMetrics();
        Configuration conf = rs.getConfiguration();
        conf.setLocale(new Locale(lng.toLowerCase()));
        rs.updateConfiguration(conf, met);
    }

    public void loadUserData(){
        // check if ther is a user in the local DB
        if(roomDbStoreUser.checkIfUserExist(db)){
            user = roomDbStoreUser.getCredentials(db);
        }else{
            System.out.println(new UserLocation(this).getLocationName());

            // create an account if not

            user = new User(12);
            user.setLanguage("en");
            user = userController.createUser(user);
            roomDbStoreUser.storeCredentials(user, db);
        }
        switchLan(user.getLanguage());
    }


//    public void displayPosts(List<Post> posts){
//
//    }

    public void listPost(Post post, LinearLayout layoutScroll){
        try{
        View layout = getLayoutInflater().inflate(R.layout.dog_card, null, false);
        ImageView img = layout.findViewById(R.id.dog_card_img);
        TextView statusText = layout.findViewById(R.id.connect_connect);
        TextView descriptionText = layout.findViewById(R.id.connect_username);

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
//        ((LinearLayout)findViewById(R.id.home_list_posts)).addView(layout);
            layoutScroll.addView(layout);
        }catch (Exception e){

        }
    }



}