package com.example.afinal.Fragment;


import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.afinal.BuildConfig;
import com.example.afinal.R;
import com.example.afinal.adapter.AdapterMovie;
import com.example.afinal.api.Client;
import com.example.afinal.api.Service;
import com.example.afinal.model.Movie;
import com.example.afinal.respone.MovieRespone;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class Main_Fragment_Movie extends Fragment {

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    List<Movie> modelMovies;
    ProgressDialog progressDialog;
    AdapterMovie adapterMovie;

    private static String  MOVIE = "movie";

    public Main_Fragment_Movie() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.main__fragment__movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.rv_movie);
        swipeRefreshLayout = view.findViewById(R.id.main_view_movie);

        show();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                show();
            }
        });
    }

    private void show(){
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Fetching Data");
        progressDialog.setCancelable(false);
        progressDialog.show();

        List<Movie> listMovies = new ArrayList<>();
        adapterMovie = new AdapterMovie(getActivity(), listMovies);
        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        }else {
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),6));
        }

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterMovie);
        adapterMovie.notifyDataSetChanged();

        loadJsoon();
    }

    private void loadJsoon() {

        try {
            Client client = new Client();
            final Service service = client.getClient().create(Service.class);

            Call<MovieRespone> call = service.getNowPlayingMovie(BuildConfig.API_KEY);
            call.enqueue(new Callback<MovieRespone>() {
                @Override
                public void onResponse(Call<MovieRespone> call, Response<MovieRespone> response) {
                    List<Movie> modelMovies = response.body().getMovieResult();
                    recyclerView.setAdapter(new AdapterMovie(getActivity(), modelMovies));
                    adapterMovie.setMovies(modelMovies);
                    if (swipeRefreshLayout.isRefreshing()){
                        swipeRefreshLayout.setRefreshing(false);
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<MovieRespone> call, Throwable t) {
                    Toast.makeText(getActivity(), "Fail to Load data", Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch (Exception e){
            Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(MOVIE, new ArrayList<>(adapterMovie.getMovie()));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null){
            ArrayList<Movie> mMovie;
            mMovie = savedInstanceState.getParcelableArrayList(MOVIE);
            adapterMovie.setMovies(mMovie);
            recyclerView.setAdapter(adapterMovie);
            progressDialog.dismiss();
        }
    }

}
