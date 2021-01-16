package com.sean.movieapp.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sean.movieapp.models.MovieModel;
import com.sean.movieapp.repositories.MovieRepository;

import java.util.List;

public class MovieListViewModel extends ViewModel {
    private static final String TAG = "MovieListViewModel : ";

    // Live data
    // move to repositories
//    private MutableLiveData<List<MovieModel>> movies =

    private MovieRepository movieRepository;

    public MovieListViewModel() {
        this.movieRepository = MovieRepository.getInstance();
    }

    public LiveData<List<MovieModel>> getMovies() {
        return this.movieRepository.getMovies();
    }

    // 3- Calling method
    public void searchMovieApi(String query, int pageNumber) {
        Log.i(TAG, "searchMovieApi: " + query + " : " + pageNumber);
        movieRepository.searchMovieApi(query, pageNumber);
    }

}
