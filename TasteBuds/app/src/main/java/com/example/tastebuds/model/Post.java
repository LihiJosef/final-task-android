package com.example.tastebuds.model;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.tastebuds.MyApplication;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

import java.util.HashMap;
import java.util.Map;

@Entity
public class Post {
    @PrimaryKey
    @NonNull
    private String id = "";
    private String userName = "";
    private String imageUrl = "";
    private String location = "";
    private Integer stars = 0;
    private String review = "";
    private Long lastUpdated;


    public Post(@NonNull String id, String userName, String imageUrl, String location, Integer stars, String review) {
        this.id = id;
        this.userName = userName;
        this.imageUrl = imageUrl;
        this.location = location;
        this.stars = stars;
        this.review = review;
    }

    static final String COLLECTION = "posts";
    static final String ID = "id";
    static final String USER_NAME = "userName";
    static final String IMAGE_URL = "imageUrl";
    static final String LOCATION = "location";
    static final String STARS = "stars";
    static final String REVIEW = "review";
    static final String LAST_UPDATED = "lastUpdated";
    static final String LOCAL_LAST_UPDATED = "student_local_last_update";


    public static Post parseJson(Map<String, Object> studentJson){
        String id = (String) studentJson.get(ID);
        String name = (String) studentJson.get(USER_NAME);
        String imageUrl = (String) studentJson.get(IMAGE_URL);
        String location = (String) studentJson.get(LOCATION);
        Integer stars = Integer.parseInt(studentJson.get(STARS).toString());
        String review = (String) studentJson.get(REVIEW);
        Post st = new Post(id,name,imageUrl,location, stars,review);

        try {
            Timestamp time = (Timestamp) studentJson.get(LAST_UPDATED);
            st.setLastUpdated(time.getSeconds());
        } catch (Exception e) {

        }

        return st;
    }

    public static Long getLocalLastUpdate() {
        SharedPreferences sharedPref = MyApplication.getMyContext().getSharedPreferences("TAG", Context.MODE_PRIVATE);
        return sharedPref.getLong(LOCAL_LAST_UPDATED, 0);
    }

    public static  void setLocalLastUpdate(Long time) {
        MyApplication.getMyContext().getSharedPreferences("TAG", Context.MODE_PRIVATE)
                .edit().putLong(LOCAL_LAST_UPDATED, time).commit();
    }

    public Map<String, Object> toJson(){
        // Create a new user with a first and last name
        Map<String, Object> studentJson = new HashMap<>();
        studentJson.put(ID, getId());
        studentJson.put(USER_NAME, getUserName());
        studentJson.put(IMAGE_URL, getImageUrl());
        studentJson.put(LOCATION, getLocation());
        studentJson.put(STARS, getStars());
        studentJson.put(REVIEW, getReview());
        studentJson.put(LAST_UPDATED, FieldValue.serverTimestamp());

        return studentJson;
    }


    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Long getLastUpdated() { return this.lastUpdated;}

    public void setLastUpdated(Long lastUpdated) { this.lastUpdated = lastUpdated; }
}
