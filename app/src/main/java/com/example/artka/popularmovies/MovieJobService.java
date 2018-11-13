package com.example.artka.popularmovies;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

import com.example.artka.popularmovies.retrofit.MovieApi;
import com.example.artka.popularmovies.model.MovieListModel;
import com.example.artka.popularmovies.retrofit.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieJobService extends JobService {

    private static final String POPULAR_CALL = "popular";

    @Override
    public boolean onStartJob(final JobParameters params) {
        MovieApi movieApi = RetrofitInstance.getRetrofitInstance().create(MovieApi.class);
        movieApi.getPopularMovieList(POPULAR_CALL, getString(R.string.API_KEY)).enqueue(new Callback<MovieListModel>() {
            @Override
            public void onResponse(Call<MovieListModel> call, Response<MovieListModel> response) {
                if (response.code() == 200) {
                    ((App) MovieJobService.this.getApplication()).getSharedPrefManager().updatePopularMovies(response.body().getResults());
                    jobFinished(params, true);
                    Log.e("SERVICE", "Service Loading");
                }
            }
            @Override
            public void onFailure(Call<MovieListModel> call, Throwable t) {
                Log.e("SERVICE", "Service failed to load");
            }
        });
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
