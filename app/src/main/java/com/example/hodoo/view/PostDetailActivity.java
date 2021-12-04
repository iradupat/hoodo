package com.example.hodoo.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.hodoo.R;
import com.example.hodoo.controller.FactoryController;
import com.example.hodoo.controller.PostCallback;
import com.example.hodoo.controller.PostInterface;
import com.example.hodoo.model.Post;
import com.example.hodoo.model.PostStatus;
import com.example.hodoo.util.UserLocation;

public class PostDetailActivity  extends AppCompatActivity {
    private TextView editBtn;
    private PostInterface controller;
    private Post thePost;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_detail_layout);
        editBtn = findViewById(R.id.detail_edit_btn);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoEditPostIntent = new Intent(PostDetailActivity.this,CreateEditPostActivity.class);
                gotoEditPostIntent.putExtra("isEdit", true);
                gotoEditPostIntent.putExtra("postId", thePost.getPostId());
                if(thePost.getStatus().equals(PostStatus.SEEN)){
                        gotoEditPostIntent.putExtra("action",0);
                }else if(thePost.getStatus().equals(PostStatus.FOUND)){
                    gotoEditPostIntent.putExtra("action",1);

                }else if(thePost.getStatus().equals(PostStatus.LOST)){
                    gotoEditPostIntent.putExtra("action",2);

                }else{

                }
//                startActivity(gotoEditPostIntent);
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

    }

    public void mapPost(Post post){
        try{

            ImageView img = findViewById(R.id.post_detail_img);
            TextView author = findViewById(R.id.post_detail_author);
            TextView postStatus = findViewById(R.id.post_detail_status);
            TextView postDate = findViewById(R.id.post_detail_date);
            TextView postDescription = findViewById(R.id.post_detail_details_text);

            author.setText(post.getEditor().getUserName());
            postStatus.setText(post.getStatus().getString());
            postDate.setText(post.getFormattedDate());
            postDescription.setText(post.getDescription().toUpperCase());
            Glide.with(this).load(post.getImage()).into(img);

        }catch (Exception e){

        }

    }
}
