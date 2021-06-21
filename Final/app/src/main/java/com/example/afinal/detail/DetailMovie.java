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
import com.example.afinal.model.Favorite;
import com.example.afinal.model.Movie;

public class DetailMovie extends AppCompatActivity {

    public static final String KEY_MOVIE="movie";

    private MovieHelper movieHelper;

    TextView txttitle, txtoverview, txtrate, txtlanguage;
    ImageView imgbackdrop, imgposter;
    ImageButton btnstatus;

    String URL_IMAGE = "https://image.tmdb.org/t/p/w500";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);


        txttitle = findViewById(R.id.detail_title_movie);
        txtoverview = findViewById(R.id.detail_desc_movie);
        txtrate = findViewById(R.id.detail_rating_movie);
        txtlanguage  = findViewById(R.id.detail_language_movie);
        btnstatus = findViewById(R.id.detail_status_movie);
        imgposter = findViewById(R.id.detail_poster_movie);
        imgbackdrop = findViewById(R.id.detail_backdrop_movie);

        final Movie movie = getIntent().getExtras().getParcelable(KEY_MOVIE);
        txttitle.setText(movie.getMovie_title());
        txtoverview.setText(movie.getMovie_overview());
        String rating= Double.toString(movie.getMovie_rating());
        txtrate.setText(rating);
        txtlanguage.setText(movie.getMovie_language());
        Glide.with(this)
                .load(URL_IMAGE + movie.getMovie_poster())
                .into(imgposter);
        Glide.with(this)
                .load(URL_IMAGE + movie.getMovie_backdrop())
                .into(imgbackdrop);

        setTitle(movie.getMovie_title());

        movieHelper = new MovieHelper(this);
        movieHelper.open();

        if (!movieHelper.hasObject(movie.getMovie_id())){
            btnstatus.setImageDrawable(getResources().getDrawable(R.drawable.liked));
        }
        btnstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!movieHelper.hasObject(movie.getMovie_id())){
                    btnstatus.setImageDrawable(getResources().getDrawable(R.drawable.dislike));

                    movieHelper.delete(movie.getMovie_id());

                }
                else{
                    btnstatus.setImageDrawable(getResources().getDrawable(R.drawable.liked));

                    Favorite favorite = new Favorite();
                    favorite.setFav_id(movie.getMovie_id());
                    favorite.setFav_title(movie.getMovie_title());
                    favorite.setFav_overview(movie.getMovie_overview());
                    favorite.setFav_rating(movie.getMovie_rating());
                    favorite.setFav_language(movie.getMovie_language());
                    favorite.setFav_poster(movie.getMovie_poster());
                    favorite.setFav_backdrop(movie.getMovie_backdrop());
                    movieHelper.insert(favorite);

                }
            }
        });

    }

}
