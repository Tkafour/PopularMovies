package com.example.artka.popularmovies.ui.movielist;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.artka.popularmovies.App;
import com.example.artka.popularmovies.R;
import com.example.artka.popularmovies.SharedPrefManager;
import com.example.artka.popularmovies.model.MovieDetailModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MovieFragmentList extends Fragment implements SharedPrefManager.OnPopularMoviesUpdate, MovieListContract.View {

    private static final String POPULAR_CALL = "popular";
    private static final String LIKED_CALL = "top_rated";
    private static final String RECENT_CALL = "now_playing";
    private static final String UPCOMING_CALL = "upcoming";

    @BindView(R.id.fragment_movie_recycler_view)
    RecyclerView recyclerView;

    private MovieListAdapter movieAdapter;
    private List<MovieDetailModel> movies = new ArrayList<>();
    private MovieListPresenter movieListPresenter;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.movie_fragment_list_menu, menu);
        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) search.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                movieListPresenter.movieSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.popularity_menu:
                movieListPresenter.getMovies(POPULAR_CALL);
                return true;
            case R.id.liked_menu:
                movieListPresenter.getMovies(LIKED_CALL);
                return true;
            case R.id.recent_menu:
                movieListPresenter.getMovies(RECENT_CALL);
                return true;
            case R.id.upcoming_menu:
                movieListPresenter.getMovies(UPCOMING_CALL);
                return true;
            case R.id.go_to_popular:
                refreshFavouriteList();

            default:
                break;
        }
        return false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        ((App) getActivity().getApplication()).getSharedPrefManager().setOnPopularMoviesUpdate(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        ((App) getActivity().getApplication()).getSharedPrefManager().removeOnPopularMoviesUpdate(this);
    }

    @Override
    public void moviesUpdated(final List<MovieDetailModel> updatedMovies) {
        movieAdapter.setItems(updatedMovies);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_movie_list, container, false);

        ButterKnife.bind(this, v);

        movieListPresenter = new MovieListPresenter(this);

        movieAdapter = new MovieListAdapter(movies);
        recyclerView.setAdapter(movieAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        return v;
    }

    public void refreshFavouriteList() {
        SharedPrefManager sharedPrefManager = ((App) getActivity().getApplication()).getSharedPrefManager();
        movieAdapter.setItems(sharedPrefManager.getFavouriteMovie());
    }

    @Override
    public void showMovies(List<MovieDetailModel> movies) {
        movieAdapter.setItems(movies);
    }
}
