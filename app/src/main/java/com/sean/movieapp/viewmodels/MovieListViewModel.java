package com.sean.movieapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sean.movieapp.models.MovieModel;

import java.util.List;

public class MovieListViewModel extends ViewModel {

    // Live data
    private MutableLiveData<List<MovieModel>> movies = new MutableLiveData<>();

    public MovieListViewModel() {
    }

    public LiveData<List<MovieModel>> getMovies() {
        return movies;
    }
}
