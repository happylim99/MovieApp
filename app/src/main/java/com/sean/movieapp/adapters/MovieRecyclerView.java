package com.sean.movieapp.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sean.movieapp.models.MovieModel;

import java.util.List;

import com.sean.movieapp.R;

public class MovieRecyclerView extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "MovieRecyclerView";
    private List<MovieModel> movies;
    private OnMovieListener onMovieListener;

    public MovieRecyclerView() {
    }

    public MovieRecyclerView(OnMovieListener onMovieListener) {
        this.onMovieListener = onMovieListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item,
                parent, false);
        return new MovieViewHolder(view, onMovieListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        ((MovieViewHolder)holder).title.setText(movies.get(i).getTitle());
        ((MovieViewHolder)holder).release_date.setText(movies.get(i).getRelease_date());
//        ((MovieViewHolder)holder).runtime.setText(movies.get(i).getRuntime());
        ((MovieViewHolder)holder).ratingBar.setRating(movies.get(i).getVote_average()/2);

        Glide.with(holder.itemView.getContext())
                .load(movies.get(i).getPoster_path())
                .into(((MovieViewHolder)holder).imageView);
    }

    @Override
    public int getItemCount() {
        if(movies != null) {
            return movies.size();
        }
        return 0;
    }

    public void setMovies(List<MovieModel> movies) {
        Log.i(TAG, "setMovies: " + movies.get(0).getTitle());
        this.movies = movies;
        notifyDataSetChanged();
    }
}
