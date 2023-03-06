package com.example.tastebuds.model;

import retrofit2.Call;
import retrofit2.http.GET;

public interface StaffReviewApi {
    @GET("/our-foods")
    Call<StaffReviewGetResult> getStaffReviews();
}
