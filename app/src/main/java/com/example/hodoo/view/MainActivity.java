package com.example.hodoo.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

<<<<<<< HEAD:app/src/main/java/com/example/hodoo/view/MainActivity.java
import com.example.hodoo.R;
=======
import com.google.firebase.auth.FirebaseAuth;
>>>>>>> efb86a3270f014542d493ae1d915fc5fea4b1bba:app/src/main/java/com/example/hodoo/MainActivity.java

public class MainActivity extends AppCompatActivity {
    private TextView profileBtn;
    private Button createPostBtn, messagesBtn, signOutBtn;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();

        signOutBtn = (Button) findViewById(R.id.btn_signOut);

        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
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

<<<<<<< HEAD:app/src/main/java/com/example/hodoo/view/MainActivity.java
    public void showPost(){
            System.out.println("Was clicked");
=======
    private void signOut() {
        firebaseAuth.signOut();
        finish();
>>>>>>> efb86a3270f014542d493ae1d915fc5fea4b1bba:app/src/main/java/com/example/hodoo/MainActivity.java
    }
}