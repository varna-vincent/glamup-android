package com.coen499.glamup.models;

public class Review {

    private String review;
    private Float rating;
    private String userEmail;
    private String userName;

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {

        String str = "\n\nUser email : " + getUserEmail()
                + "\nUser name : " + getUserName()
                + "\nRating : " + getRating()
                + "\nReview : " + getReview();
        System.out.println(str);
        return str;
    }
}
