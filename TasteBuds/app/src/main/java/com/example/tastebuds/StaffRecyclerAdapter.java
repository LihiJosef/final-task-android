package com.example.tastebuds;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.example.tastebuds.model.StaffReview;
import com.squareup.picasso.Picasso;

import java.util.List;

// 3
class StaffViewHolder extends GenericViewHolder<StaffReview>{
    ImageView userImage;
    TextView userTv;
    ImageView postImage;
    TextView locationTv;
    TextView reviewTv;
    RatingBar starsRB;

    public StaffViewHolder(@NonNull View itemView) {
        super(itemView);
        userImage = itemView.findViewById(R.id.feedlistrow_user_img);
        userTv = itemView.findViewById(R.id.feedlistrow_user_tv);
        postImage = itemView.findViewById(R.id.feedlistrow_post_img);
        locationTv = itemView.findViewById(R.id.feedlistrow_location_tv);
        reviewTv = itemView.findViewById(R.id.feedlistrow_review_tv);
        starsRB = itemView.findViewById(R.id.feedlistrow_ratingbar);
    }

    @Override
    public void bind(StaffReview staffReview, int pos){

        userTv.setText(staffReview.getName());
        locationTv.setText(staffReview.getCountry());
        reviewTv.setText(staffReview.getDsc());
        starsRB.setRating((float)staffReview.getRate());

        if(staffReview.getImg().trim().length() != 0) {
            Picasso.get().load(staffReview.getImg()).placeholder(R.drawable.blank_img).into(postImage);
        } else {
            postImage.setImageResource(R.drawable.blank_img);
        }
    }
}

public class StaffRecyclerAdapter extends GenericRecyclerAdapter<StaffReview, StaffViewHolder>{
    public StaffRecyclerAdapter(LayoutInflater inflater, List<StaffReview> data){
        super(inflater, data);
    }

    @NonNull
    @Override
    public StaffViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 4
        View view = inflater.inflate(R.layout.feed_list_row, parent, false);
        return new StaffViewHolder(view);
    }
}