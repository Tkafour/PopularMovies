package com.example.artka.popularmovies.ui.moviedetail;

import com.example.artka.popularmovies.util.mvp.IBasePresenter;

public interface MovieDetailContract {
    interface View {
        void showReviews(String reviews);
    }

    interface Presenter extends IBasePresenter<View> {
        void getReviews(String movieId);
    }
}
