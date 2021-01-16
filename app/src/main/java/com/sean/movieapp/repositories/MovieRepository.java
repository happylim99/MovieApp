package com.sean.movieapp.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.sean.movieapp.models.MovieModel;
import com.sean.movieapp.requests.MovieApiClient;

import java.util.List;

public class MovieRepository {

    private static MovieRepository instance;

    // Live data
    // move to movieApiClient again
//    private MutableLiveData<List<MovieModel>> movies;

    private MovieApiClient movieApiClient;

    private MovieRepository() {
        movieApiClient = MovieApiClient.getInstance();
    }

    public static MovieRepository getInstance() {
        if(instance == null) {
            instance = new MovieRepository();
        }
        return instance;
    }

    public LiveData<List<MovieModel>> getMovies() {
        return movieApiClient.getMovies();
    }

    // 2- Calling the method
    public void searchMovieApi(String query, int pageNumber) {
        movieApiClient.searchMovieApi(query, pageNumber);
    }

}
