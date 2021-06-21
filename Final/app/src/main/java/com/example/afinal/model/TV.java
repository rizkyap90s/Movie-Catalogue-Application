package com.example.afinal.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class TV implements Parcelable {


    @SerializedName("id")
    private String tv_id;

    @SerializedName("name")
    private String tv_title;

    @SerializedName("overview")
    private String tv_overview;

    @SerializedName("vote_average")
    private Double tv_rate;

    @SerializedName("poster_path")
    private String tv_poster;

    @SerializedName("backdrop_path")
    private String tv_backdrop;


    @SerializedName("original_language")
    private String tv_language;

    public String getTv_id() {
        return tv_id;
    }

    public void setTv_id(String tv_id) {
        this.tv_id = tv_id;
    }

    public String getTv_title() {
        return tv_title;
    }

    public void setTv_title(String tv_title) {
        this.tv_title = tv_title;
    }

    public String getTv_overview() {
        return tv_overview;
    }

    public void setTv_overview(String tv_overview) {
        this.tv_overview = tv_overview;
    }

    public Double getTv_rate() {
        return tv_rate;
    }

    public void setTv_rate(Double tv_rate) {
        this.tv_rate = tv_rate;
    }

    public String getTv_poster() {
        return tv_poster;
    }

    public void setTv_poster(String tv_poster) {
        this.tv_poster = tv_poster;
    }

    public String getTv_backdrop() {
        return tv_backdrop;
    }

    public void setTv_backdrop(String tv_backdrop) {
        this.tv_backdrop = tv_backdrop;
    }

    public String getTv_language() {
        return tv_language;
    }

    public void setTv_language(String tv_language) {
        this.tv_language = tv_language;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.tv_id);
        dest.writeString(this.tv_title);
        dest.writeString(this.tv_overview);
        dest.writeValue(this.tv_rate);
        dest.writeString(this.tv_poster);
        dest.writeString(this.tv_backdrop);
        dest.writeString(this.tv_language);
    }

    public TV() {
    }

    protected TV(Parcel in) {
        this.tv_id = in.readString();
        this.tv_title = in.readString();
        this.tv_overview = in.readString();
        this.tv_rate = (Double) in.readValue(Double.class.getClassLoader());
        this.tv_poster = in.readString();
        this.tv_backdrop = in.readString();
        this.tv_language = in.readString();
    }

    public static final Creator<TV> CREATOR = new Creator<TV>() {
        @Override
        public TV createFromParcel(Parcel source) {
            return new TV(source);
        }

        @Override
        public TV[] newArray(int size) {
            return new TV[size];
        }
    };
}
