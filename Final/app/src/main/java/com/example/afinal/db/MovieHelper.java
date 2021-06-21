package com.example.afinal.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.afinal.model.Favorite;

import java.util.ArrayList;

import static com.example.afinal.db.DataConstract.DbColumns.BACKDROP;
import static com.example.afinal.db.DataConstract.DbColumns.ID;
import static com.example.afinal.db.DataConstract.DbColumns.LANGUAGE;
import static com.example.afinal.db.DataConstract.DbColumns.OVERVIEW;
import static com.example.afinal.db.DataConstract.DbColumns.POSTER;
import static com.example.afinal.db.DataConstract.DbColumns.RATE;
import static com.example.afinal.db.DataConstract.DbColumns.TABLE_NAME_MOVIE;
import static com.example.afinal.db.DataConstract.DbColumns.TITLE;

public class MovieHelper {
    private static String DATABASE_TABLE = TABLE_NAME_MOVIE;
    private Context context;
    private DataHelper dataHelper;
    private static MovieHelper movieHelper;

    private SQLiteDatabase database;

    public MovieHelper(Context context){
        this.context = context;
    }
    public MovieHelper open() throws SQLException {
        dataHelper = new DataHelper(context);
        database = dataHelper.getWritableDatabase();
        return this;
    }
    public void close(){
        dataHelper.close();
    }

    public static MovieHelper getInstance(Context context) {
        if (movieHelper == null){
            synchronized (SQLiteOpenHelper.class){
                if (movieHelper == null){
                    movieHelper = new MovieHelper(context);
                }
            }
        }
        return movieHelper;
    }

    public ArrayList<Favorite> query(){
        ArrayList<Favorite> arrayList = new ArrayList<Favorite>();
        Cursor cursor = database.query(DATABASE_TABLE,null,null,null,null,null,ID +" DESC",null);
        cursor.moveToFirst();
        Favorite favorite;
        if (cursor.getCount()>0) {
            do {
                favorite = new Favorite();
                favorite.setFav_id(cursor.getString(cursor.getColumnIndexOrThrow(ID)));
                favorite.setFav_title(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                favorite.setFav_poster(cursor.getString(cursor.getColumnIndexOrThrow(POSTER)));
                favorite.setFav_language(cursor.getString(cursor.getColumnIndexOrThrow(LANGUAGE)));
                favorite.setFav_overview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));
                favorite.setFav_rating(cursor.getDouble(cursor.getColumnIndexOrThrow(RATE)));
                favorite.setFav_backdrop(cursor.getString(cursor.getColumnIndexOrThrow(BACKDROP)));

                arrayList.add(favorite);
                cursor.moveToNext();
            }while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insert(Favorite data){
        ContentValues init = new ContentValues();
        init.put(ID, data.getFav_id());
        init.put(TITLE, data.getFav_title());
        init.put(RATE, data.getFav_rating());
        init.put(BACKDROP, data.getFav_backdrop());
        init.put(OVERVIEW, data.getFav_overview());
        init.put(POSTER, data.getFav_poster());
        init.put(LANGUAGE, data.getFav_language());
        return database.insert(DATABASE_TABLE, null, init);
    }

    public void delete(String id){
        database.delete(TABLE_NAME_MOVIE, ID + " = '" + id + "'", null);
    }

    public boolean hasObject (String id){
        database = dataHelper.getWritableDatabase();
        String query = "SELECT "  + ID + " FROM " + TABLE_NAME_MOVIE + " WHERE " + ID +" =?";
        Cursor cursor = database.rawQuery(query, new String[]{id});
        if (cursor.getCount() > 0) {
            cursor.close();
            return false;
        }else {
            return true;

        }
    }
    public Cursor queryByIdProvider(String id){
        return database.query(DATABASE_TABLE,null
                ,ID + " = ?"
                ,new String[]{id}
                ,null
                ,null
                ,null
                ,null);
    }
    public Cursor queryProvider(){
        return database.query(DATABASE_TABLE
                ,null
                ,null
                ,null
                ,null
                ,null
                ,ID + " DESC");
    }
    public long insertProvider(ContentValues values){
        return database.insert(DATABASE_TABLE,null,values);
    }

    public int updateProvider(String id, ContentValues values) {
        return database.update(DATABASE_TABLE, values, ID + " = ?", new String[]{id});
    }
    public int deleteProvider(String id){
        return database.delete(DATABASE_TABLE,ID + " = ?", new String[]{id});
    }


}
