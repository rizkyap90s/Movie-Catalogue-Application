package com.example.consumerfavorite;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

import static com.example.consumerfavorite.DataConstract.DbColumns.TABLE_NAME_MOVIE;

public class DataConstract {

    public static final class DbColumns implements BaseColumns {
        public static final String TABLE_NAME_MOVIE = "favoriteMovie";
        static final String ID ="id";
        static final String TITLE = "title";
        static final String OVERVIEW = "overview";
        static final String RATE = "rate";
        static final String POSTER = "poster";

    }

    public static final String AUTHORITY = "com.example.afinal";

    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME_MOVIE)
            .build();

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString( cursor.getColumnIndex(columnName) );
    }

    public static double getColumnDouble(Cursor cursor, String columnName) {
        return cursor.getDouble(cursor.getColumnIndex(columnName));
    }


}
