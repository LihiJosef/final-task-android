package com.example.tastebuds;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import com.example.tastebuds.model.Post;
import com.squareup.picasso.Picasso;
import java.util.List;

// 3
class FeedViewHolder extends GenericViewHolder<Post>{
    ImageView userImage;
    TextView userTv;
    ImageView postImage;
    TextView locationTv;
    TextView reviewTv;
    RatingBar starsRB;

    public FeedViewHolder(@NonNull View itemView) {
        super(itemView);
        userImage = itemView.findViewById(R.id.feedlistrow_user_img);
        userTv = itemView.findViewById(R.id.feedlistrow_user_tv);
        postImage = itemView.findViewById(R.id.feedlistrow_post_img);
        locationTv = itemView.findViewById(R.id.feedlistrow_location_tv);
        reviewTv = itemView.findViewById(R.id.feedlistrow_review_tv);
        starsRB = itemView.findViewById(R.id.feedlistrow_ratingbar);
    }

    @Override
    public void bind(Post post, int pos){
        userTv.setText(post.getUserName());
        locationTv.setText(post.getLocation());
        reviewTv.setText(post.getReview());
        starsRB.setRating((float)post.getStars());

        if(post.getImageUrl().trim().length() != 0) {
            Picasso.get().load(post.getImageUrl()).placeholder(R.drawable.blank_img).into(postImage);
        } else {
            postImage.setImageResource(R.drawable.blank_img);
        }
    }
}

public class FeedRecyclerAdapter extends GenericRecyclerAdapter<Post, FeedViewHolder>{
    public FeedRecyclerAdapter(LayoutInflater inflater, List<Post> data){
        super(inflater, data);
    }

    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 4
        View view = inflater.inflate(R.layout.feed_list_row, parent, false);
        return new FeedViewHolder(view);
    }
}