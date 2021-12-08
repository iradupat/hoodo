package com.example.hodoo.view;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hodoo.R;
import com.example.hodoo.controller.FactoryController;
import com.example.hodoo.controller.PostCallback;
import com.example.hodoo.controller.PostInterface;
import com.example.hodoo.controller.PostSuggestionInterface;
import com.example.hodoo.controller.StoreUserInterface;
import com.example.hodoo.controller.UserAuthInterface;
import com.example.hodoo.controller.UserListCallback;
import com.example.hodoo.dao.RoomDB;
import com.example.hodoo.model.Post;
import com.example.hodoo.model.PostSuggestion;
import com.example.hodoo.model.User;
import com.example.hodoo.util.SuggestionBuilder;

import java.util.List;
import java.util.stream.Collectors;

public class SuggestActivity extends AppCompatActivity {
    private StoreUserInterface roomUserController;
    private RoomDB db;
    private UserAuthInterface userAuthInterface;
    private PostInterface postInterface;
    private PostSuggestionInterface suggestionInterface;
    private TextView userName, connectBtn;
    private Post thePost;
    private User suggestedUser, suggestingUser;
    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suggestion_layout);

        context = getApplicationContext();
        postInterface = FactoryController.createPostController("FIREBASE_DB");
        suggestionInterface = FactoryController.createPostSuggestionController("FIREBASE_DB");
        userAuthInterface = FactoryController.registerUserController("FIREBASE_DB");
        roomUserController = FactoryController.createStoreUserController("ROOM_DB");
        db = RoomDB.getInstance(this);


        suggestingUser = roomUserController.getCredentials(db);
        String postId = getIntent().getExtras().get("postId").toString();
        postInterface.getPost(new PostCallback() {
            @Override
            public void onComplete(Post post) {
                thePost = post;
            }
        }, postId);
        listUsers();

    }

    public void listUsers(){
        userAuthInterface.getUsers(new UserListCallback() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onComplete(List<User> users) {


                RecyclerView recyclerView = findViewById(R.id.connect_recycle);
                users = users.stream().filter(user -> !user.getUserId().equals(thePost.getEditor().getUserId())).collect(Collectors.toList());
                UsersAdapter adapter = new UsersAdapter(SuggestActivity.this,users,thePost,suggestingUser);
                recyclerView.setLayoutManager(new LinearLayoutManager(SuggestActivity.this));
                recyclerView.setAdapter(adapter);

            }
        });
    }

}
