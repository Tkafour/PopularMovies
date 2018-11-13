package com.example.artka.popularmovies.ui.movielist;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.artka.popularmovies.R;
import com.example.artka.popularmovies.model.MovieDetailModel;
import com.example.artka.popularmovies.ui.moviedetail.MovieDetailFragment;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieHolder> {

    private List<MovieDetailModel> movies;

    private int positionClicked = -1;

    private final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w500";

    public MovieListAdapter(List<MovieDetailModel> movies) {
        this.movies = movies;
    }

    @Override
    public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_movie, null);
        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieHolder holder, int position) {
        MovieDetailModel movieDetailModel = movies.get(position);
        Picasso.get().load(IMAGE_BASE_URL + movieDetailModel.getPosterPath()).into(holder.moviePhoto);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void setItems(List<MovieDetailModel> movie) {
        movies.clear();
        movies.addAll(movie);
        notifyDataSetChanged();
    }

    class MovieHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.list_item_movie_picture)
        ImageView moviePhoto;

        public MovieHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View itemView) {
            if (positionClicked != getAdapterPosition()) {
                positionClicked = getAdapterPosition();
                AppCompatActivity activity = (AppCompatActivity) itemView.getContext();
                Fragment movieDetailFragment = new MovieDetailFragment();
                Bundle bundle = new Bundle();

                Gson gson = new Gson();
                bundle.putString("TAG", gson.toJson(movies.get(getAdapterPosition())));

                movieDetailFragment.setArguments(bundle);

                if (activity.findViewById(R.id.movie_detail_fragment) == null) {
                    activity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, movieDetailFragment)
                            .addToBackStack(null)
                            .commit();
                } else {

                    activity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.movie_detail_fragment, movieDetailFragment)
                            .addToBackStack(null)
                            .commit();
                }
            }
        }
    }
}

