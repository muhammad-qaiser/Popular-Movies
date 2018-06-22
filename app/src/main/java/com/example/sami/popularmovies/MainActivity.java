package com.example.sami.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

import com.example.sami.popularmovies.model.ApiResponse;
import com.example.sami.popularmovies.model.Movie;
import com.example.sami.popularmovies.services.FetchMoviesData;
import com.example.sami.popularmovies.services.OnAsyncEventListener;
import com.example.sami.popularmovies.utils.NetworkUtils;
import com.google.gson.Gson;
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


    //Width of each column.
    private static final int COLUMN_WIDTH = 140;

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
               onSpinnerItemSelected(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }



    private void onSpinnerItemSelected(int position)
    {
        try{
            ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
            if(isConnected) {
                URL url = NetworkUtils.buildUrl(position);
                FetchMoviesData moviesData = new FetchMoviesData(new OnAsyncEventListener<String>() {
                    @Override
                    public void onSuccess(String object) {
                        m_pb_loading.setVisibility(View.INVISIBLE);
                        ApiResponse apiResponse = new Gson().fromJson(object,ApiResponse.class);
                        mMoviesList = apiResponse.mMoviesResposeList;
                        mMoviesRV.setVisibility(View.VISIBLE);
                        m_tv_error.setVisibility(View.INVISIBLE);
                        loadMovies();
                    }

                    @Override
                    public void onFailure(Exception e) {
                        hideViews();
                    }

                    @Override
                    public void inProcess() {
                        m_tv_error.setVisibility(View.INVISIBLE);
                        m_pb_loading.setVisibility(View.VISIBLE);
                    }
                });

                moviesData.execute(url);
            }
            else{
                hideViews();
            }
        }
        catch (Exception e )
        {e.printStackTrace();}
    }

    //Calculate the number of columns that can be created on the screen.
    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        return (int) (dpWidth / COLUMN_WIDTH);
    }

    // recyclerView/moviesAdapter Click Listener
    @Override
    public void onListItemClick(int clickedItemIndex) {
        if(mMoviesList.size() > 0)
        {
            Intent intent = new Intent(this, DetailActivity.class);
            Movie m = mMoviesList.get(clickedItemIndex);
            intent.putExtra("movieObj", m);
            startActivity(intent);
        }
    }

    //creates an adapter and assign it to RecyclerView
    private void loadMovies()
    {
        MoviesAdapter moviesAdapter;
        moviesAdapter = new MoviesAdapter(mMoviesList ,  MainActivity.this);
        mMoviesRV.setAdapter( moviesAdapter);
    }

    void hideViews()
    {
        mMoviesRV.removeAllViews();
        mMoviesRV.setVisibility(View.INVISIBLE);
        m_tv_error.setVisibility(View.VISIBLE);
        m_pb_loading.setVisibility(View.INVISIBLE);
    }
}
