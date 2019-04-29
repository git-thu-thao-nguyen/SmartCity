package com.example.smartcity.Api;

import com.example.smartcity.Model.NewsModel.News;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterfaceNews {

    @GET("top-headlines")
    Call<News> getNews(

            @Query("country") String country,
            @Query("apiKey") String apiKey
    );
}



