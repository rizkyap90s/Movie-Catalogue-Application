package com.example.afinal.detail;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.afinal.R;
import com.example.afinal.db.TvHelper;
import com.example.afinal.model.Favorite;
import com.example.afinal.model.TV;

public class DetailTv extends AppCompatActivity {

    public static final String KEY_TV="tv";

    TextView txttitle, txtoverview, txtrate, txtlanguage;
    ImageView imgbackdrop, imgposter;
    ImageButton btnstatus;

    private TvHelper tvHelper;

    String URL_IMAGE = "https://image.tmdb.org/t/p/w500";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tv);

        txttitle = findViewById(R.id.detail_title_tv);
        txtoverview = findViewById(R.id.detail_desc_tv);
        txtrate = findViewById(R.id.detail_rating_tv);
        txtlanguage  = findViewById(R.id.detail_language_tv);
        btnstatus = findViewById(R.id.detail_status_tv);
        imgposter = findViewById(R.id.detail_poster_tv);
        imgbackdrop = findViewById(R.id.detail_backdrop_tv);

        final TV tv = getIntent().getExtras().getParcelable(KEY_TV);
        txttitle.setText(tv.getTv_title());
        txtoverview.setText(tv.getTv_overview());
        String rate = Double.toString(tv.getTv_rate());
        txtrate.setText(rate);
        txtlanguage.setText(tv.getTv_language());
        Glide.with(this)
                .load(URL_IMAGE+tv.getTv_poster())
                .into(imgposter);
        Glide.with(this)
                .load(URL_IMAGE+tv.getTv_backdrop())
                .into(imgbackdrop);

        setTitle(tv.getTv_title());


        tvHelper = new TvHelper(this);
        tvHelper.open();

        if (!tvHelper.hasObject(tv.getTv_id())) {
            btnstatus.setImageDrawable(getResources().getDrawable(R.drawable.liked));
        }

        btnstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!tvHelper.hasObject(tv.getTv_id())){
                    btnstatus.setImageDrawable(getResources().getDrawable(R.drawable.dislike));

                    tvHelper.delete(tv.getTv_id());

                }
                else{
                    btnstatus.setImageDrawable(getResources().getDrawable(R.drawable.liked));

                    Favorite favorite = new Favorite();
                    favorite.setFav_id(tv.getTv_id());
                    favorite.setFav_title(tv.getTv_title());
                    favorite.setFav_overview(tv.getTv_overview());
                    favorite.setFav_rating(tv.getTv_rate());
                    favorite.setFav_language(tv.getTv_language());
                    favorite.setFav_poster(tv.getTv_poster());
                    favorite.setFav_backdrop(tv.getTv_backdrop());
                    tvHelper.insert(favorite);
                }
            }
        });


    }

}
