package com.example.tastebuds;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.tastebuds.model.PostModel;
import com.example.tastebuds.model.Post;

import java.util.List;

public class UserPostListFragmentViewModel extends ViewModel {
    private LiveData<List<Post>> data = PostModel.instance().getAllUserPosts();
    public LiveData<List<Post>> getData() {
        return data;
    }
}
