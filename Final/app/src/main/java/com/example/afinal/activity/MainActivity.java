package com.example.afinal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.afinal.Fragment.Main_Fragment_Movie;
import com.example.afinal.Fragment.Main_Fragment_Tv;
import com.example.afinal.Fragment.Search_Fragment;
import com.example.afinal.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView navigationView;
    FloatingActionButton floatingActionButton;
    MaterialSearchView searchView;;
    Toolbar toolbar;
    Fragment fragment;
    public static final String SEARCH = "search";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("");


        searchView = findViewById(R.id.search_bar);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView = findViewById(R.id.navigation);
        floatingActionButton = findViewById(R.id.fab_fav);
        clickNavigation();
        clickfavorite();
        if (savedInstanceState == null){
            navigationView.setSelectedItemId(R.id.navigation_movie);
        }

        searchView.setHint(getString(R.string.search_hint));
        searchView.setHintTextColor(getResources().getColor(R.color.colorAccent));
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Bundle bundle = new Bundle();
                bundle.putString(SEARCH, query);
                fragment = new Search_Fragment();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container_layout, fragment, fragment.getClass().getSimpleName())
                        .commit();
                if (getSupportActionBar() != null){
                    getSupportActionBar().setTitle(getString(R.string.search)+" '"+query+"'");

                }
                fragment.setArguments(bundle);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void clickfavorite() {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FavoriteActivity.class);
                startActivity(intent);
            }
        });
    }

    private void clickNavigation() {
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem Item) {
                Fragment fragment;
                switch (Item.getItemId()){
                    case R.id.navigation_movie:
                        fragment = new Main_Fragment_Movie();
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.container_layout, fragment, fragment.getClass().getSimpleName())
                                .commit();
                        if (getSupportActionBar() != null){
                            getSupportActionBar().setTitle(getString(R.string.navigation_movie));
                        }
                        return true;
                    case R.id.navigation_tv:
                        fragment = new Main_Fragment_Tv();
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.container_layout, fragment, fragment.getClass().getSimpleName())
                                .commit();
                        if (getSupportActionBar() != null){
                            getSupportActionBar().setTitle(getString(R.string.navigation_tv));
                        }
                        return true;
                }
                return false;
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.setting, menu);
        MenuItem item = menu.findItem(R.id.search);
        searchView.setMenuItem(item);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.setting:
                Intent toSetting = new Intent(this, SettingsActivity.class);
                startActivity(toSetting);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
