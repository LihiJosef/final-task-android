package com.example.tastebuds;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

abstract class GenericViewHolder<T> extends RecyclerView.ViewHolder{
    List<T> data;

    public GenericViewHolder(@NonNull View itemView, GenericRecyclerAdapter.OnItemClickListener listener, List<T> data) {
        super(itemView);
        this.data = data;
    }

    public abstract void bind(T object, int pos);

//    public void bind(T object, int pos){
//        userTv.setText(post.getUserName());
//        locationTv.setText(post.getLocation());
//        reviewTv.setText(post.getReview());
//        starsRB.setRating((float)post.getStars());
//
//        if(post.getImageUrl().trim().length() != 0) {
//            Picasso.get().load(post.getImageUrl()).placeholder(R.drawable.blank_img).into(postImage);
//        } else {
//            postImage.setImageResource(R.drawable.blank_img);
//        }
//    }
}

public abstract class GenericRecyclerAdapter<T, VH extends GenericViewHolder<T>> extends RecyclerView.Adapter<VH>{
    /*Set listener to catch view row click and handle in the activity ->*/
    OnItemClickListener listener;
    LayoutInflater inflater;
    List<T> data;

    public static interface OnItemClickListener{
        void onItemClick(int pos);
    }

    public void setData(List<T> data){
        this.data = data;
        notifyDataSetChanged();
    }

    public GenericRecyclerAdapter(LayoutInflater inflater, List<T> data){
        this.inflater = inflater;
        this.data = data;
    }

    void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

//    @NonNull
//    @Override
//    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        // 4
//        View view = inflater.inflate(R.layout.feed_list_row, parent, false);
//        return new VH(view, listener, data);
//    }

    @Override
    public abstract VH onCreateViewHolder(ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        T object = data.get(position);
        holder.bind(object, position);
    }

    @Override
    public int getItemCount() {
        if (data == null) return 0;
        return data.size();
    }
}