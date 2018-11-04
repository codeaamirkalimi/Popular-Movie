package com.example.aamirkalimi.popularmoviestage2;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = { MovieList.class }, version = 3)
public abstract class MovieDB extends RoomDatabase {
    public abstract MovieDao getMovieDao();
    private static MovieDB INSTANCE;
    public static MovieDB getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (MovieDB.class) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(), MovieDB.class, StringUrlConstants.DB_NAME).build();
            }
        }
        return INSTANCE;
    }
    public static void destroyInstance() {
        INSTANCE = null;
    }
}
