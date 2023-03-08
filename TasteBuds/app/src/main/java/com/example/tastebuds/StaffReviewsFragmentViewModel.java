package com.example.tastebuds;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.tastebuds.model.StaffReview;
import com.example.tastebuds.model.StaffReviewModel;

import java.util.List;

public class StaffReviewsFragmentViewModel extends ViewModel {
    private LiveData<List<StaffReview>> data = StaffReviewModel.instance().getStaffReviews();
    public LiveData<List<StaffReview>> getData() {
        return data;
    }
}