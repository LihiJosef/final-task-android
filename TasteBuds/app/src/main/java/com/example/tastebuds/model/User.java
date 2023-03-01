package com.example.tastebuds.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity
public class User {
    @PrimaryKey
    @NonNull
    private String userName="";
    private String nickName="";
    private String profileImgUrl="";
    private String email;
    private String password;

    public User(@NonNull String userName, String nickName, String profileImgUrl, String email, String password) {
        this.userName = userName;
        this.nickName = nickName;
        this.profileImgUrl = profileImgUrl;
        this.email = email;
        this.password = password;
    }

    @NonNull
    public String getUserName() {
        return userName;
    }

    public void setUserName(@NonNull String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getProfileImgUrl() {
        return profileImgUrl;
    }

    public void setProfileImgUrl(String profileImgUrl) {
        this.profileImgUrl = profileImgUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
// TODO : IS NEEDED?
