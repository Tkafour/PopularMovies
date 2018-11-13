package com.example.artka.popularmovies.util.mvp;

public interface IBasePresenter<ViewT> {
    void onViewActive(ViewT view);

    void onViewInactive();
}
