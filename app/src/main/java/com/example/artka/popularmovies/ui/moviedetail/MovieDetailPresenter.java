package com.example.artka.popularmovies.ui.moviedetail;

import android.util.Log;

import com.example.artka.popularmovies.BuildConfig;
import com.example.artka.popularmovies.retrofit.MovieApi;
import com.example.artka.popularmovies.retrofit.RetrofitInstance;
import com.example.artka.popularmovies.model.ReviewListModel;
import com.example.artka.popularmovies.model.ReviewModel;
import com.example.artka.popularmovies.util.mvp.BasePresenter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailPresenter extends BasePresenter<MovieDetailContract.View> implements MovieDetailContract.Presenter {

    private MovieApi movieApi;
    private String API_KEY = BuildConfig.ApiKey;

    public MovieDetailPresenter(MovieDetailContract.View view) {
        this.view = view;
    }

    @Override
    public void getReviews(String movieId) {

        movieApi = RetrofitInstance.getRetrofitInstance().create(MovieApi.class);
        movieApi.getMovieReviews(movieId, API_KEY).enqueue(new Callback<ReviewListModel>() {
            @Override
            public void onResponse(Call<ReviewListModel> call, Response<ReviewListModel> response) {
                List<ReviewModel> review = response.body().getResults();
                StringBuilder stringBuilder = new StringBuilder();
                for (ReviewModel reviewModel : review) {
                    stringBuilder.append(reviewModel.getAuthor());
                    stringBuilder.append(System.getProperty("line.separator"));
                    stringBuilder.append(reviewModel.getContent());
                }
                view.showReviews(stringBuilder.toString());
            }

            @Override
            public void onFailure(Call<ReviewListModel> call, Throwable t) {
                Log.e("MovieDetail", "Operation Failed", t);
            }
        });
    }
}
