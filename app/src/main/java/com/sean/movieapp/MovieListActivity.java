package com.sean.movieapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.sean.movieapp.adapters.MovieRecyclerView;
import com.sean.movieapp.adapters.OnMovieListener;
import com.sean.movieapp.models.MovieModel;
import com.sean.movieapp.requests.Services;
import com.sean.movieapp.responses.MovieSearchResponse;
import com.sean.movieapp.utils.Credentials;
import com.sean.movieapp.utils.MovieApi;
import com.sean.movieapp.viewmodels.MovieListViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieListActivity extends AppCompatActivity implements OnMovieListener {

    public static final String TAG = "MovieListActivity";

    private RecyclerView recyclerView;

    //adapter
    private MovieRecyclerView movieRecyclerView;

//    Button btn;

    // ViewModel
    private MovieListViewModel movieListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        movieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);

        ConfigureRecyclerView();
        ObserveAnyChange();
        searchMovieApi("fast", 1);



//        btn = findViewById(R.id.button);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                searchMovieApi("fast", 1);
//            }
//        });



    }

    // Observing any data changes
    private void ObserveAnyChange() {
        movieListViewModel.getMovies().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(@Nullable List<MovieModel> movieModels) {
                if(movieModels != null) {
                    Log.i(TAG, "onChanged: " + movieModels.get(0).getTitle());
                    movieRecyclerView.setMovies(movieModels);
                    for(MovieModel movieModel : movieModels) {
                        Log.i(TAG, "onChanged: " + movieModel.getTitle());

                    }
                }
            }
        });
    }

    // 4- Calling method in Main activity
    private void searchMovieApi(String query, int pageNumber) {
        movieListViewModel.searchMovieApi(query, pageNumber);
    }

    // 5- Initializing recyclerView & adding data to it
    private void ConfigureRecyclerView() {
        this.movieRecyclerView = new MovieRecyclerView(this);
        recyclerView.setAdapter(this.movieRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void GetRetrofitResponse() {
        MovieApi movieApi = Services.getMovieApi();
        Call<MovieSearchResponse> responseCall = movieApi.searchMovie(
                Credentials.API_KEY,
                "Action",
                1);

        responseCall.enqueue(new Callback<MovieSearchResponse>() {
            @Override
            public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {
                if(response.code() == 200) {
                    Log.i(TAG, "onResponse: jajajaja" + response.body().toString());
                    Log.i(TAG, "onResponse: " + call.request().url());
                    List<MovieModel> movies = new ArrayList<>(response.body().getMovies());

                    for (MovieModel movie : movies) {
                        Log.i(TAG, "onResponse: " + movie.getRelease_date());
                    }
                } else {
                    try {
                        Log.i(TAG, "onResponse: " + response.errorBody().string());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieSearchResponse> call, Throwable t) {
                Log.i(TAG, "onFailure: jajajaja" + call.request().url());
            }
        });
    }

    private void GetRetrofitResponseById() {
        MovieApi movieApi = Services.getMovieApi();
        Call<MovieModel> responseCall = movieApi.getMovie(
                550,
                Credentials.API_KEY);

        responseCall.enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
                if(response.code() == 200) {
                    MovieModel movie = response.body();
                    Log.i(TAG, "onResponse: " + movie.getTitle());
                } else {
                    try {
                        Log.i(TAG, "onResponse: " + response.errorBody().string());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieModel> call, Throwable t) {
                Log.i(TAG, "onFailure: jajajaja" + call.request().url());
            }
        });
    }

    @Override
    public void onMovieClick(int position) {

    }

    @Override
    public void onCategoryClick(String category) {

    }
}
