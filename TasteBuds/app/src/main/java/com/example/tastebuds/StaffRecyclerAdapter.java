package com.example.tastebuds;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tastebuds.model.StaffReview;
import com.squareup.picasso.Picasso;

import java.util.List;

// 3
class StaffViewHolder extends RecyclerView.ViewHolder{
    ImageView userImage;
    TextView userTv;
    ImageView postImage;
    TextView locationTv;
    TextView reviewTv;
    RatingBar starsRB;
    List<StaffReview> data;

    public StaffViewHolder(@NonNull View itemView, StaffRecyclerAdapter.OnItemClickListener listener, List<StaffReview> data) {
        super(itemView);
        this.data = data;
        userImage = itemView.findViewById(R.id.feedlistrow_user_img);
        userTv = itemView.findViewById(R.id.feedlistrow_user_tv);
        postImage = itemView.findViewById(R.id.feedlistrow_post_img);
        locationTv = itemView.findViewById(R.id.feedlistrow_location_tv);
        reviewTv = itemView.findViewById(R.id.feedlistrow_review_tv);
        starsRB = itemView.findViewById(R.id.feedlistrow_ratingbar);
    }

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

public class StaffRecyclerAdapter extends RecyclerView.Adapter<StaffViewHolder>{
    /*Set listener to catch view row click and handle in the activity ->*/
    OnItemClickListener listener;
    LayoutInflater inflater;
    List<StaffReview> data;

    public static interface OnItemClickListener{
        void onItemClick(int pos);
    }

    public void setData(List<StaffReview> data){
        this.data = data;
        notifyDataSetChanged();
    }

    public StaffRecyclerAdapter(LayoutInflater inflater, List<StaffReview> data){
        this.inflater = inflater;
        this.data = data;
    }

    void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public StaffViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 4
        View view = inflater.inflate(R.layout.feed_list_row, parent, false);
        return new StaffViewHolder(view, listener, data);
    }

    @Override
    public void onBindViewHolder(@NonNull StaffViewHolder holder, int position) {
        StaffReview staffReview = data.get(position);
        holder.bind(staffReview, position);
    }

    @Override
    public int getItemCount() {
        if (data == null) return 0;
        return data.size();
    }
}