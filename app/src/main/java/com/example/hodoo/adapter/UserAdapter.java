package com.example.hodoo.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.example.hodoo.view.MessageActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyHolder> {

    private Context context;
    private List<User> userlist;
    private boolean isChat;
    private User currentUser;
    private RoomDB roomDB;

    String friendid;
    String thelastmessage;

    public UserAdapter(Context context, List<User> userlist, boolean isChat) {
        this.context = context;
        this.userlist = userlist;
        this.isChat = isChat;
        this.roomDB = RoomDB.getInstance(context);
        this.currentUser = FactoryController.createStoreUserController("ROOM_DB").getCredentials(roomDB);
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.layoutofusers, parent, false);
        return new MyHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        User user = userlist.get(position);

        friendid = user.getUserId();
        holder.username.setText(user.getUserName());
        holder.imageView.setImageResource(R.drawable.user);





//        if (isChat) {
//            holder.image_on.setVisibility(View.VISIBLE);
//        } else {
//            holder.image_on.setVisibility(View.GONE);
//        }
        holder.image_on.setVisibility(View.GONE);
        holder.image_off.setVisibility(View.GONE);

        if (isChat) {

            LastMessage(user.getUserId(), holder.last_msg);

        } else {

            holder.last_msg.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return userlist.size();
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        TextView username, last_msg;
        CircleImageView imageView, image_on, image_off;

        public MyHolder(@NonNull View itemView) {
            super(itemView);


            username = itemView.findViewById(R.id.username_userfrag);
            imageView = itemView.findViewById(R.id.image_user_userfrag);
            image_on = itemView.findViewById(R.id.image_online);
            image_off = itemView.findViewById(R.id.image_offline);
            last_msg = itemView.findViewById(R.id.lastMessage);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            User user = userlist.get(getAdapterPosition());

            friendid = user.getUserId();

            // send message to a user
            Intent intent = new Intent(context, MessageActivity.class);
            intent.putExtra("friendid", friendid);
            context.startActivity(intent);



        }
    }

    private void LastMessage(final String friendid, final TextView last_msg) {

        thelastmessage = "default";

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Chats");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot ds: snapshot.getChildren()) {

                    Chat chat = ds.getValue(Chat.class);

                    if (currentUser!=null &&  chat!=null) {


                        if (chat.getSender().equals(friendid) && chat.getReceiver().equals(currentUser.getUserId()) ||
                                chat.getSender().equals(currentUser.getUserId()) && chat.getReceiver().equals(friendid)) {


                            thelastmessage = chat.getMessage();
                        }


                    }

                }


                switch (thelastmessage) {


                    case "default":
                        last_msg.setText("No message");
                        break;

                    default:
                        last_msg.setText(thelastmessage);

                }


                thelastmessage = "default";


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });










    }
}