package com.coen499.glamup.models;

public class User {

    private String tokenId;
    private String userName;
    private String userEmail;

    public User() {}

    public User(String tokenId, String userName, String userEmail) {

        this.userEmail=userEmail;
        this.userName=userName;
        this.tokenId=tokenId;
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
}
