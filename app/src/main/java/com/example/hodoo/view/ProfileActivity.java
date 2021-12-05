package com.example.hodoo.view;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hodoo.R;
import com.example.hodoo.controller.FactoryController;
import com.example.hodoo.controller.StoreUserInterface;
import com.example.hodoo.controller.UserAuthInterface;
import com.example.hodoo.dao.RoomDB;
import com.example.hodoo.model.User;

public class ProfileActivity extends AppCompatActivity {
    private TextView save, profileName;
    private EditText editName;
    private UserAuthInterface userController;
    private StoreUserInterface roomDbStoreUser;
    private RoomDB db;
    private User user;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_layout);

        String userId = getIntent().getExtras().get("userId").toString();
        save = findViewById(R.id.profile_save);
        editName = findViewById(R.id.profile_edit_name);
        profileName = findViewById(R.id.profile_profile_name);

        roomDbStoreUser = FactoryController.createStoreUserController("ROOM_DB");
        db = RoomDB.getInstance(this);

        user = roomDbStoreUser.getCredentials(db);

        profileName.setText(user.getUserName());

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editName.getText().toString().equals("")){
                    Toast.makeText(ProfileActivity.this, "Enter a name in the box", Toast.LENGTH_LONG).show();
                }else{
                    user.setUserName(editName.getText().toString());
                    profileName.setText(user.getUserName());
                    roomDbStoreUser.updateUser(db,user);
                }
            }
        });

    }
}
