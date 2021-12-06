package com.example.hodoo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hodoo.R;
import com.example.hodoo.controller.FactoryController;
import com.example.hodoo.dao.RoomDB;
import com.example.hodoo.model.Chat;
import com.example.hodoo.model.User;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {
    Context context;
    List<Chat> chatlist;
    String imageURL;

    private final RoomDB roomDB;
    private final User user;

    public static final int MESSAGE_RIGHT = 0;
    public static final int MESSAGE_LEFT = 1;



    public MessageAdapter(Context context, List<Chat> chatlist) {
        this.context = context;
        this.chatlist = chatlist;
        this.imageURL = imageURL;

        roomDB = RoomDB.getInstance(context);
        user = FactoryController.createStoreUserController("ROOM_DB").getCredentials(roomDB);

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == MESSAGE_RIGHT) {

            View view = LayoutInflater.from(context).inflate(R.layout.chat_item_right, parent, false);
            return new MyViewHolder(view);


        } else {

            View view = LayoutInflater.from(context).inflate(R.layout.chat_item_left, parent, false);
            return new MyViewHolder(view);

        }



    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Chat chat  = chatlist.get(position);

        holder.messagetext.setText(chat.getMessage());

    }

    @Override
    public int getItemCount() {
        return chatlist.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView messagetext;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            messagetext = itemView.findViewById(R.id.show_message);
        }
    }


    @Override
    public int getItemViewType(int position) {

        assert user != null;
        if (chatlist.get(position).getSender().equals(user.getUserId())) {


            return MESSAGE_RIGHT;
        } else {

            return MESSAGE_LEFT;


        }
    }
}

