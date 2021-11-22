package com.example.hodoo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CreateEditPostActivity extends AppCompatActivity {
    TextView saveBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_create_post_layout);


        saveBtn = findViewById(R.id.edit_create_save_btn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent postDetailIntent = new Intent(CreateEditPostActivity.this, PostDetailActivity.class);
                startActivity(postDetailIntent);
            }
        });
    }
}
