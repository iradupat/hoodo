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
import com.example.hodoo.dao.RoomDB;
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
    List<User> usersList;
    UserAdapter mAdapter;
    private User currentUser;
    private RoomDB roomDB;

    public ChatListFragment() {
        // Required empty public constructor
        roomDB = RoomDB.getInstance(getContext());
        currentUser = FactoryController.createStoreUserController("ROOM_DB").getCredentials(roomDB);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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

        DatabaseReference reference  = FirebaseDatabase.getInstance().getReference().child("Hoodo").child("Users");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usersList.clear();

                for (DataSnapshot ds: snapshot.getChildren()) {

                    User user = ds.getValue(User.class);

                    if (!user.getUserId().equals(currentUser.getUserId())) {


                        usersList.add(user);

                    }


                    mAdapter  = new UserAdapter(getContext(), usersList, false);
                    recyclerView.setAdapter(mAdapter);








                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("From Chat :"+error);
            }
        });






    }
}