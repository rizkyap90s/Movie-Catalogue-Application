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
import com.example.afinal.adapter.AdapterTv;
import com.example.afinal.api.Client;
import com.example.afinal.api.Service;
import com.example.afinal.model.TV;
import com.example.afinal.respone.TvRespone;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class Main_Fragment_Tv extends Fragment {

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    List<TV> modelTv;
    AdapterTv adapterTv;

    private static String  TV = "tv";

    public Main_Fragment_Tv() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main__fragment__tv, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        swipeRefreshLayout = view.findViewById(R.id.main_view_tv);
        recyclerView = view.findViewById(R.id.rv_tv);
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

        List<TV> listtv = new ArrayList<>();
        adapterTv = new AdapterTv(getActivity(), listtv);
        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        }else {
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),6));
        }

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterTv);
        adapterTv.notifyDataSetChanged();

        loadJsoon();
    }
    private void loadJsoon() {

        try {
            Client client = new Client();
            Service service = client.getClient().create(Service.class);

            Call<TvRespone> call = service.getTvAiringToday(BuildConfig.API_KEY);
            call.enqueue(new Callback<TvRespone>() {
                @Override
                public void onResponse(Call<TvRespone> call, Response<TvRespone> response) {
                    List<TV> modelTv = response.body().getTvResult();
                    recyclerView.setAdapter(new AdapterTv(getActivity(), modelTv));
                    adapterTv.setTV(modelTv);

                    if (swipeRefreshLayout.isRefreshing()){
                        swipeRefreshLayout.setRefreshing(false);
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<TvRespone> call, Throwable t) {
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
        outState.putParcelableArrayList("tv", new ArrayList<>(adapterTv.getTV()));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null){
            ArrayList<TV> mMovie;
            mMovie = savedInstanceState.getParcelableArrayList(TV);
            adapterTv.setTV(mMovie);
            recyclerView.setAdapter(adapterTv);
            progressDialog.dismiss();

        }
    }
}
