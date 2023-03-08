package com.example.tastebuds;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.tastebuds.model.Model;
import com.example.tastebuds.model.Post;

import java.util.LinkedList;
import java.util.List;

public class UserPostListFragmentViewModel extends ViewModel {
    private LiveData<List<Post>> data = Model.instance().getAllUserPosts();
    public LiveData<List<Post>> getData() {
        return data;
    }
}
