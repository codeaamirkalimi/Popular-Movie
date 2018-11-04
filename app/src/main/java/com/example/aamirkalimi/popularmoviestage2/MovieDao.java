package com.example.aamirkalimi.popularmoviestage2;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface MovieDao {

    @Insert
    void insert(MovieList movie);

    @Update
    void update(MovieList... repos);

    @Delete
    void delete(MovieList movie);

    @Query("SELECT * FROM  moviesFav")
    LiveData<List<MovieList>> getMovies();

    @Query("SELECT * FROM moviesFav WHERE id = :number")
    boolean getMovieWithId(long number);
}
