package com.example.sami.popularmovies.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TrailersList {

    @SerializedName("id")
    public int id;

    @SerializedName("results")
    public ArrayList<Trailer> mTrailersList;

    public ArrayList<Trailer> getTrailers()
    {
        return mTrailersList;
    }
}
