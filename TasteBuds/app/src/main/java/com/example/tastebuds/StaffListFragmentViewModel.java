package com.example.tastebuds;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.tastebuds.model.Model;
import com.example.tastebuds.model.Post;
import com.example.tastebuds.model.StaffReview;
import com.example.tastebuds.model.StaffReviewModel;

import java.util.List;

public class StaffListFragmentViewModel extends ViewModel {
    private LiveData<List<StaffReview>> data = StaffReviewModel.instance.getStaffReviews();
    public LiveData<List<StaffReview>> getData() {
        return data;
    }
}