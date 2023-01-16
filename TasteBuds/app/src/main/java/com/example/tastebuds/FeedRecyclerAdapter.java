package com.example.tastebuds;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tastebuds.model.Post;

import java.util.List;

// 3
class FeedViewHolder extends RecyclerView.ViewHolder{
    ImageView userImage;
    TextView userTv;
    ImageView postImage;
    TextView locationTv;
    TextView starsTv;
    TextView reviewTv;
    List<Post> data;

    public FeedViewHolder(@NonNull View itemView, FeedRecyclerAdapter.OnItemClickListener listener, List<Post> data) {
        super(itemView);
        this.data = data;
        userImage = itemView.findViewById(R.id.feedlistrow_user_img);
        userTv = itemView.findViewById(R.id.feedlistrow_user_tv);
        postImage = itemView.findViewById(R.id.feedlistrow_post_img);
        locationTv = itemView.findViewById(R.id.feedlistrow_location_tv);
        starsTv = itemView.findViewById(R.id.feedlistrow_stars_tv);
        reviewTv = itemView.findViewById(R.id.feedlistrow_review_tv);
    }

    public void bind(Post post, int pos){
        userTv.setText(post.getUserName());
        locationTv.setText(post.getLocation());
        starsTv.setText(post.getStars().toString() + "/5");
        reviewTv.setText(post.getReview());

//        if(post.getImageUrl() != "") {
//            Picasso.get().load(post.getImageUrl()).placeholder(R.drawable.avatar).into(postImage);
//        } else {
//            postImage.setImageResource(R.drawable.avatar);
//        }
    }
}

public class FeedRecyclerAdapter extends RecyclerView.Adapter<FeedViewHolder>{
    /*Set listener to catch view row click and handle in the activity ->*/
    OnItemClickListener listener;
    LayoutInflater inflater;
    List<Post> data;

    public static interface OnItemClickListener{
        void onItemClick(int pos);
    }

    public void setData(List<Post> data){
        this.data = data;
        notifyDataSetChanged();
    }

    public FeedRecyclerAdapter(LayoutInflater inflater, List<Post> data){
        this.inflater = inflater;
        this.data = data;
    }

    void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 4
        View view = inflater.inflate(R.layout.feed_list_row, parent, false);
        return new FeedViewHolder(view, listener, data);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedViewHolder holder, int position) {
        Post post = data.get(position);
        holder.bind(post, position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}