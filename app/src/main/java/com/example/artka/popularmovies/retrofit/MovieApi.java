package com.example.artka.popularmovies.retrofit;


import com.example.artka.popularmovies.model.MovieListModel;
import com.example.artka.popularmovies.model.ReviewListModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApi {

    @GET("/3/movie/{movie_type}")
    Call<MovieListModel> getPopularMovieList(@Path("movie_type") String movieType, @Query("api_key") String apiKey);

    @GET("3/search/movie?")
    Call<MovieListModel> getMovieSearch(@Query("api_key") String apiKey, @Query("query") String movieName);

    @GET("3/movie/{movie_id}/reviews??")
    Call<ReviewListModel> getMovieReviews(@Path("movie_id") String movieId, @Query("api_key") String apiKey);

}
