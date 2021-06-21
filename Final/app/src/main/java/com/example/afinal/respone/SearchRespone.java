package com.example.afinal.respone;

import com.example.afinal.model.Search;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchRespone {
    @SerializedName("results")
    private List<Search> search_result;

    public List<Search> getSearchResult(){
        return search_result;
    }
    public void setMovieResult(List<Search> search_result){
        this.search_result=search_result;
    }
}
