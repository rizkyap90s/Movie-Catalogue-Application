package com.example.afinal.respone;

import com.example.afinal.model.Movie;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieRespone {
    @SerializedName("results")
    private List<Movie> movie_result;

    public List<Movie> getMovieResult(){
        return movie_result;
    }
}
