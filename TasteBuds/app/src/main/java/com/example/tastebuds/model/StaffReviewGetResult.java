package com.example.tastebuds.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StaffReviewGetResult {
    @SerializedName("staffReviews")
    private List<StaffReview> staffReviews;

    public List<StaffReview> getStaffReviews() {
        return staffReviews;
    }

    public void setStaffReviews(List<StaffReview> staffReviews) {
        this.staffReviews = staffReviews;
    }
}
