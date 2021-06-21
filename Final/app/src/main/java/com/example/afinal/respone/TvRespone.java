package com.example.afinal.respone;

import com.example.afinal.model.TV;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TvRespone {
    @SerializedName("results")
    private List<TV> tv_result;

    public List<TV> getTvResult(){
        return tv_result;
    }
    public void setTvResult(List<TV> movie_result){
        this.tv_result=tv_result;
    }
}
