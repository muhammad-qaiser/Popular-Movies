package com.example.sami.popularmovies.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponse {

    @SerializedName("page")
    public int mPage;

    @SerializedName("total_results")
    public int mTotalResults;

    @SerializedName("total_pages")
    public int mTotalPages;

    @SerializedName("results")
    public List<Movie> mMoviesResposeList;
}

