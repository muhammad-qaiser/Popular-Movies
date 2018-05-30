package com.example.sami.popularmovies.utils;

import android.net.Uri;


import com.example.sami.popularmovies.BuildConfig;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    //Ref: Lesson 2
    private static final String MOVIE_BASE_URL = "http://api.themoviedb.org/3/movie";
    private final static String PARAM_POP = "popular";
    private final static String PARAM_TOP = "top_rated";
    private static final String PARAM_QUERY = "api_key";

    //Place your API Key Here.
    //To create one goto  https://www.themoviedb.org/account/signup.
    private static final String API_KEY = BuildConfig.MOVIEDB_API_KEY;

    private static final int POPULAR = 0;
    private static final int TOP_RATED = 1;

    public static URL buildUrl (int param)
    {
        Uri builtUri;
        if (param == TOP_RATED)
        {
            builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                    .appendPath(PARAM_TOP)
                    .appendQueryParameter(PARAM_QUERY, API_KEY)
                    .build();
        }
        else{
            builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                    .appendPath(PARAM_POP)
                    .appendQueryParameter(PARAM_QUERY, API_KEY)
                    .build();
        }
        URL url = null;
        try {
            url = new URL(builtUri.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
