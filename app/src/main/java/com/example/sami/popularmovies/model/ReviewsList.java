package com.example.sami.popularmovies.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ReviewsList {

    @SerializedName("id")
    public int id;

    @SerializedName("results")
    public ArrayList<Review> mReviewsList;

    public ArrayList<Review> getReviews()
    {
        return mReviewsList;
    }
}
