package com.example.afinal.Fragment;


import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.afinal.R;
import com.example.afinal.adapter.AdapterFavorite;
import com.example.afinal.db.MovieHelper;
import com.example.afinal.model.Favorite;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Favorite_Fragment_Movie extends Fragment {


    private RecyclerView rvFav;

    private LinkedList<Favorite> listfavorites;
    private MovieHelper movieHelper;
    private AdapterFavorite adapterFavorite;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.favorite__fragment__movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvFav = view.findViewById(R.id.rv_movie_fav);
//        emptyImageView = view.findViewById(R.id.empty);
    }
    private  class LoadData extends AsyncTask<Void, Void, ArrayList<Favorite>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (listfavorites.size() > 0){
                listfavorites.clear();
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Favorite> favorites) {
            super.onPostExecute(favorites);
            listfavorites.addAll(favorites);
            adapterFavorite.setFavorites(listfavorites);
            adapterFavorite.notifyDataSetChanged();

        }

        @Override
        protected ArrayList<Favorite> doInBackground(Void... voids) {
            return movieHelper.query();
        }
    }

    @Override
    public void onResume() {
        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            rvFav.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        }
        else {
            rvFav.setLayoutManager(new GridLayoutManager(getActivity(), 6));
        }
        rvFav.setHasFixedSize(true);

        movieHelper = new MovieHelper(getActivity());
        movieHelper.open();
        listfavorites = new LinkedList<>();
        adapterFavorite= new AdapterFavorite(getActivity());
        adapterFavorite.setFavorites(listfavorites);
        rvFav.setAdapter(adapterFavorite);
        new Favorite_Fragment_Movie.LoadData().execute();

        super.onResume();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (movieHelper != null){
            movieHelper.close();
        }
    }
}
