package com.sean.movieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.sean.movieapp.models.MovieModel;
import com.sean.movieapp.requests.Services;
import com.sean.movieapp.responses.MovieSearchResponse;
import com.sean.movieapp.utils.Credentials;
import com.sean.movieapp.utils.MovieApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieListActivity extends AppCompatActivity {

    public static final String TAG = "MovieListActivity";

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetRetrofitResponse();
            }
        });

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
}
