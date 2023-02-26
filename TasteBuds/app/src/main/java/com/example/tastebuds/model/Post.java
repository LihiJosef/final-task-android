package com.example.tastebuds.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

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

    public Post(@NonNull String id, String userName, String imageUrl, String location, Integer stars, String review) {
        this.id = id;
        this.userName = userName;
        this.imageUrl = imageUrl;
        this.location = location;
        this.stars = stars;
        this.review = review;
    }

    static final String ID = "id";
    static final String USER_NAME = "userName";
    static final String IMAGE_URL = "imageUrl";
    static final String LOCATION = "location";
    static final String STARS = "stars";
    static final String REVIEW = "review";

    public static Post parseJson(Map<String, Object> studentJson){
        String id = (String) studentJson.get(ID);
        String name = (String) studentJson.get(USER_NAME);
        String imageUrl = (String) studentJson.get(IMAGE_URL);
        String location = (String) studentJson.get(LOCATION);
        Integer stars = Integer.parseInt(studentJson.get(STARS).toString());
        String review = (String) studentJson.get(REVIEW);

        Post st = new Post(id,name,imageUrl,location, stars,review);
        return st;
    }

    public Map<String, Object> toJson(){
        // Create a new post
        Map<String, Object> postJson = new HashMap<>();
        postJson.put(ID, getId());
        postJson.put(USER_NAME, getUserName());
        postJson.put(IMAGE_URL, getImageUrl());
        postJson.put(LOCATION, getLocation());
        postJson.put(STARS, getStars());
        postJson.put(REVIEW, getReview());

        return postJson;
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
}
