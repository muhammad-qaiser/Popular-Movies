package com.example.sami.popularmovies;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sami.popularmovies.Adapter.ReviewsAdapter;
import com.example.sami.popularmovies.Adapter.TrailersAdapter;
import com.example.sami.popularmovies.Database.AppDatabase;
import com.example.sami.popularmovies.model.Movie;
import com.example.sami.popularmovies.model.Review;
import com.example.sami.popularmovies.model.ReviewsList;
import com.example.sami.popularmovies.model.Trailer;
import com.example.sami.popularmovies.model.TrailersList;
import com.example.sami.popularmovies.services.AppExecutors;
import com.example.sami.popularmovies.utils.ListItemClickListener;
import com.example.sami.popularmovies.utils.MoviesClient;
import com.example.sami.popularmovies.utils.RetrofitClient;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity implements ListItemClickListener {

    @BindView(R.id.rv_trailer) RecyclerView m_rv_trailer;
    @BindView(R.id.rv_review) RecyclerView m_rv_review;
    @BindView(R.id.iv_cover) ImageView m_iv_coverImage;
    @BindView(R.id.iv_poster) ImageView m_iv_posterImage;
    @BindView(R.id.tv_title) TextView m_tv_title;
    @BindView(R.id.tv_language) TextView m_tv_language;
    @BindView(R.id.tv_ratings) TextView m_tv_ratings;
    @BindView(R.id.tv_voteCount) TextView m_tv_voteCount;
    @BindView(R.id.tv_overview) TextView m_tv_overview;
    @BindView(R.id.ratingBar) RatingBar m_ratingBar;
    @BindView(R.id.iv_voteCountIcon) ImageView m_iv_voteCountIcon;
    @BindView(R.id.tv_releaseDate) TextView m_iv_releaseDate;
    @BindView(R.id.tv_trailer) TextView m_tv_trailer;
    @BindView(R.id.tv_review) TextView m_tv_review;


    //test
    @BindView(R.id.ib_favorite) ImageButton m_ib_favorite;

    TrailersAdapter mTrailerAdapter;
    ArrayList<Trailer> mTrailerList;
    ReviewsAdapter mReviewAdapter;
    ArrayList<Review> mReviewList;
    private final int REVIEW = 1;
    private final int TRAILER = 2;
    private Movie mMovie;
    private AppDatabase mDatabase;
    private AppExecutors mAppExecutors;
    private boolean mIsFavorite;
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);
        mAppExecutors = AppExecutors.getInstance();
        mDatabase = AppDatabase.getInstance(getApplicationContext());

        LinearLayoutManager trailerLayoutManager = new LinearLayoutManager(this );
        m_rv_trailer.setLayoutManager(trailerLayoutManager);
        m_rv_trailer.setHasFixedSize(true);

        LinearLayoutManager reviewsLayoutManager = new LinearLayoutManager(this );
        m_rv_review.setLayoutManager(reviewsLayoutManager);
        m_rv_review.setHasFixedSize(true);

        m_iv_voteCountIcon.setImageResource(R.raw.user_icon);
        mMovie = getIntent().getParcelableExtra("movieObj");
        mIsFavorite = getIntent().getBooleanExtra("isFavorite",false);

        setDetails();
    }


    private void setDetails()
    {
        String backdropPath = "http://image.tmdb.org/t/p/w500";
        String posterPath= "http://image.tmdb.org/t/p/w185";
        Picasso.get().load(backdropPath + mMovie.mBackdropPath).fit().into(m_iv_coverImage);
        Picasso.get().load(posterPath + mMovie.mPosterPath).fit().into(m_iv_posterImage);
        m_tv_ratings.setText(String.format(Locale.US,"%.1f",mMovie.mVoteAvg/2));
        m_ratingBar.setRating(mMovie.mVoteAvg/2);
        m_tv_title.setText(mMovie.mTitle);
        m_iv_releaseDate.setText("Release Date: " + mMovie.mReleaseDate);
        m_tv_language.setText("Language: " + mMovie.mLanguage);
        m_tv_voteCount.setText(String.format(Locale.US,"%,d",mMovie.mVoteCount));
        m_tv_overview.setText(mMovie.mOverview);
        loadTrailer(mMovie.mId);
        loadReviews(mMovie.mId);
        initFavorite(mIsFavorite);
    }

    protected void displayTitle(RecyclerView rv , int type)
    {
        RecyclerView.Adapter adapter = rv.getAdapter();
        if(adapter.getItemCount() == 0)
        {
            if(type == REVIEW)
                m_tv_review.setText("No Review Yet..");
            else
                m_tv_trailer.setText("No Trailer Yet");
        }
        else
        {
            if(type == REVIEW)
                m_tv_review.setText("Reviews");
            else
                m_tv_trailer.setText("Trailer");
        }
    }
    private void loadReviews(int id)
    {
        MoviesClient client = new RetrofitClient().getClient().create(MoviesClient.class);
        Call<ReviewsList> reviewsListCall= client.reviews(id, BuildConfig.MOVIEDB_API_KEY);
        reviewsListCall.enqueue(new Callback<ReviewsList>() {
            @Override
            public void onResponse(Call<ReviewsList> call, Response<ReviewsList> response) {
                mReviewList = response.body().getReviews();
                mReviewAdapter = new ReviewsAdapter(mReviewList);
                m_rv_review.setAdapter(mReviewAdapter);
                displayTitle(m_rv_review,REVIEW);
            }

            @Override
            public void onFailure(Call<ReviewsList> call, Throwable t) {
                Toast.makeText(DetailActivity.this, "Error Loading Trailer", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void loadTrailer(int id)
    {
        MoviesClient client = new RetrofitClient().getClient().create(MoviesClient.class);
        Call<TrailersList> trailerCall = client.trailers(id, BuildConfig.MOVIEDB_API_KEY);
        trailerCall.enqueue(new Callback<TrailersList>() {
            @Override
            public void onResponse(Call<TrailersList> call, Response<TrailersList> response) {
                mTrailerList = response.body().getTrailers();
                mTrailerAdapter = new TrailersAdapter(mTrailerList , DetailActivity.this);
                m_rv_trailer.setAdapter(mTrailerAdapter);
                displayTitle(m_rv_trailer,TRAILER);
            }

            @Override
            public void onFailure(Call<TrailersList> call, Throwable t) {
                Toast.makeText(DetailActivity.this, "Error Loading Trailer", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void toggleFavorite(View view) {
        initFavorite(!mIsFavorite);
        mAppExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if(mIsFavorite) {
                    mDatabase.movieDao().deleteMovie(mMovie);
                }
                else {
                    mDatabase.movieDao().insertMovie(mMovie);
                }
                mIsFavorite = !mIsFavorite;
            }
        });
    }

    void initFavorite( boolean fav)
    {
        if (fav) {
            m_ib_favorite.setBackground(getDrawable(android.R.drawable.btn_star_big_on));

        } else {
            m_ib_favorite.setBackground(getDrawable(android.R.drawable.btn_star_big_off));
        }
    }
    @Override
    public void onListItemClick(int clickedItemIndex) {
        Trailer trailer = mTrailerList.get(clickedItemIndex);
        Uri webPage = Uri.parse("http://www.youtube.com/watch?v=" + trailer.getKey());
        Intent intent = new Intent(Intent.ACTION_VIEW, webPage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @Override
    public void onLongClick(int clickedItemIndex) {
        Trailer trailer = mTrailerList.get(clickedItemIndex);
        Intent i=new Intent(android.content.Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(android.content.Intent.EXTRA_SUBJECT,trailer.getName());
        i.putExtra(android.content.Intent.EXTRA_TEXT, "http://www.youtube.com/watch?v=" + trailer.getKey());
        startActivity(Intent.createChooser(i,"Share via"));
    }
}
