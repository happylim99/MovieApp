package com.sean.movieapp.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sean.movieapp.models.MovieModel;

import java.util.List;

public class MovieSearchResponse {

    @SerializedName("totalResults")
    @Expose()
    private int totalCount;

    @SerializedName("results")
    @Expose()
    private List<MovieModel> movies;

    public int getTotalCount() {
        return totalCount;
    }

    public List<MovieModel> getMovies() {
        return movies;
    }

    @Override
    public String toString() {
        return "MovieSearchResponse{" +
                "totalCount=" + totalCount +
                ", movies=" + movies +
                '}';
    }
}
