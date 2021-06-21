package com.example.afinal.detail;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.afinal.R;
import com.example.afinal.db.MovieHelper;
import com.example.afinal.db.TvHelper;
import com.example.afinal.model.Favorite;

public class Favorite_detail extends AppCompatActivity {

    public static String FAV ="fav";

    TextView txttitle, txtoverview, txtrate, txtlanguage;
    ImageView imgbackdrop, imgposter;
    ImageButton btnstatus;

    String URL_IMAGE = "https://image.tmdb.org/t/p/w500";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_detail);
        txttitle = findViewById(R.id.detail_title_fav);
        txtoverview = findViewById(R.id.detail_desc_fav);
        txtrate = findViewById(R.id.detail_rating_fav);
        txtlanguage = findViewById(R.id.detail_language_fav);
        imgbackdrop = findViewById(R.id.detail_backdrop_fav);
        imgposter = findViewById(R.id.detail_poster_fav);
        btnstatus = findViewById(R.id.detail_status_fav);

        final Favorite favorite = getIntent().getExtras().getParcelable(FAV);
        txttitle.setText(favorite.getFav_title());
        txtoverview.setText(favorite.getFav_overview());
        String rate = Double.toString(favorite.getFav_rating());
        txtrate.setText(rate);
        txtlanguage.setText(favorite.getFav_language());
        Glide.with(this)
                .load(URL_IMAGE+favorite.getFav_poster())
                .into(imgposter);
        Glide.with(this)
                .load(URL_IMAGE+favorite.getFav_backdrop())
                .into(imgbackdrop);

        setTitle(favorite.getFav_title());

        final MovieHelper movieHelper = new MovieHelper(this);
        movieHelper.open();
        final TvHelper tvHelper = new TvHelper(this);
        tvHelper.open();

        if (favorite.getFav_id() != null){
            btnstatus.setImageDrawable(getResources().getDrawable(R.drawable.liked));
        }
        btnstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnstatus.setImageDrawable(getResources().getDrawable(R.drawable.dislike));
                movieHelper.delete(favorite.getFav_id());
                tvHelper.delete(favorite.getFav_id());

            }
        });

    }
}
