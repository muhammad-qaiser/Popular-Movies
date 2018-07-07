package com.example.sami.popularmovies.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.sami.popularmovies.model.Movie;

import timber.log.Timber;


@Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MovieDAO movieDao();
    private static final String dbName = "favoriteMovies";

    private static AppDatabase mInstance;
    private static final Object LOCK = new Object();
    public static AppDatabase getInstance(Context context)
    {
        if (mInstance == null)
        {
            synchronized (LOCK)
            {
                mInstance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, dbName)
                        .build();
                Timber.d("Database Created");
            }
        }
        return mInstance;
    }

}
