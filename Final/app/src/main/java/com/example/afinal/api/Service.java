package com.example.afinal.api;

import com.example.afinal.respone.MovieRespone;
import com.example.afinal.respone.SearchRespone;
import com.example.afinal.respone.TvRespone;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Service {
    @GET("movie/now_playing")
    Call<MovieRespone> getNowPlayingMovie(@Query("api_key") String apikey);

    @GET("tv/airing_today")
    Call<TvRespone> getTvAiringToday(@Query("api_key") String apikey);

    @GET("search/multi")
    Call<SearchRespone> getMovieSearch(@Query("query") String key, @Query("api_key") String apikey);

    @GET("movie/upcoming")
    Call<MovieRespone> getUpComingMovie(@Query("api_key") String apikey);


}
