package com.example.sami.popularmovies.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "movie")
public class Movie implements Parcelable {

    public Movie(){}

    @SerializedName("vote_count")
    public int mVoteCount;

    @PrimaryKey
    @SerializedName("id")
    public int mId;

    @SerializedName("video")
    public boolean mHaveVideo;

    @SerializedName("vote_average")
    public float mVoteAvg;

    @SerializedName("title")
    public String mTitle;

    @SerializedName("popularity")
    public float mPopularity;

    @SerializedName("poster_path")
    public String mPosterPath;

    @SerializedName("original_language")
    public String mLanguage;

    @SerializedName("backdrop_path")
    public String mBackdropPath;

    @SerializedName("overview")
    public String mOverview;

    @SerializedName("release_date")
    public String mReleaseDate;

    private Movie(Parcel source) {
        mVoteCount = source.readInt();
        mId = source.readInt();
        mHaveVideo = source.readByte()!= 0;
        mVoteAvg = source.readFloat();
        mTitle = source.readString();
        mPopularity = source.readFloat();
        mPosterPath = source.readString();
        mLanguage = source.readString();
        mBackdropPath = source.readString();
        mOverview = source.readString();
        mReleaseDate = source.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(mVoteCount);
        dest.writeInt(mId);
        dest.writeByte((byte) (mHaveVideo ? 1 : 0));
        dest.writeFloat(mVoteAvg);
        dest.writeString(mTitle);
        dest.writeFloat(mPopularity);
        dest.writeString(mPosterPath);
        dest.writeString(mLanguage);
        dest.writeString(mBackdropPath);
        dest.writeString(mOverview);
        dest.writeString(mReleaseDate);
    }

    public static final Parcelable.Creator<Movie> CREATOR =
            new Creator<Movie>() {
                @Override
                public Movie createFromParcel(Parcel source) {
                    return new Movie(source);
                }

                @Override
                public Movie[] newArray(int size) {
                    return new Movie[size];
                }
            };
}
