package com.example.tastebuds.model;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StaffReviewModel {
    private static final StaffReviewModel _instance = new StaffReviewModel();

    public static StaffReviewModel instance() {
        return _instance;
    }

    final String BASE_URL = "https://free-food-menus-api-production.up.railway.app/";
    Retrofit retrofit;
    StaffReviewApi staffReviewApi;

    private StaffReviewModel() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        staffReviewApi = retrofit.create(StaffReviewApi.class);
    }

    public enum LoadingState {
        LOADING,
        NOT_LOADING
    }

    final public MutableLiveData<StaffReviewModel.LoadingState> EventPostsListLoadingState = new MutableLiveData<StaffReviewModel.LoadingState>(LoadingState.LOADING);

    public LiveData<List<StaffReview>> getStaffReviews() {
        EventPostsListLoadingState.setValue(StaffReviewModel.LoadingState.LOADING);
        MutableLiveData<List<StaffReview>> data = new MutableLiveData<>();
        Call<List<StaffReview>> call = staffReviewApi.getStaffReviews();
        call.enqueue(new Callback<List<StaffReview>>() {
            @Override
            public void onResponse(Call<List<StaffReview>> call, Response<List<StaffReview>> response) {
                if (response.isSuccessful()) {
                    Log.d("APIcheck", "----- getStaffReviews happened");
                    List<StaffReview> res = response.body();
                    data.setValue(res);
                } else {
                    try {
                        Log.d("APIcheck", "----- getStaffReviews response error" + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                EventPostsListLoadingState.postValue(StaffReviewModel.LoadingState.NOT_LOADING);
            }

            @Override
            public void onFailure(Call<List<StaffReview>> call, Throwable t) {
                Log.d("APIcheck", "----- getStaffReviews fail" + t.getMessage());
                EventPostsListLoadingState.postValue(StaffReviewModel.LoadingState.NOT_LOADING);
            }
        });

        return data;
    }
}
