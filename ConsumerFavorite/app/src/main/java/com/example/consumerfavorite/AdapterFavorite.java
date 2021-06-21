package com.example.consumerfavorite;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import static com.example.consumerfavorite.DataConstract.DbColumns.POSTER;
import static com.example.consumerfavorite.DataConstract.DbColumns.TITLE;
import static com.example.consumerfavorite.DataConstract.getColumnString;

public class AdapterFavorite extends CursorAdapter {

    public final static String URL_IMAGE = "http://image.tmdb.org/t/p/w500";
    Context context;
    public AdapterFavorite(Context context, Cursor cursor, boolean autoRequery) {
        super(context, cursor, autoRequery);
        this.context = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_item, parent, false);
        return view;
    }

    @Override
    public Cursor getCursor() {
        return super.getCursor();
    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {
        if (cursor != null) {
            final TextView txtTitle = view.findViewById(R.id.txt_title);
            ImageView imageView = view.findViewById(R.id.img_poster);
            txtTitle.setText(getColumnString(cursor, TITLE));
            Glide.with(context)
                    .load(URL_IMAGE + getColumnString(cursor, POSTER))
                    .into(imageView);
        }
    }
}
