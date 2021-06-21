package com.example.afinal.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Movie implements Parcelable {

    @SerializedName("id")
    private String movie_id;

    @SerializedName("original_title")
    private String movie_title;

    @SerializedName("overview")
    private String movie_overview;

    @SerializedName("vote_average")
    private Double movie_rating;

    @SerializedName("poster_path")
    private String movie_poster;

    @SerializedName("backdrop_path")
    private String movie_backdrop;

    @SerializedName("original_language")
    private String movie_language;

    @SerializedName("release_date")
    private String movie_release;

    public Movie(Parcel in) {
        movie_id = in.readString();
        movie_title = in.readString();
        movie_overview = in.readString();
        if (in.readByte() == 0) {
            movie_rating = null;
        } else {
            movie_rating = in.readDouble();
        }
        movie_poster = in.readString();
        movie_backdrop = in.readString();
        movie_language = in.readString();
        movie_release = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public Movie() {
    }

    public String getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(String movie_id) {
        this.movie_id = movie_id;
    }

    public String getMovie_title() {
        return movie_title;
    }

    public void setMovie_title(String movie_title) {
        this.movie_title = movie_title;
    }

    public String getMovie_overview() {
        return movie_overview;
    }

    public void setMovie_overview(String movie_overview) {
        this.movie_overview = movie_overview;
    }

    public Double getMovie_rating() {
        return movie_rating;
    }

    public void setMovie_rating(Double movie_rating) {
        this.movie_rating = movie_rating;
    }

    public String getMovie_poster() {
        return movie_poster;
    }

    public void setMovie_poster(String movie_poster) {
        this.movie_poster = movie_poster;
    }

    public String getMovie_backdrop() {
        return movie_backdrop;
    }

    public void setMovie_backdrop(String movie_backdrop) {
        this.movie_backdrop = movie_backdrop;
    }

    public String getMovie_language() {
        return movie_language;
    }

    public void setMovie_language(String movie_language) {
        this.movie_language = movie_language;
    }

    public String getMovie_release() {
        return movie_release;
    }

    public void setMovie_release(String movie_release) {
        this.movie_release = movie_release;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(movie_id);
        dest.writeString(movie_title);
        dest.writeString(movie_overview);
        if (movie_rating == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(movie_rating);
        }
        dest.writeString(movie_poster);
        dest.writeString(movie_backdrop);
        dest.writeString(movie_language);
        dest.writeString(movie_release);
    }
}
