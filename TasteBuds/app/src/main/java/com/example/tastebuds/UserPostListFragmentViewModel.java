package com.example.tastebuds;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.tastebuds.model.Model;
import com.example.tastebuds.model.Post;

import java.util.LinkedList;
import java.util.List;

public class UserPostListFragmentViewModel extends ViewModel {
//    private List<Post> data = new LinkedList<>();
//
//    public List<Post> getData() {
//        return data;
//    }
//
//    public void setData(List<Post> data) {
//        this.data = data;
//    }

    private LiveData<List<Post>> data = Model.instance().getAllUserPosts();
    public LiveData<List<Post>> getData() {
        return data;
    }
}
