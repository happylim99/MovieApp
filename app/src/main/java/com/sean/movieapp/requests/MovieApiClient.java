package com.sean.movieapp.requests;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.sean.movieapp.AppExecutors;
import com.sean.movieapp.models.MovieModel;
import com.sean.movieapp.responses.MovieSearchResponse;
import com.sean.movieapp.utils.Credentials;
import com.sean.movieapp.utils.MovieApi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class MovieApiClient {

    private static final String TAG = "MovieApiClient";
    private MutableLiveData<List<MovieModel>> movies;
    private static MovieApiClient instance;
    private RetrieveMoviesRunnable retrieveMoviesRunnable;

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

    // 1- This method that will be called throught the classes
    public void searchMovieApi(String query, int pageNumber) {
        if(retrieveMoviesRunnable != null) {
            retrieveMoviesRunnable = null;
        }
        retrieveMoviesRunnable = new RetrieveMoviesRunnable(query, pageNumber);
        final Future handler = AppExecutors.getInstance().networkIO().submit(retrieveMoviesRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                // Cancelling the retrofit call
                handler.cancel(true);
            }
        }, 3000, TimeUnit.MILLISECONDS);
    }

    private class RetrieveMoviesRunnable implements Runnable {

        private String query;
        private int pageNumber;
        boolean cancelRequest;

        public RetrieveMoviesRunnable(String query, int pageNumber) {
            this.query = query;
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {
            try {
                Response response = getMovies(query, pageNumber).execute();
                if(cancelRequest) {
                    return;
                }
                if(response.code() == 200) {
                    List<MovieModel> list = new ArrayList<>(((MovieSearchResponse)response.body()).getMovies());
                    if(pageNumber == 1) {
                        // postValue used for background thread
                        // setValue not for background thread
                        movies.postValue(list);
                    } else {
                        List<MovieModel> currentMovies = movies.getValue();
                        currentMovies.addAll(list);
                        movies.postValue(currentMovies);
                    }
                } else {
                    String error = response.errorBody().string();
                    Log.i(TAG, "run: " + error);
                    movies.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (cancelRequest) {
                return;
            }
        }

        private Call<MovieSearchResponse> getMovies(String query, int pageNumber) {
            Log.i(TAG, "getMovies: " + query);
            MovieApi movieApi = Services.getMovieApi();
            Call<MovieSearchResponse> responseCall = movieApi.searchMovie(
                    Credentials.API_KEY,
                    query,
                    pageNumber);
            Log.i(TAG, "getMovies: " + responseCall);
            return responseCall;
        }

        private void cancelRequest() {
            Log.i(TAG, "cancelRequest: ");
            cancelRequest = true;
        }
    }

}
