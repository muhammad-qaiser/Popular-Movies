package com.example.sami.popularmovies.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

import com.example.sami.popularmovies.model.Movie;

@Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MovieDAO movieDao();

    private static AppDatabase mInstance;
    public static AppDatabase getInstance(Context context)
    {
        if (mInstance == null) {
                mInstance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, "favoriteMovies")
                        .build();
            Log.i("Database" , "Database Created");
        }
        return mInstance;
    }

}
