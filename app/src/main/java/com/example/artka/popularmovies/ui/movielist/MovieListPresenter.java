package com.example.artka.popularmovies.ui.movielist;

import android.util.Log;

import com.example.artka.popularmovies.BuildConfig;
import com.example.artka.popularmovies.retrofit.MovieApi;
import com.example.artka.popularmovies.model.MovieListModel;
import com.example.artka.popularmovies.retrofit.RetrofitInstance;
import com.example.artka.popularmovies.util.mvp.BasePresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieListPresenter extends BasePresenter<MovieListContract.View>implements MovieListContract.Presenter {

    private MovieApi movieApi;
    private String API_KEY = BuildConfig.ApiKey;

    public MovieListPresenter(MovieListContract.View view) {
        this.view = view;
    }

    @Override
    public void getMovies(String callId) {
        movieApi = RetrofitInstance.getRetrofitInstance().create(MovieApi.class);
        movieApi.getPopularMovieList(callId, API_KEY).enqueue(new Callback<MovieListModel>() {
            @Override
            public void onResponse(Call<MovieListModel> call, Response<MovieListModel> response) {
                if (response.code() == 200) {
                    view.showMovies(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<MovieListModel> call, Throwable t) {
                Log.e("TAG", "Failed to get movies");
            }
        });
    }

    @Override
    public void movieSearch(String search) {
            movieApi = RetrofitInstance.getRetrofitInstance().create(MovieApi.class);
            movieApi.getMovieSearch(API_KEY, search).enqueue(new Callback<MovieListModel>() {
                @Override
                public void onResponse(Call<MovieListModel> call, Response<MovieListModel> response) {
                    if (response.code() == 200) {
                        view.showMovies(response.body().getResults());
                    }
                }

                @Override
                public void onFailure(Call<MovieListModel> call, Throwable t) {
                    Log.e("TAG", "Failed to get movies");
                }
            });
    }

}
