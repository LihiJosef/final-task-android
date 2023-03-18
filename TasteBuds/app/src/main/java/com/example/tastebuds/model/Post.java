package com.example.tastebuds.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.tastebuds.MyApplication;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

import java.util.HashMap;
import java.util.Map;

@Entity
public class Post implements Parcelable {
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
    static final String IMAGE_FOLDER_NAME = "postsImages";
    static final String ID = "id";
    static final String USER_NAME = "userName";
    static final String IMAGE_URL = "imageUrl";
    static final String LOCATION = "location";
    static final String STARS = "stars";
    static final String REVIEW = "review";
    static final String LAST_UPDATED = "lastUpdated";
    static final String LOCAL_LAST_UPDATED = "student_local_last_update";


    public static Post parseJson(Map<String, Object> postJson) {
        String id = (String) postJson.get(ID);
        String name = (String) postJson.get(USER_NAME);
        String imageUrl = (String) postJson.get(IMAGE_URL);
        String location = (String) postJson.get(LOCATION);
        Integer stars = Integer.parseInt(postJson.get(STARS).toString());
        String review = (String) postJson.get(REVIEW);
        Post post = new Post(id, name, imageUrl, location, stars, review);

        try {
            Timestamp time = (Timestamp) postJson.get(LAST_UPDATED);
            post.setLastUpdated(time.getSeconds());
        } catch (Exception e) {

        }

        return post;
    }

    public static Long getLocalLastUpdate() {
        SharedPreferences sharedPref = MyApplication.getMyContext().getSharedPreferences("TAG", Context.MODE_PRIVATE);
        return sharedPref.getLong(LOCAL_LAST_UPDATED, 0);
    }

    public static void setLocalLastUpdate(Long time) {
        MyApplication.getMyContext().getSharedPreferences("TAG", Context.MODE_PRIVATE)
                .edit().putLong(LOCAL_LAST_UPDATED, time).commit();
    }

    public Map<String, Object> toJson() {
        // Create a new user with a first and last name
        Map<String, Object> postJson = new HashMap<>();
        postJson.put(ID, getId());
        postJson.put(USER_NAME, getUserName());
        postJson.put(IMAGE_URL, getImageUrl());
        postJson.put(LOCATION, getLocation());
        postJson.put(STARS, getStars());
        postJson.put(REVIEW, getReview());
        postJson.put(LAST_UPDATED, FieldValue.serverTimestamp());

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

    public Long getLastUpdated() {
        return this.lastUpdated;
    }

    public void setLastUpdated(Long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String validationMessage() {
        String errorMessage = "";

        if (location.isEmpty()) {
            errorMessage = "Location must not be empty.";
        } else if (review.isEmpty()) {
            errorMessage = "Review must not be empty.";
        } else if (stars < 1) {
            errorMessage = "Stars should be at list 1";
        }

        return errorMessage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    protected Post(Parcel in) {
        this.id = in.readString();
        this.userName = in.readString();
        this.imageUrl = in.readString();
        this.location = in.readString();
        this.stars = in.readInt();
        this.review = in.readString();
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel source) {
            return new Post(source);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(userName);
        dest.writeString(imageUrl);
        dest.writeString(location);
        dest.writeInt(stars);
        dest.writeString(review);
    }
}
