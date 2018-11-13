package com.example.artka.popularmovies.ui.movielist;

import com.example.artka.popularmovies.model.MovieDetailModel;
import com.example.artka.popularmovies.util.mvp.IBasePresenter;

import java.util.List;

public interface MovieListContract {
    interface View {
        void showMovies(List<MovieDetailModel> movies);
    }

    interface Presenter extends IBasePresenter<View> {
        void getMovies(String callId);
        void movieSearch(String search);
    }
}
