package com.example.sami.popularmovies.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Trailer implements Parcelable {

    @SerializedName("id")
    protected String id;

    @SerializedName("iso_639_1")
    protected String language;

    @SerializedName("iso_3166_1")
    protected String country;

    @SerializedName("key")
    protected String key;

    @SerializedName("name")
    protected String name;

    @SerializedName("site")
    protected String site;

    @SerializedName("size")
    protected int size;

    @SerializedName("type")
    protected String type;

    public String getLanguage() {
        return language;
    }

    public String getCountry() {
        return country;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Trailer(Parcel source) {
        id = source.readString();
        language = source.readString();
        country = source.readString();
        key = source.readString();
        name = source.readString();
        site = source.readString();
        size = source.readInt();
        type = source.readString();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(id);
        dest.writeString(language);
        dest.writeString(country);
        dest.writeString(key);
        dest.writeString(name);
        dest.writeString(site);
        dest.writeInt(size);
        dest.writeString(type);
    }

    public static final Parcelable.Creator<Trailer> CREATOR =
            new Creator<Trailer>() {
                @Override
                public Trailer createFromParcel(Parcel source) {
                    return new Trailer(source);
                }

                @Override
                public Trailer[] newArray(int size) {
                    return new Trailer[size];
                }
            };
}
