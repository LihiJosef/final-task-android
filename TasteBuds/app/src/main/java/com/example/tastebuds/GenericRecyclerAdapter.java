package com.example.tastebuds;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

abstract class GenericViewHolder<T> extends RecyclerView.ViewHolder{
    public GenericViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public abstract void bind(T object, int pos);
}

public abstract class GenericRecyclerAdapter<T, VH extends GenericViewHolder<T>> extends RecyclerView.Adapter<VH>{
    /*Set listener to catch view row click and handle in the activity ->*/
    OnItemClickListener listener;
    LayoutInflater inflater;
    List<T> data;

    void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

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