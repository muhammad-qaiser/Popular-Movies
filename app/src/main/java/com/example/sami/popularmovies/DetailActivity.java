package com.example.sami.popularmovies;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
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

    //test
    @BindView(R.id.ibFavourite) ImageButton m_ib_favourite;

    private String backdropPath = "http://image.tmdb.org/t/p/w500";
    private String posterPath= "http://image.tmdb.org/t/p/w185";
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);
        m_iv_voteCountIcon.setImageResource(R.raw.user_icon);
        Movie m = getIntent().getParcelableExtra("movieObj");
        setDetails(m);
    }

    private void setDetails(Movie movie)
    {
        Picasso.get().load(backdropPath + movie.mBackdropPath).fit().into(m_iv_coverImage);
        Picasso.get().load(posterPath + movie.mPosterPath).fit().into(m_iv_posterImage);
        m_tv_ratings.setText(String.format(Locale.US,"%.1f",movie.mVoteAvg/2));
        m_ratingBar.setRating(movie.mVoteAvg/2);
        m_tv_title.setText(movie.mTitle);
        m_iv_releaseDate.setText("Release Date: " + movie.mReleaseDate);
        m_tv_language.setText("Language: " + movie.mLanguage);
        m_tv_voteCount.setText(String.format(Locale.US,"%,d",movie.mVoteCount));
        m_tv_overview.setText(movie.mOverview);

        initFavourite();
    }

    public void checkFavourite(View view) {
        boolean isFav = readState();
        if (isFav) {
            m_ib_favourite.setBackground(getDrawable(android.R.drawable.btn_star_big_off));
            isFav = false;
            saveState(isFav);

        } else {
            m_ib_favourite.setBackground(getDrawable(android.R.drawable.btn_star_big_on));
            isFav = true;
            saveState(isFav);
        }
    }

    void initFavourite()
    {
        boolean isFav = readState();
        if (isFav) {
            m_ib_favourite.setBackground(getDrawable(android.R.drawable.btn_star_big_on));

        } else {
            m_ib_favourite.setBackground(getDrawable(android.R.drawable.btn_star_big_off));
        }
    }
    private void saveState(boolean isFavourite) {
        SharedPreferences aSharedPreferences = this.getSharedPreferences(
                "Favourite", Context.MODE_PRIVATE);
        SharedPreferences.Editor aSharedPreferencesEdit = aSharedPreferences
                .edit();
        aSharedPreferencesEdit.putBoolean("State", isFavourite);
        aSharedPreferencesEdit.commit();
    }

    private boolean readState() {
        SharedPreferences aSharedPreferences = this.getSharedPreferences(
                "Favourite", Context.MODE_PRIVATE);
        return aSharedPreferences.getBoolean("State", true);
    }

}
