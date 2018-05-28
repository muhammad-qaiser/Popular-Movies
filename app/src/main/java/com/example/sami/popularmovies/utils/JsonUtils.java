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
            movie.setmHaveVideo(movieObject.getBoolean("video"));
            movie.setmVoteAvg(Float.valueOf(movieObject.getString("vote_average")));
            movie.setmTitle(movieObject.getString("title"));
            movie.setmPopularity(movieObject.getLong("popularity"));
            movie.setmPosterPath(movieObject.getString("poster_path"));
            movie.setmLanguage(movieObject.getString("original_language"));
            movie.setmBackdropPath(movieObject.getString("backdrop_path"));
            movie.setmOverview(movieObject.getString("overview"));
            movie.setmReleaseDate(movieObject.getString("release_date"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movie;
    }

}
