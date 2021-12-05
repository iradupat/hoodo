package com.example.hodoo.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.hodoo.R;
import com.example.hodoo.controller.FactoryController;
import com.example.hodoo.controller.PostCallback;
import com.example.hodoo.controller.PostInterface;
import com.example.hodoo.controller.StoreUserInterface;
import com.example.hodoo.dao.RoomDB;
import com.example.hodoo.model.Post;
import com.example.hodoo.model.PostStatus;
import com.example.hodoo.model.User;

public class PostDetailActivity  extends AppCompatActivity {
    private TextView editBtn, post_detail_connect, post_detail_message_btn;
    private PostInterface controller;
    private User user;
    private RoomDB db;
    private Post thePost;
    private StoreUserInterface roomUserController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_detail_layout);
        editBtn = findViewById(R.id.detail_edit_btn);
        post_detail_connect = findViewById(R.id.post_detail_connect);

        db = RoomDB.getInstance(this);
        roomUserController = FactoryController.createStoreUserController("ROOM_DB");
        user = roomUserController.getCredentials(db);




        post_detail_connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToSuggest = new Intent(PostDetailActivity.this, SuggestActivity.class);

                goToSuggest.putExtra("postId", thePost.getPostId());
                startActivity(goToSuggest);
            }
        });

        controller = FactoryController.createPostController("FIREBASE_DB");
        String postId = getIntent().getExtras().get("postId").toString();

        controller.getPost(new PostCallback() {
            @Override
            public void onComplete(Post post) {
                thePost = post;
                mapPost(post);

            }
        }, postId);

        editBtn = findViewById(R.id.detail_edit_btn);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.getPost(new PostCallback() {
                    @Override
                    public void onComplete(Post post) {
                        thePost = post;
                        if(user.getUserName().equalsIgnoreCase(post.getEditor().getUserName()))
                        {
                            updateStatus(post);
                        }


                    }
                }, postId);

            }
        });

    }

    public void mapPost(Post post){
        try{

            editBtn = findViewById(R.id.detail_edit_btn);
            ImageView img = findViewById(R.id.post_detail_img);
            TextView author = findViewById(R.id.post_detail_author);
            TextView postStatus = findViewById(R.id.post_detail_status);
            TextView postDate = findViewById(R.id.post_detail_date);
            TextView postDescription = findViewById(R.id.post_detail_details_text);
            post_detail_message_btn = findViewById(R.id.post_detail_message);

            author.setText(post.getEditor().getUserName());
            postStatus.setText(post.getStatus().getString());
            postDate.setText(post.getFormattedDate());
            postDescription.setText(post.getDescription().toUpperCase());
            Glide.with(this).load(post.getImage()).into(img);

            String postEditorId = thePost.getEditor().getUserId();
            if(postEditorId.equals(user.getUserId())) {
                Toast.makeText(PostDetailActivity.this,"You are the owner of this post",Toast.LENGTH_SHORT);
                post_detail_message_btn.setVisibility(View.GONE);
            } else {
                post_detail_message_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // send message to a user
                        Intent intent = new Intent(PostDetailActivity.this, MessageActivity.class);
                        intent.putExtra("friendid", postEditorId);
                        PostDetailActivity.this.startActivity(intent);
                    }
                });
            }


            if (post.getStatus().equals(PostStatus.RETURNED))
            {
                editBtn.setText("DOG "+post.getStatus().toString()+"!");
            }

        }catch (Exception e){

        }


    }

    public void updateStatus(Post post){
        try{


            if (!post.getStatus().equals(PostStatus.RETURNED))
            {
                editBtn = findViewById(R.id.detail_edit_btn);
                post.setStatus(PostStatus.RETURNED);
                controller.updatePost(post);
                editBtn.setText(" DOG IS "+post.getStatus().toString()+"!");
            }




        }catch (Exception e){

        }
    }
}
