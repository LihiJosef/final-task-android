package com.example.tastebuds;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.example.tastebuds.model.Post;
import com.squareup.picasso.Picasso;
import java.util.List;

// 3
class UserPostViewHolder extends GenericViewHolder<Post> {
    ImageView postImage;
    TextView locationTv;
    TextView starsTv;
    TextView reviewTv;

    public UserPostViewHolder(@NonNull View itemView, UserPostRecyclerAdapter.OnItemClickListener listener) {
        super(itemView);
        postImage = itemView.findViewById(R.id.userpostlistrow_post_img);
        locationTv = itemView.findViewById(R.id.userpostlistrow_location_tv);
        starsTv = itemView.findViewById(R.id.userpostlistrow_stars_tv);
        reviewTv = itemView.findViewById(R.id.userpostlistrow_review_tv);

        itemView.setOnClickListener(view -> {
            int pos = getAdapterPosition();
            Log.d("TAG", "row click " + pos);
            listener.onItemClick(pos);
        });
    }

    public void bind(Post post, int pos) {
        locationTv.setText(post.getLocation());
        starsTv.setText(post.getStars().toString() + "/5");
        reviewTv.setText(post.getReview());

        if(post.getImageUrl().trim().length() != 0) {
            Picasso.get().load(post.getImageUrl()).placeholder(R.drawable.blank_img).into(postImage);
        } else {
            postImage.setImageResource(R.drawable.blank_img);
        }
    }
}

public class UserPostRecyclerAdapter extends GenericRecyclerAdapter<Post, UserPostViewHolder> {
    public UserPostRecyclerAdapter(LayoutInflater inflater, List<Post> data) {
        super(inflater, data);
    }

    /*Set listener to catch view row click and handle in the fragment ->*/
    OnItemClickListener listener;

    public static interface OnItemClickListener {
        void onItemClick(int pos);
    }

    void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public UserPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 4
        View view = inflater.inflate(R.layout.userpost_list_row, parent, false);
        return new UserPostViewHolder(view, listener);
    }
}