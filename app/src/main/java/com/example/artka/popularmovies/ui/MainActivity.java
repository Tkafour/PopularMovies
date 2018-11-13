package com.example.artka.popularmovies.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.artka.popularmovies.R;
import com.example.artka.popularmovies.util.Utility;
import com.example.artka.popularmovies.ui.moviedetail.MovieDetailFragment;
import com.example.artka.popularmovies.ui.movielist.MovieFragmentList;


public class MainActivity extends AppCompatActivity implements MovieDetailFragment.Callbacks {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masterdetail);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = new MovieFragmentList();
            fm.beginTransaction().add(R.id.fragment_container, fragment).addToBackStack(null).commit();
        }

        Utility.scheduleJob(this);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() <= 1) {
            supportFinishAfterTransition();
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }

    public void onFavouriteUpdated() {
        MovieFragmentList movieFragmentList = (MovieFragmentList) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        movieFragmentList.refreshFavouriteList();
    }
}
