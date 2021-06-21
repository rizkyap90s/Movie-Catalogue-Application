package com.example.afinal.db;

import android.net.Uri;
import android.provider.BaseColumns;

import static com.example.afinal.db.DataConstract.DbColumns.TABLE_NAME_MOVIE;
import static com.example.afinal.db.DataConstract.DbColumns.TABLE_NAME_TV;

public class DataConstract {

    public static final class DbColumns implements BaseColumns {
        public static final String TABLE_NAME_MOVIE = "favoriteMovie";
        public static final String TABLE_NAME_TV = "favoriteTVShow";
        static final String ID ="id";
        static final String TITLE = "title";
        static final String OVERVIEW = "overview";
        static final String RATE = "rate";
        static final String POSTER = "poster";
        static final String LANGUAGE = "language";
        static final String BACKDROP = "backdrop";

    }

    public static final String AUTHORITY = "com.example.afinal";

    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME_MOVIE)
            .appendPath(TABLE_NAME_TV)
            .build();


}
