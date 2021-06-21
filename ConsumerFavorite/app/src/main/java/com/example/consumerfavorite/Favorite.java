package com.example.consumerfavorite;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import static com.example.consumerfavorite.DataConstract.DbColumns.OVERVIEW;
import static com.example.consumerfavorite.DataConstract.DbColumns.POSTER;
import static com.example.consumerfavorite.DataConstract.DbColumns.RATE;
import static com.example.consumerfavorite.DataConstract.DbColumns.TITLE;
import static com.example.consumerfavorite.DataConstract.getColumnDouble;
import static com.example.consumerfavorite.DataConstract.getColumnString;

public class Favorite implements Parcelable {


    private String fav_id;
    private String fav_title;
    private String fav_overview;
    private Double fav_rating;
    private String fav_poster;
    private String fav_backdrop;
    private String fav_language;

    public String getFav_id() {
        return fav_id;
    }

    public void setFav_id(String fav_id) {
        this.fav_id = fav_id;
    }

    public String getFav_title() {
        return fav_title;
    }

    public void setFav_title(String fav_title) {
        this.fav_title = fav_title;
    }

    public String getFav_overview() {
        return fav_overview;
    }

    public void setFav_overview(String fav_overview) {
        this.fav_overview = fav_overview;
    }

    public Double getFav_rating() {
        return fav_rating;
    }

    public void setFav_rating(Double fav_rating) {
        this.fav_rating = fav_rating;
    }

    public String getFav_poster() {
        return fav_poster;
    }

    public void setFav_poster(String fav_poster) {
        this.fav_poster = fav_poster;
    }

    public String getFav_backdrop() {
        return fav_backdrop;
    }

    public void setFav_backdrop(String fav_backdrop) {
        this.fav_backdrop = fav_backdrop;
    }

    public String getFav_language() {
        return fav_language;
    }

    public void setFav_language(String fav_language) {
        this.fav_language = fav_language;
    }


    protected Favorite(Parcel in) {
        fav_id = in.readString();
        fav_title = in.readString();
        fav_overview = in.readString();
        if (in.readByte() == 0) {
            fav_rating = null;
        } else {
            fav_rating = in.readDouble();
        }
        fav_poster = in.readString();
        fav_backdrop = in.readString();
        fav_language = in.readString();
    }

    public static final Creator<Favorite> CREATOR = new Creator<Favorite>() {
        @Override
        public Favorite createFromParcel(Parcel in) {
            return new Favorite(in);
        }

        @Override
        public Favorite[] newArray(int size) {
            return new Favorite[size];
        }
    };

    public Favorite(Cursor cursor) {
        this.fav_id = getColumnString(cursor, DataConstract.DbColumns.ID);
        this.fav_title = getColumnString(cursor, TITLE);
        this.fav_rating = getColumnDouble(cursor, RATE);
        this.fav_poster = getColumnString(cursor, POSTER);
        this.fav_overview = getColumnString(cursor, OVERVIEW);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fav_id);
        dest.writeString(fav_title);
        dest.writeString(fav_overview);
        if (fav_rating == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(fav_rating);
        }
        dest.writeString(fav_poster);
        dest.writeString(fav_backdrop);
        dest.writeString(fav_language);
    }
}
