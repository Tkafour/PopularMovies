package com.example.artka.popularmovies.ui.moviedetail;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.artka.popularmovies.App;
import com.example.artka.popularmovies.R;
import com.example.artka.popularmovies.model.MovieDetailModel;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailFragment extends Fragment implements MovieDetailContract.View {

    private final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w500";

    @BindView(R.id.fragment_movie_image)
    ImageView moviePoster;
    @BindView(R.id.fragment_movie_title)
    TextView movieTitle;
    @BindView(R.id.fragment_movie_description)
    TextView movieOverview;
    @BindView(R.id.fragment_movie_votes_text)
    TextView movieRating;
    @BindView(R.id.fragment_release_date)
    TextView movieReleaseDate;
    @BindView(R.id.movie_review_text)
    TextView reviews;
    @BindView(R.id.fragment_review_button)
    Button reviewButton;

    private Callbacks callbacks;
    private MovieDetailPresenter movieDetailPresenter;
    private MovieDetailModel movie;


    public interface Callbacks {
        void onFavouriteUpdated();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_fragment_detail, menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getFragmentManager().popBackStack();
                return true;
            case R.id.add_to_favorite:
                ((App) getActivity().getApplication()).getSharedPrefManager().addFavouriteMovie(movie);
                return true;
            case R.id.remove_favorite:
                ((App) getActivity().getApplication()).getSharedPrefManager().removeFavouriteMovie(movie);
                callbacks.onFavouriteUpdated();
                getFragmentManager().popBackStack();
                return true;
            default:
                break;
        }
        return false;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        callbacks = (Callbacks) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.movie_fragment, container, false);

        ButterKnife.bind(this, v);

        movieDetailPresenter = new MovieDetailPresenter(this);

        setMovieInfo();

        reviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reviews.setVisibility(View.GONE);
            }
        });

        reviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movieDetailPresenter.getReviews(movie.getId().toString());
                reviews.setVisibility(View.VISIBLE);
            }
        });

        return v;
    }

    private void setMovieInfo() {
        movie = new MovieDetailModel();
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            Gson gson = new Gson();
            String movieString = bundle.getString("TAG");
            movie = gson.fromJson(movieString, MovieDetailModel.class);

            movieTitle.setText(movie.getTitle());
            movieOverview.setText(movie.getOverview());
            movieRating.setText(movie.getVoteCount().toString());
            movieReleaseDate.setText(movie.getReleaseDate());
            Picasso.get().load(IMAGE_BASE_URL + movie.getPosterPath()).into(moviePoster);
        }
    }

    @Override
    public void showReviews(String review) {
        reviews.setText(review);
    }
}
