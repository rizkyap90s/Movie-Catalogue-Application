package com.example.afinal.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Search implements Parcelable {

    @SerializedName("id")
    private String search_id;

    @SerializedName("original_title")
    private String search_title;

    @SerializedName("overview")
    private String search_overview;

    @SerializedName("vote_average")
    private Double search_rating;

    @SerializedName("poster_path")
    private String search_poster;

    @SerializedName("backdrop_path")
    private String search_backdrop;

    @SerializedName("original_language")
    private String search_language;

    public Search() {
    }

    protected Search(Parcel in) {
        search_id = in.readString();
        search_title = in.readString();
        search_overview = in.readString();
        if (in.readByte() == 0) {
            search_rating = null;
        } else {
            search_rating = in.readDouble();
        }
        search_poster = in.readString();
        search_backdrop = in.readString();
        search_language = in.readString();
    }

    public static final Creator<Search> CREATOR = new Creator<Search>() {
        @Override
        public Search createFromParcel(Parcel in) {
            return new Search(in);
        }

        @Override
        public Search[] newArray(int size) {
            return new Search[size];
        }
    };

    public String getSearch_id() {
        return search_id;
    }

    public void setSearch_id(String search_id) {
        this.search_id = search_id;
    }

    public String getSearch_title() {
        return search_title;
    }

    public void setSearch_title(String search_title) {
        this.search_title = search_title;
    }

    public String getSearch_overview() {
        return search_overview;
    }

    public void setSearch_overview(String search_overview) {
        this.search_overview = search_overview;
    }

    public Double getSearch_rating() {
        return search_rating;
    }

    public void setSearch_rating(Double search_rating) {
        this.search_rating = search_rating;
    }

    public String getSearch_poster() {
        return search_poster;
    }

    public void setSearch_poster(String search_poster) {
        this.search_poster = search_poster;
    }

    public String getSearch_backdrop() {
        return search_backdrop;
    }

    public void setSearch_backdrop(String search_backdrop) {
        this.search_backdrop = search_backdrop;
    }

    public String getSearch_language() {
        return search_language;
    }

    public void setSearch_language(String search_language) {
        this.search_language = search_language;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(search_id);
        dest.writeString(search_title);
        dest.writeString(search_overview);
        if (search_rating == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(search_rating);
        }
        dest.writeString(search_poster);
        dest.writeString(search_backdrop);
        dest.writeString(search_language);
    }
}
