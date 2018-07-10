package com.example.sami.popularmovies;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Parcelable;
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

import com.example.sami.popularmovies.Adapter.MoviesAdapter;
import com.example.sami.popularmovies.Database.AppDatabase;
import com.example.sami.popularmovies.model.FavoriteViewModel;
import com.example.sami.popularmovies.model.Movie;
import com.example.sami.popularmovies.model.MoviesList;
import com.example.sami.popularmovies.utils.ListItemClickListener;
import com.example.sami.popularmovies.utils.MoviesClient;
import com.example.sami.popularmovies.utils.RetrofitClient;
import com.facebook.stetho.Stetho;

import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements ListItemClickListener, AdapterView.OnItemSelectedListener {

    private static final int POPULAR = 0;
    private static final int TOP_RATED = 1;
    private static final int FAVOURITE = 2;
    private static final String POPULARSTR = "popular";
    private static final String TOPRATEDSTR = "top_rated";
    private final String MOVIE_OBJECT_EXTRA = "movieObj";
    private final String IS_FAVORITE_EXTRA = "isFavorite";

    private final String KEY_MOVIE_STATE = "movieState";
    private static Parcelable mListState;
    private GridLayoutManager mLayoutManager;

    @BindView(R.id.movies_rv) RecyclerView mMoviesRV;
    @BindView (R.id.tv_error_message_display) TextView m_tv_error;
    @BindView (R.id.pb_loading_indicator) ProgressBar m_pb_loading;
    @BindView(R.id.spinner) Spinner mSpinnerSearch;

    //To Store list of movies fetched from MovieDB
    private static List<Movie> mMoviesList;
    private static List<Movie> mFavMovies;
    AppDatabase mDatabase;
    MoviesAdapter mMoviesAdapter;

    //Width of each column.
    private static final int COLUMN_WIDTH = 140;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //Stetho
        Stetho.InitializerBuilder initializerBuilder = Stetho.newInitializerBuilder(this);
        initializerBuilder.enableWebKitInspector(Stetho.defaultInspectorModulesProvider(getApplicationContext()));
        Stetho.Initializer initializer = initializerBuilder.build();
        Stetho.initialize(initializer);

        //Timber
        Timber.plant(new Timber.DebugTree());

        mDatabase = AppDatabase.getInstance(this);
        mLayoutManager = new GridLayoutManager(this
                ,calculateNoOfColumns(this));
        mMoviesRV.setLayoutManager(mLayoutManager);
        mMoviesAdapter = new MoviesAdapter(MainActivity.this);
        mMoviesRV.setAdapter(mMoviesAdapter);


        //Default spinner Position;
        mSpinnerSearch.setSelection(POPULAR);
        mSpinnerSearch.setOnItemSelectedListener(this);

        if(savedInstanceState == null) {
            loadMovies();
            favoriteMovieVMInit();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mListState = mLayoutManager.onSaveInstanceState();
        outState.putParcelable(KEY_MOVIE_STATE, mListState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(mListState!= null )
            mListState = savedInstanceState.getParcelable(KEY_MOVIE_STATE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mListState != null)
            mLayoutManager.onRestoreInstanceState(mListState);
    }

    public void favoriteMovieVMInit()
    {
        FavoriteViewModel viewModel = ViewModelProviders.of(this).get(FavoriteViewModel.class);

        viewModel.getAllMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                mFavMovies = movies;
                if(mSpinnerSearch.getSelectedItemPosition() == FAVOURITE) {
                    mMoviesList = movies;
                    mMoviesAdapter.setMoviesList(mMoviesList);
                }
            }
        });
    }
    private void loadMovies()
    {
        int position = mSpinnerSearch.getSelectedItemPosition();
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        MoviesClient client = new RetrofitClient().getClient().create(MoviesClient.class);
        String type = "";
        switch (position)
        {
            case FAVOURITE:
                m_pb_loading.setVisibility(View.INVISIBLE);
                mMoviesList = mFavMovies;
                mMoviesRV.setVisibility(View.VISIBLE);
                m_tv_error.setVisibility(View.INVISIBLE);
                mMoviesAdapter.setMoviesList(mMoviesList);
                return;
            case TOP_RATED:
                type = TOPRATEDSTR;
                break;
            case POPULAR:
                type = POPULARSTR;
                break;
        }
        if(isConnected) {
            Call<MoviesList> moviesListCall = client.movies(type, BuildConfig.MOVIEDB_API_KEY);

            m_tv_error.setVisibility(View.INVISIBLE);
            m_pb_loading.setVisibility(View.VISIBLE);

            moviesListCall.enqueue(new Callback<MoviesList>() {
                @Override
                public void onResponse(Call<MoviesList> call, Response<MoviesList> response) {
                    m_pb_loading.setVisibility(View.INVISIBLE);
                    mMoviesList = response.body().getMovies();
                    mMoviesRV.setVisibility(View.VISIBLE);
                    m_tv_error.setVisibility(View.INVISIBLE);
                    mMoviesAdapter.setMoviesList(mMoviesList);
                }

                @Override
                public void onFailure(Call<MoviesList> call, Throwable t) {
                    hideViews();
                }
            });
        }
        else if((position == POPULAR ||position == TOP_RATED) && !isConnected)
        {
            hideViews();
        }
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
            intent.putExtra(MOVIE_OBJECT_EXTRA, m);
            intent.putExtra(IS_FAVORITE_EXTRA , isMovieFavorite(m));
            startActivity(intent);
        }
    }

    @Override
    public void onLongClick (int clickedItemIndex) {

    }

    void hideViews()
    {
        mMoviesRV.removeAllViews();
        mMoviesRV.setVisibility(View.INVISIBLE);
        m_tv_error.setVisibility(View.VISIBLE);
        m_pb_loading.setVisibility(View.INVISIBLE);
    }

    public boolean isMovieFavorite(Movie movie)
    {
        for(int i=0; i<mFavMovies.size(); i++)
        {
            Movie m = mFavMovies.get(i);
            if (m.mId == movie.mId && m.mTitle.equals(movie.mTitle) && m.mLanguage.equals(movie.mLanguage))
                return true;
        }
        return false;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        loadMovies();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}