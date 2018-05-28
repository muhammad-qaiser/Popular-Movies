package com.example.sami.popularmovies;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.sami.popularmovies.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.iv_cover) ImageView m_iv_coverImage;
    @BindView(R.id.iv_poster) ImageView m_iv_posterImage;
    @BindView(R.id.tv_title) TextView m_tv_title;
    @BindView(R.id.tv_language) TextView m_tv_language;
    @BindView(R.id.tv_ratings) TextView m_tv_ratings;
    @BindView(R.id.tv_voteCount) TextView m_tv_voteCount;
    @BindView(R.id.tv_overview) TextView m_tv_overview;
    @BindView(R.id.ratingBar) RatingBar m_ratingBar;
    @BindView(R.id.iv_voteCountIcon) ImageView m_iv_voteCountIcon;
    @BindView(R.id.m_tv_releaseDate) TextView m_iv_releaseDate;

    private String backdropPath = "http://image.tmdb.org/t/p/w500";
    private String posterPath= "http://image.tmdb.org/t/p/w185";
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);
        m_iv_voteCountIcon.setImageResource(R.raw.user_icon);

        Movie m = (Movie) getIntent().getSerializableExtra("movieObj");
        setDetails(m);


    }

    private void setDetails(Movie movie)
    {
        Picasso.get().load(backdropPath + movie.getmBackdropPath()).fit().into(m_iv_coverImage);
        Picasso.get().load(posterPath + movie.getmPosterPath()).fit().into(m_iv_posterImage);
        m_tv_ratings.setText(String.format(Locale.US,"%.1f",movie.getmVoteAvg()/2));
        m_ratingBar.setRating(movie.getmVoteAvg()/2);
        m_tv_title.setText(movie.getmTitle());
        m_iv_releaseDate.setText("Release Date: " + movie.getmReleaseDate());
        m_tv_language.setText("Language: " + movie.getmLanguage());
        m_tv_voteCount.setText(String.format(Locale.US,"%,d",movie.getmVoteCount()));
        m_tv_overview.setText(movie.getmOverview());

    }
}
