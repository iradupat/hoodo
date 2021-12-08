package com.example.hodoo.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hodoo.R;
import com.example.hodoo.model.Post;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.RecycleHolder> {
    private List<Post> postList;
    private Context context;
    private String lng;
    public PostAdapter(Context contextIn, List<Post> posts, String lngIn) {
        postList = posts;
        context =  contextIn;
        lng = lngIn;
    }

    @NonNull
    @Override
    public PostAdapter.RecycleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInf = LayoutInflater.from(context);
        View view = layoutInf.inflate(R.layout.dog_card, parent, false);
        return new RecycleHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.RecycleHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.descriptionText.setText(postList.get(position).toString());
        holder.statusText.setText(postList.get(position).getStatus().getTranslatedText(lng));
        Glide.with(context).load(postList.get(position).getImage()).into(holder.img);


        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent postDetailsIntent = new Intent(context, PostDetailActivity.class);
                postDetailsIntent.putExtra("postId", postList.get(position).getPostId());
                context.startActivity(postDetailsIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public class RecycleHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView statusText, descriptionText;

        public RecycleHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.dog_card_img);
            statusText = itemView.findViewById(R.id.connect_connect);
            descriptionText = itemView.findViewById(R.id.connect_username);
        }
    }
}
