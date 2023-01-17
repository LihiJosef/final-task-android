package com.example.tastebuds;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tastebuds.model.Post;
import com.squareup.picasso.Picasso;

import java.util.List;

// 3
class UserPostViewHolder extends RecyclerView.ViewHolder {
    ImageView postImage;
    TextView locationTv;
    TextView starsTv;
    TextView reviewTv;
    List<Post> data;

    public UserPostViewHolder(@NonNull View itemView, UserPostRecyclerAdapter.OnItemClickListener listener, List<Post> data) {
        super(itemView);
        this.data = data;
        postImage = itemView.findViewById(R.id.userpostlistrow_post_img);
        locationTv = itemView.findViewById(R.id.userpostlistrow_location_tv);
        starsTv = itemView.findViewById(R.id.userpostlistrow_stars_tv);
        reviewTv = itemView.findViewById(R.id.userpostlistrow_review_tv);
    }

    public void bind(Post post, int pos) {
        locationTv.setText(post.getLocation());
        starsTv.setText(post.getStars().toString() + "/5");
        reviewTv.setText(post.getReview());

        // todo : get post picture
        if(post.getImageUrl() != "") {
            Picasso.get().load(post.getImageUrl()).placeholder(R.drawable.blank_img).into(postImage);
        } else {
            postImage.setImageResource(R.drawable.blank_img);
        }
    }
}

public class UserPostRecyclerAdapter extends RecyclerView.Adapter<UserPostViewHolder> {
    /*Set listener to catch view row click and handle in the activity ->*/
    OnItemClickListener listener;
    LayoutInflater inflater;
    List<Post> data;

    public static interface OnItemClickListener {
        void onItemClick(int pos);
    }

    public void setData(List<Post> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public UserPostRecyclerAdapter(LayoutInflater inflater, List<Post> data) {
        this.inflater = inflater;
        this.data = data;
    }

    void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public UserPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 4
        View view = inflater.inflate(R.layout.userpost_list_row, parent, false);
        return new UserPostViewHolder(view, listener, data);
    }

    @Override
    public void onBindViewHolder(@NonNull UserPostViewHolder holder, int position) {
        Post post = data.get(position);
        holder.bind(post, position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}