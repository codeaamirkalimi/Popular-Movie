package com.example.aamirkalimi.popularmoviestage2;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.util.Log;

import java.util.List;

public class MovieViewModel extends AndroidViewModel {
    private static final String TAG = MovieViewModel.class.getSimpleName();
    private LiveData<List<MovieList>> tasks;
    public MovieViewModel(Application application) {
        super(application);
        MovieDB database = MovieDB.getInstance(this.getApplication());
        Log.d(TAG, "Actively retrieving the tasks from the DataBase");
        tasks = database.getMovieDao().getMovies();
    }
    public LiveData<List<MovieList>> getTasks() {
        return tasks;
    }
}
