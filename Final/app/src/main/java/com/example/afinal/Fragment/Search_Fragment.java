package com.example.afinal.Fragment;


import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
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

import com.example.afinal.BuildConfig;
import com.example.afinal.R;
import com.example.afinal.adapter.AdapterSearch;
import com.example.afinal.api.Client;
import com.example.afinal.api.Service;
import com.example.afinal.model.Search;
import com.example.afinal.respone.SearchRespone;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.afinal.activity.MainActivity.SEARCH;

/**
 * A simple {@link Fragment} subclass.
 */
public class Search_Fragment extends Fragment {
    private String key ;
    RecyclerView recyclerView;
    AdapterSearch adapterSearch;
    private static String STATE = "state";


    public Search_Fragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        key = getArguments().getString(SEARCH);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rv_search);

        show();
        loadJsoon(key);

        if (savedInstanceState != null) {
            ArrayList<Search>list= savedInstanceState.getParcelableArrayList(STATE);
            adapterSearch.setSearches(list);
            recyclerView.setAdapter(adapterSearch);
        }else {
            loadJsoon(key);
        }
    }

    private void show(){
        List<Search> searchList = new ArrayList<>();

        adapterSearch  = new AdapterSearch(getActivity(), searchList);
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        }else {
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 6));
        }
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterSearch);
        adapterSearch.notifyDataSetChanged();
    }

    private void loadJsoon(String key) {
        try {

            Client Client = new Client();
            Service service = Client.getClient().create(Service.class);
            Call<SearchRespone> call = service.getMovieSearch(key, BuildConfig.API_KEY);
            call.enqueue(new Callback<SearchRespone>() {
                @Override
                public void onResponse(Call<SearchRespone> call, Response<SearchRespone> response) {

                    List<Search> searches = response.body().getSearchResult();
                    recyclerView.setAdapter(new AdapterSearch(getActivity().getApplicationContext(), searches));
                    recyclerView.smoothScrollToPosition(0);
                    adapterSearch.setSearches(searches);
                }

                @Override
                public void onFailure(Call<SearchRespone> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                }
            });
        }
        catch (Exception e){
            Log.d("Error", e.getMessage());
            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(STATE, new ArrayList<>(adapterSearch.getSearches()));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            ArrayList<Search> list= savedInstanceState.getParcelableArrayList(STATE);
            adapterSearch.setSearches(list);
            recyclerView.setAdapter(adapterSearch);
        }
    }

}
