package com.example.hodoo.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hodoo.R;

public class PostMenuActivity extends AppCompatActivity {
    View wanderingBtn, foundBtn, missingBtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_menu_layout);

        Intent editCreateIntent = new Intent(PostMenuActivity.this, CreateEditPostActivity.class);
        editCreateIntent.putExtra("isEdit", false);

        wanderingBtn =  findViewById(R.id.post_menu_wandering_btn);
        foundBtn  =  findViewById(R.id.post_menu_found_btn);
        missingBtn = findViewById(R.id.post_menu_missing_btn);


        wanderingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editCreateIntent.putExtra("action",0);
                startActivity(editCreateIntent);
            }
        });

        foundBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editCreateIntent.putExtra("action",1);

                startActivity(editCreateIntent);
            }
        });

        missingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editCreateIntent.putExtra("action",2);

                startActivity(editCreateIntent);
            }
        });
    }
}
