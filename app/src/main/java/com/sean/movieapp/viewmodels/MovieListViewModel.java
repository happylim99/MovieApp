package com.sean.movieapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sean.movieapp.models.MovieModel;
import com.sean.movieapp.repositories.MovieRepository;

import java.util.List;

public class MovieListViewModel extends ViewModel {

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
}
