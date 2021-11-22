package com.example.hodoo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PostMenuActivity extends AppCompatActivity {
    View wanderingBtn, foundBtn, missingBtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_menu_layout);

        Intent editCreateIntent = new Intent(PostMenuActivity.this, CreateEditPostActivity.class);


        wanderingBtn =  findViewById(R.id.post_menu_wandering_btn);
        foundBtn  =  findViewById(R.id.post_menu_found_btn);
        missingBtn = findViewById(R.id.post_menu_missing_btn);


        wanderingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(editCreateIntent);
            }
        });

        foundBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(editCreateIntent);
            }
        });

        missingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(editCreateIntent);
            }
        });
    }
}
