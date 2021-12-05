package com.example.hodoo.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hodoo.R;
import com.example.hodoo.adapter.UserAdapter;
import com.example.hodoo.controller.FactoryController;
import com.example.hodoo.controller.UserAuthInterface;
import com.example.hodoo.dao.RoomDB;
import com.example.hodoo.model.Chatlist;
import com.example.hodoo.model.User;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ChatListFragment extends Fragment {

    RecyclerView recyclerView;
    List<User> users;
    List<Chatlist> usersList;
    UserAdapter mAdapter;
    private User currentUser;
    private RoomDB roomDB;
    private UserAuthInterface userController;

    public ChatListFragment() {
        // Required empty public constructor

        userController = FactoryController.registerUserController("FIREBASE_DB");
        roomDB = RoomDB.getInstance(getContext());
        currentUser = FactoryController.createStoreUserController("ROOM_DB").getCredentials(roomDB);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        usersList = new ArrayList<>();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat_list, container, false);
        recyclerView = view.findViewById(R.id.chat_recyclerview_chatfrag);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        displayUsers();

        return view;
    }

    private void displayUsers() {

        usersList = new ArrayList<>();


        DatabaseReference reference  = FirebaseDatabase.getInstance().getReference("Chatlist").child(currentUser.getUserId());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                usersList.clear();
                for (DataSnapshot ds: snapshot.getChildren()) {
                    Chatlist chatlist = ds.getValue(Chatlist.class);

                    usersList.add(chatlist);

                }

                ChatsListings();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






    }

    private void ChatsListings() {

        users = new ArrayList<>();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Hoodo").child("users_two");


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                users.clear();

                for (DataSnapshot ds: snapshot.getChildren()) {

                    User user = ds.getValue(User.class);

                    for (Chatlist chatlist: usersList) {


                        if (user.getUserId().equals(chatlist.getId())) {


                            users.add(user);

                        }




                    }


                }

                mAdapter = new UserAdapter(getContext(), users, true);
                recyclerView.setAdapter(mAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}