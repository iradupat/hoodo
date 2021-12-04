package com.example.hodoo.view;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hodoo.R;
import com.example.hodoo.controller.FactoryController;
import com.example.hodoo.controller.StoreUserInterface;
import com.example.hodoo.controller.UserAuthInterface;
import com.example.hodoo.dao.RoomDB;
import com.example.hodoo.model.User;

public class ProfileActivity extends AppCompatActivity {

    private UserAuthInterface userController;
    private StoreUserInterface roomDbStoreUser;
    private RoomDB db;
    private User user;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_layout);

        String userId = getIntent().getExtras().get("userId").toString();

        roomDbStoreUser = FactoryController.createStoreUserController("ROOM_DB");
        db = RoomDB.getInstance(this);



    }
}
