package com.example.sami.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Review implements Parcelable {

    @SerializedName("author")
    protected String mAuthor;

    @SerializedName("content")
    protected String mReview;

    @SerializedName("id")
    protected String mId;

    public String getAuthor(){ return mAuthor;}
    public String getReview() {return mReview;}
    public String getReviewId() { return mId;}

    public void setmAuthor(String mAuthor) { this.mAuthor = mAuthor; }
    public void setmReview(String mReview) { this.mReview = mReview; }
    public void setmId(String mId) { this.mId = mId; }

    protected Review(Parcel in) {
        mAuthor = in.readString();
        mReview = in.readString();
        mId = in.readString();
    }

    public static final Creator<Review> CREATOR = new Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mAuthor);
        dest.writeString(mReview);
        dest.writeString(mId);
    }
}
