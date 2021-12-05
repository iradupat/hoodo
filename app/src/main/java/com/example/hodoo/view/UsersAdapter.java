package com.example.hodoo.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hodoo.R;
import com.example.hodoo.controller.BooleanCallBack;
import com.example.hodoo.controller.FactoryController;
import com.example.hodoo.controller.PostSuggestionInterface;
import com.example.hodoo.model.Post;
import com.example.hodoo.model.PostSuggestion;
import com.example.hodoo.model.User;
import com.example.hodoo.util.SuggestionBuilder;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.MyViewHolder> {

    private List<User> users;
    private Context context;
    private Post post;
    private User suggestingUser;
    private PostSuggestionInterface suggestionInterface;

    public UsersAdapter(Context contextIn, List<User> usersIn, Post postIn, User user){
        context = contextIn;
        users =  usersIn;
        suggestingUser = user;
        post = postIn;
        suggestionInterface = FactoryController.createPostSuggestionController("FIREBASE_DB");
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInf = LayoutInflater.from(context);
        View view = layoutInf.inflate(R.layout.user_card, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.userName.setText(users.get(position).getUserName());
        holder.connectBtn.setVisibility(View.INVISIBLE);
        suggestionInterface.checkIfSuggestionExist(new BooleanCallBack() {
            @Override
            public void onComplete(Boolean value) {
                if(!value){
                    holder.connectBtn.setVisibility(View.VISIBLE);
                    holder.connectBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            PostSuggestion suggestion = new SuggestionBuilder(post, users.get(position), suggestingUser).buildSuggestion();
                            suggestionInterface.checkIfSuggestionExist(new BooleanCallBack() {
                                @Override
                                public void onComplete(Boolean value) {

                                }
                            },post,users.get(position));
                            suggestionInterface.giveSuggestion(suggestion);
                            Toast.makeText(context, "Suggestion sent", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        },post,users.get(position));


    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView connectBtn;
        TextView userName;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            connectBtn = itemView.findViewById(R.id.connect_connect);
            userName = itemView.findViewById(R.id.connect_username);
        }
    }
}
