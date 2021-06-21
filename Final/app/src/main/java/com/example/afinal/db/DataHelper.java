package com.example.afinal.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "favorite_db";
    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_TABLE_FAVORITE_MOVIES = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            DataConstract.DbColumns.TABLE_NAME_MOVIE,
            DataConstract.DbColumns._ID,
            DataConstract.DbColumns.ID,
            DataConstract.DbColumns.TITLE,
            DataConstract.DbColumns.OVERVIEW,
            DataConstract.DbColumns.RATE,
            DataConstract.DbColumns.LANGUAGE,
            DataConstract.DbColumns.POSTER,
            DataConstract.DbColumns.BACKDROP
    );

    private static final String CREATE_TABLE_FAVORITE_TV = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            DataConstract.DbColumns.TABLE_NAME_TV,
            DataConstract.DbColumns._ID,
            DataConstract.DbColumns.ID,
            DataConstract.DbColumns.TITLE,
            DataConstract.DbColumns.OVERVIEW,
            DataConstract.DbColumns.RATE,
            DataConstract.DbColumns.LANGUAGE,
            DataConstract.DbColumns.POSTER,
            DataConstract.DbColumns.BACKDROP
    );

    DataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_FAVORITE_MOVIES);
        db.execSQL(CREATE_TABLE_FAVORITE_TV);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DataConstract.DbColumns.TABLE_NAME_MOVIE);
        db.execSQL("DROP TABLE IF EXISTS " + DataConstract.DbColumns.TABLE_NAME_TV);
        onCreate(db);
    }
}
