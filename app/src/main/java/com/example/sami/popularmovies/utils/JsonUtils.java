package com.example.sami.popularmovies.utils;

import com.example.sami.popularmovies.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


// Primary purpose is to convert string to Json Object.

public class JsonUtils {

    //Take JSON String as a parameter and convert that to a List of Movie Objects.
    public static List<Movie> parseMovieJson(String json)
    {
        List<Movie> movies = new ArrayList<>();

        try {
            JSONObject moviesObject = new JSONObject(json);
            JSONArray resultArray = moviesObject.getJSONArray("results");

            for (int i=0; i< resultArray.length() ; i++)
            {
                JSONObject movieObject = resultArray.getJSONObject(i);
                Movie movie = getMovieFromJson(movieObject);
                movies.add(movie);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movies;
    }

    //Convert a single JSONObject to a Movie Object
    private static Movie getMovieFromJson(JSONObject movieObject)
    {
        Movie movie = new Movie();
        try {
            movie.setmVoteCount(movieObject.getInt("vote_count"));
            movie.setmId(movieObject.getInt("id"));
            movie.setmHaveVideo(movieObject.optBoolean("video"));
            movie.setmVoteAvg(Float.valueOf(movieObject.optString("vote_average")));
            movie.setmTitle(movieObject.optString("title"));
            movie.setmPopularity(movieObject.optLong("popularity"));
            movie.setmPosterPath(movieObject.optString("poster_path"));
            movie.setmLanguage(movieObject.optString("original_language"));
            movie.setmBackdropPath(movieObject.optString("backdrop_path"));
            movie.setmOverview(movieObject.optString("overview"));
            movie.setmReleaseDate(movieObject.optString("release_date"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movie;
    }
}
