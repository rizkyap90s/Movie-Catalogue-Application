package com.example.afinal.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.afinal.db.DataConstract;
import com.example.afinal.db.MovieHelper;

import static com.example.afinal.db.DataConstract.AUTHORITY;
import static com.example.afinal.db.DataConstract.CONTENT_URI;
import static com.example.afinal.db.DataConstract.DbColumns.TABLE_NAME_MOVIE;

public class MovieProvider extends ContentProvider {

    private static final int FAVORITE = 1;
    private static final int FAVORITE_ID = 2;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(AUTHORITY, DataConstract.DbColumns.TABLE_NAME_MOVIE, FAVORITE);
        sUriMatcher.addURI(AUTHORITY,
                DataConstract.DbColumns.TABLE_NAME_MOVIE+ "/#",
                FAVORITE_ID);
    }

    private MovieHelper favoriteHelper;
    @Override
    public boolean onCreate() {
        favoriteHelper = new MovieHelper(getContext());
        favoriteHelper.open();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;
        switch(sUriMatcher.match(uri)){
            case FAVORITE:
                cursor = favoriteHelper.queryProvider();
                break;
            case FAVORITE_ID:
                cursor = favoriteHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }

        if (cursor != null){
            cursor.setNotificationUri(getContext().getContentResolver(),uri);
        }

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long add ;

        switch (sUriMatcher.match(uri)){
            case FAVORITE:
                add = favoriteHelper.insertProvider(values);
                break;
            default:
                add = 0;
                break;
        }

        if (add > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return Uri.parse(CONTENT_URI + "/" + add);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int delete;
        switch (sUriMatcher.match(uri)) {
            case FAVORITE_ID:
                delete =  favoriteHelper.deleteProvider(uri.getLastPathSegment());
                break;
            default:
                delete = 0;
                break;
        }

        if (delete > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return delete;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int update;
        if(sUriMatcher.match(uri) == FAVORITE_ID){
            update = favoriteHelper.updateProvider(uri.getLastPathSegment(),values);
        }
        else {
            update = 0;
        }
        if (update > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return update;
    }
}
