package com.example.sami.popularmovies.utils;

import com.example.sami.popularmovies.model.MoviesList;
import com.example.sami.popularmovies.model.ReviewsList;
import com.example.sami.popularmovies.model.TrailersList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MoviesClient {

    @GET("/3/movie/{id}/videos")
    Call<TrailersList> trailers(@Path("id") int id, @Query("api_key") String apiKey);

    @GET("/3/movie/{id}/reviews")
    Call<ReviewsList> reviews(@Path("id") int id, @Query("api_key") String apiKey);

    @GET("/3/movie/{typ}")
    Call<MoviesList> movies(@Path("typ") String typ, @Query("api_key") String apiKey);
}
