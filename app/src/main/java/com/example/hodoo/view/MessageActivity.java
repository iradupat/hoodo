package com.example.hodoo.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hodoo.R;
import com.example.hodoo.adapter.MessageAdapter;
import com.example.hodoo.controller.FactoryController;
import com.example.hodoo.dao.RoomDB;
import com.example.hodoo.model.Chat;
import com.example.hodoo.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class MessageActivity extends AppCompatActivity {

    String friendid, message, myid;
    CircleImageView imageViewOnToolbar;
    TextView usernameonToolbar;
    Toolbar toolbar;

    EditText et_message;
    Button send;

    DatabaseReference reference;

    List<Chat> chatsList;
    MessageAdapter messageAdapter;
    RecyclerView recyclerView;
    private RoomDB roomDB;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        roomDB = RoomDB.getInstance(MessageActivity.this);
        currentUser = FactoryController.createStoreUserController("ROOM_DB").getCredentials(roomDB);
        setContentView(R.layout.activity_message);

        toolbar = findViewById(R.id.toolbar_message);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imageViewOnToolbar = findViewById(R.id.profile_image_toolbar_message);
        usernameonToolbar = findViewById(R.id.username_ontoolbar_message);

        recyclerView = findViewById(R.id.recyclerview_messages);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        send = findViewById(R.id.send_messsage_btn);
        et_message = findViewById(R.id.edit_message_text);
        myid = currentUser.getUserId();

        friendid = getIntent().getStringExtra("friendid"); // retreive the friendid when we click on the item

System.out.println("*****Friend id "+friendid);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Hoodo").child("users_two").child(friendid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                User user = snapshot.getValue(User.class);

                usernameonToolbar.setText(user.getUserName()); // set the text of the user on textivew in toolbar

                imageViewOnToolbar.setImageResource(R.drawable.user);

                readMessages(myid, friendid);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






        et_message.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                if (s.toString().length() > 0) {

                    send.setEnabled(true);

                } else {

                    send.setEnabled(false);


                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String text = et_message.getText().toString();

                if (!text.startsWith(" ")) {
                    et_message.getText().insert(0, " ");

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });




        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                message = et_message.getText().toString();

                sendMessage(myid, friendid, message);

                et_message.setText(" ");


            }
        });




    }



    private void readMessages(final String myid, final String friendid) {

        chatsList = new ArrayList<>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Chats");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatsList.clear();

                for (DataSnapshot ds: snapshot.getChildren()) {

                    Chat chat = ds.getValue(Chat.class);

                    if (chat.getSender().equals(myid) && chat.getReceiver().equals(friendid) ||
                            chat.getSender().equals(friendid) && chat.getReceiver().equals(myid)) {

                        chatsList.add(chat);
                    }

                    messageAdapter = new MessageAdapter(MessageActivity.this, chatsList);
                    recyclerView.setAdapter(messageAdapter);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void sendMessage(final String myid, final String friendid, final String message) {


        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();



        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", myid);
        hashMap.put("receiver", friendid);
        hashMap.put("message", message);

        reference.child("Chats").push().setValue(hashMap);


        final DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Chatlist").child(myid).child(friendid);

        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                if (!snapshot.exists()) {


                    reference1.child("id").setValue(friendid);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


}