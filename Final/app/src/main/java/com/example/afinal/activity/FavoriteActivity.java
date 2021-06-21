package com.example.afinal.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.afinal.Fragment.Favorite_Fragment_Movie;
import com.example.afinal.Fragment.Favorite_Fragment_Tv;
import com.example.afinal.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FavoriteActivity extends AppCompatActivity {

    BottomNavigationView navigationViewfav;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        setTitle(getResources().getString(R.string.favorite));

        navigationViewfav = findViewById(R.id.navigation_fav);

        clickNavigation();

        if (savedInstanceState == null){
            navigationViewfav.setSelectedItemId(R.id.navigation_fav_movie);
        }

    }

    private void clickNavigation() {
        navigationViewfav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem Item) {
                Fragment fragment;
                switch (Item.getItemId()){
                    case R.id.navigation_fav_movie:
                        fragment = new Favorite_Fragment_Movie();
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.container_layout, fragment, fragment.getClass().getSimpleName())
                                .commit();
                        return true;
                    case R.id.navigation_fav_tv:
                        fragment = new Favorite_Fragment_Tv();
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.container_layout, fragment, fragment.getClass().getSimpleName())
                                .commit();
                        return true;
                }
                return false;
            }
        });
    }
}
