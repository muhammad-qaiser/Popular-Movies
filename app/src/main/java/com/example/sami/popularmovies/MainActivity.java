package com.example.sami.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.sami.popularmovies.model.Movie;
import com.example.sami.popularmovies.utils.JsonUtils;
import com.example.sami.popularmovies.utils.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MoviesAdapter.ListItemClickListener {

    private static final int POPULAR = 0;
    private static final int TOP_RATED = 1;
    @BindView(R.id.movies_rv) RecyclerView mMoviesRV;
    @BindView (R.id.tv_error_message_display) TextView m_tv_error;
    @BindView (R.id.pb_loading_indicator) ProgressBar m_pb_loading;
    @BindView(R.id.spinner) Spinner mSpinnerSearch;

    //To Store list of movies fetched from MovieDB
    List<Movie> mMoviesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        GridLayoutManager layoutManager = new GridLayoutManager(MainActivity.this
                ,calculateNoOfColumns(getApplicationContext()));
        mMoviesRV.setLayoutManager(layoutManager);
        mMoviesRV.setHasFixedSize(true);

        //Default spinner Position;
        mSpinnerSearch.setSelection(POPULAR);
        mSpinnerSearch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                URL url = NetworkUtils.buildUrl(position);
                new FetchData().execute(url);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    //Width of each column.
    private static final int COLUMN_WIDTH = 140;
    //Calculate the number of columns that can be created on the screen.
    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        return (int) (dpWidth / COLUMN_WIDTH);
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        if(mMoviesList.size() > 0)
        {
            Intent intent = new Intent(this, DetailActivity.class);
            Movie m = mMoviesList.get(clickedItemIndex);
            intent.putExtra("movieObj", m);
            startActivity(intent);
        }
        /*Toast.makeText(this,mMoviesList.get(clickedItemIndex).getmTitle() , Toast.LENGTH_LONG ).show();*/
    }

    //creates an adapter and assign it to RecyclerView
    private void loadMovies()
    {
        MoviesAdapter moviesAdapter;
        moviesAdapter = new MoviesAdapter(mMoviesList ,  MainActivity.this);
        mMoviesRV.setAdapter( moviesAdapter);
    }

    //Async task to fetch movies from the api receives movie Url and return String fetched from api.
    //That String will be further converted into json and finally to movies list.

    private class FetchData extends AsyncTask<URL , Void, String >{

        @Override
        protected String doInBackground(URL... urls) {
            String data = "";
            try {
                data = NetworkUtils.getResponseFromHttpUrl(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            m_tv_error.setVisibility(View.INVISIBLE);
            m_pb_loading.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            m_pb_loading.setVisibility(View.INVISIBLE);

            if(s == null || s.equals(""))
            {
                mMoviesRV.removeAllViews();
                mMoviesRV.setVisibility(View.INVISIBLE);
                m_tv_error.setVisibility(View.VISIBLE);
            }
            else {
                mMoviesList = JsonUtils.parseMovieJson(s);
                mMoviesRV.setVisibility(View.VISIBLE);
                m_tv_error.setVisibility(View.INVISIBLE);
                loadMovies();
            }

        }
    }


}
