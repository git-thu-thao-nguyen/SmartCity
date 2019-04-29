package com.example.smartcity.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Adapter;
import android.widget.Toast;

import com.example.smartcity.Adapter.RecyclerAdapter;
import com.example.smartcity.Api.ApiInterfaceNews;
import com.example.smartcity.Common.NewsCommon;
import com.example.smartcity.Model.NewsModel.Article;
import com.example.smartcity.Model.NewsModel.News;
import com.example.smartcity.R;
import com.example.smartcity.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class news extends AppCompatActivity {

    public static final String API_KEY = "be1286cb0fc84cfcbcec7366c62de76a";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Article> articles = new ArrayList<>();
    private RecyclerAdapter recyclerAdapter;
    private String TAG = news.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);

        LoadJson();
    }

    public void LoadJson(){

        ApiInterfaceNews apiInterfaceNews = NewsCommon.getApiClient().create(ApiInterfaceNews.class);

        String country = Utils.getCountry();
        Log.d("@Country",country);

        Call<News> call;
        call = apiInterfaceNews.getNews(country,API_KEY);

        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {

                if(response.isSuccessful() && response.body().getArticle() != null){

                    if(articles.isEmpty()){
                        articles.clear();
                    }

                    //Log.d("@article", String.valueOf(response.body().getArticle().get(0).getPublishedAt()));

                    articles = response.body().getArticle();
                    recyclerAdapter = new RecyclerAdapter(articles, news.this);
                    recyclerView.setAdapter(recyclerAdapter);
                    recyclerAdapter.notifyDataSetChanged();

                } else {
                    Toast.makeText(news.this,"No Result !",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {

            }
        });
    }
}
