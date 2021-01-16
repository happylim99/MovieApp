package com.sean.movieapp.requests;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.sean.movieapp.models.MovieModel;

import java.util.List;

public class MovieApiClient {

    private static final String TAG = "MovieApiClient";
    private MutableLiveData<List<MovieModel>> movies;
    private static MovieApiClient instance;

    private MovieApiClient() {
        movies = new MutableLiveData<>();
    }

    public static MovieApiClient getInstance() {
        if(instance == null) {
            instance = new MovieApiClient();
        }
        return instance;
    }

    public LiveData<List<MovieModel>> getMovies() {
        return movies;
    }

}
