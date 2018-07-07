package com.example.sami.popularmovies.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MoviesList {

    @SerializedName("page")
    public int mPage;

    @SerializedName("total_results")
    public int mTotalResults;

    @SerializedName("total_pages")
    public int mTotalPages;

    @SerializedName("results")
    public ArrayList<Movie> mMoviesResponseList;

    public ArrayList<Movie> getMovies()
    {
        return mMoviesResponseList;
    }
}

