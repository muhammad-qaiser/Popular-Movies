package com.example.sami.popularmovies.model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.sami.popularmovies.Database.AppDatabase;

import java.util.List;

public class FavoriteViewModel extends AndroidViewModel {
    public FavoriteViewModel(@NonNull Application application) {
        super(application);
        AppDatabase mDb = AppDatabase.getInstance(this.getApplication());
        mMoviesList = mDb.movieDao().loadAllMovies();
    }

    private LiveData<List<Movie>> mMoviesList;

    public LiveData<List<Movie>> getAllMovies() {
        return mMoviesList;
    }
}
