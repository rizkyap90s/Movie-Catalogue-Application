package com.example.afinal.detail;


import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.afinal.R;
import com.example.afinal.model.Search;

public class DetailSearch extends AppCompatActivity {

    public static final String KEY_SEARCH="search";

    TextView txttitle, txtoverview, txtrate, txtlanguage;
    ImageView imgbackdrop, imgposter;
    ImageButton btnstatus;

    String URL_IMAGE = "https://image.tmdb.org/t/p/w500";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_search);

        txttitle = findViewById(R.id.detail_title_search);
        txtoverview = findViewById(R.id.detail_desc_search);
        txtrate = findViewById(R.id.detail_rating_search);
        txtlanguage  = findViewById(R.id.detail_language_search);
        btnstatus = findViewById(R.id.detail_status_search);
        imgposter = findViewById(R.id.detail_poster_search);
        imgbackdrop = findViewById(R.id.detail_backdrop_search);

        final Search search = getIntent().getExtras().getParcelable(KEY_SEARCH);

        txttitle.setText(search.getSearch_title());
        txtoverview.setText(search.getSearch_overview());
        String rating= Double.toString(search.getSearch_rating());
        txtrate.setText(rating);
        txtlanguage.setText(search.getSearch_language());
        Glide.with(this)
                .load(URL_IMAGE + search.getSearch_poster())
                .into(imgposter);
        Glide.with(this)
                .load(URL_IMAGE + search.getSearch_backdrop())
                .into(imgbackdrop);

        setTitle(search.getSearch_title());


    }
}
