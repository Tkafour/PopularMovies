package com.example.artka.popularmovies;

import android.app.Application;

public class App extends Application {

    private SharedPrefManager sharedPrefManager;

    @Override
    public void onCreate() {
        super.onCreate();
        sharedPrefManager = new SharedPrefManager(this);
    }

    public SharedPrefManager getSharedPrefManager() {
        return sharedPrefManager;
    }

}