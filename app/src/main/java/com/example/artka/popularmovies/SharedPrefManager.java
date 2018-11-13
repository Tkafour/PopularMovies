package com.example.artka.popularmovies;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.artka.popularmovies.model.MovieDetailModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SharedPrefManager {

    private static final String SHARED_PREF_NAME = "PopularMoviesSharedPref";
    private final String FAVOURITE_MOVIES = "favourites";

    private SharedPreferences sharedPreferences;
    private Gson gson = new Gson();

    private List<OnPopularMoviesUpdate> onPopularMoviesUpdateList = new ArrayList<>();

    public SharedPrefManager(Context context) {
        Context applicationContext = context.getApplicationContext();
        sharedPreferences = applicationContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    public List<MovieDetailModel> getFavouriteMovie() {
        if (sharedPreferences.contains(FAVOURITE_MOVIES)) {
            String json = sharedPreferences.getString(FAVOURITE_MOVIES, null);
            MovieDetailModel[] favourites = gson.fromJson(json, MovieDetailModel[].class);
            return new ArrayList<>(Arrays.asList(favourites));
        } else {
            return null;
        }
    }

    public void removeFavouriteMovie(MovieDetailModel movie) {
        List<MovieDetailModel> favouriteMovies = getFavouriteMovie();
        if (favouriteMovies != null) {
            for (final MovieDetailModel addedMovie : favouriteMovies) {
                if (addedMovie.getId().equals(movie.getId())) {
                    favouriteMovies.remove(addedMovie);
                    saveFavouriteMovies(favouriteMovies);
                    return;
                }
            }
        }
    }

    public void addFavouriteMovie(MovieDetailModel movie) {
        List<MovieDetailModel> favouriteMovies = getFavouriteMovie();
        if (favouriteMovies == null) {
            favouriteMovies = new ArrayList<>();
        } else {
            for (final MovieDetailModel addedMovie : favouriteMovies) {
                if (addedMovie.getId().equals(movie.getId())) {
                    return;
                }
            }
        }

        favouriteMovies.add(movie);
        saveFavouriteMovies(favouriteMovies);

    }

    public void saveFavouriteMovies(List<MovieDetailModel> favouriteMovies) {
        String json = gson.toJson(favouriteMovies);
        sharedPreferences.edit().putString(FAVOURITE_MOVIES, json).commit();
    }

    public void updatePopularMovies(List<MovieDetailModel> popularMovies) {
        for (OnPopularMoviesUpdate onPopularMoviesUpdate : onPopularMoviesUpdateList) {
            onPopularMoviesUpdate.moviesUpdated(popularMovies);
        }
    }

    public void setOnPopularMoviesUpdate(OnPopularMoviesUpdate onPopularMoviesUpdate) {
        onPopularMoviesUpdateList.add(onPopularMoviesUpdate);
    }

    public void removeOnPopularMoviesUpdate(OnPopularMoviesUpdate onPopularMoviesUpdate) {
        onPopularMoviesUpdateList.remove(onPopularMoviesUpdate);
    }

    public interface OnPopularMoviesUpdate {

        void moviesUpdated(List<MovieDetailModel> updatedMovies);

    }

}
