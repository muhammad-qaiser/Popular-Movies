package com.example.sami.popularmovies.model;

import java.io.Serializable;

public class Movie implements Serializable {

    private int mVoteCount;
    private int mId;
    private boolean mHaveVideo;
    private float mVoteAvg;
    private String mTitle;
    private Long mPopularity;
    private String mPosterPath;
    private String mLanguage;
    private String mBackdropPath;
    private String mOverview;
    private String mReleaseDate;

    public Movie()
    {}

    public Movie(int voteCount, int id, boolean haveVideo, Long avgVote,
                 String title, Long popularity, String posterPath,
                 String language, String backdropPath, String overview,
                 String releaseDate)
    {
        this.mVoteCount = voteCount;
        this.mId = id;
        this.mHaveVideo = haveVideo;
        this.mVoteAvg = avgVote;
        this.mTitle = title;
        this.mPopularity = popularity;
        this.mPosterPath = posterPath;
        this.mLanguage = language;
        this.mBackdropPath = backdropPath;
        this.mOverview = overview;
        this.mReleaseDate = releaseDate;

    }

    public void setmVoteCount(int voteCount) { this.mVoteCount = voteCount; }
    public int getmVoteCount(){ return this.mVoteCount; }

    public void setmId(int id){ this.mId = id; }
    public int getmId(){return  this.mId;}

    public void setmHaveVideo(boolean haveVideo){ this.mHaveVideo = haveVideo; }
    public boolean getmHaveVideo(){ return this.mHaveVideo;}

    public void setmVoteAvg(float avgVote){ this.mVoteAvg = avgVote;}
    public float getmVoteAvg(){return this.mVoteAvg; }

    public void setmTitle(String title){ this.mTitle = title;}
    public String getmTitle(){ return this.mTitle;}

    public void setmPopularity(long popularity){ this.mPopularity = popularity;}
    public long getmPopularity(){ return this.mPopularity;}

    public void setmPosterPath(String posterPath){ this.mPosterPath = posterPath;}
    public String getmPosterPath(){return this.mPosterPath;}

    public void setmLanguage(String language){this.mLanguage = language;}
    public String getmLanguage(){return this.mLanguage;}

    public void setmBackdropPath(String backdropPath){this.mBackdropPath = backdropPath;}
    public String getmBackdropPath(){return this.mBackdropPath;}

    public void setmOverview(String overview){this.mOverview = overview; }
    public String getmOverview(){return this.mOverview;}

    public void setmReleaseDate(String releaseDate){this.mReleaseDate = releaseDate;}
    public String getmReleaseDate(){return this.mReleaseDate;}
}
