package com.example.tastebuds.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface StaffReviewApi {
    @GET("our-foods")
    Call<List<StaffReview>> getStaffReviews();
}
