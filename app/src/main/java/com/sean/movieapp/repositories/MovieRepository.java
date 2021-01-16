package com.sean.movieapp.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.sean.movieapp.models.MovieModel;

import java.util.List;

public class MovieRepository {

    private static MovieRepository instance;

    // Live data
    private MutableLiveData<List<MovieModel>> movies;

    private MovieRepository() {
       movies = new MutableLiveData<>();
    }

    public static MovieRepository getInstance() {
        if(instance == null) {
            instance = new MovieRepository();
        }
        return instance;
    }

    public LiveData<List<MovieModel>> getMovies() {
        return movies;
    }
}
