package com.coen499.glamup.models;

import android.net.Uri;

import java.io.Serializable;

public class User implements Serializable {

    private String tokenId;
    private String userName;
    private String userEmail;
    private String userPhoto;

    public User() {}

    public User(String tokenId, String userName, String userEmail, String userPhoto) {

        this.userEmail = userEmail;
        this.userName = userName;
        this.tokenId = tokenId;
        this.userPhoto = userPhoto;
    }

    public String getTokenId() {
        return tokenId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserPhoto() {
        return userPhoto;
    }
}
