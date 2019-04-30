package com.example.smartcity.Activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.smartcity.Adapter.RecyclerAdapter;
import com.example.smartcity.Api.ApiInterfaceNews;
import com.example.smartcity.Common.NewsCommon;
import com.example.smartcity.Model.NewsModel.Article;
import com.example.smartcity.Model.NewsModel.News;
import com.example.smartcity.R;
import com.example.smartcity.SharedPrefManager;
import com.example.smartcity.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class news extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    public static final String API_KEY = "be1286cb0fc84cfcbcec7366c62de76a";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Article> articles = new ArrayList<>();
    private RecyclerAdapter recyclerAdapter;
    private String TAG = news.class.getSimpleName();
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        if(!SharedPrefManager.getInstance(this).isLoggin()){
            Intent i = new Intent(this, Login.class);
            finish();
            startActivity(i);
        }

        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary2);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);

        onLoadingSwipeRefresh();
    }

    public void LoadJson(){

        swipeRefreshLayout.setRefreshing(true);

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

                    initListener();

                    swipeRefreshLayout.setRefreshing(false);

                } else {
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(news.this,"No Result !",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onRefresh() {
        LoadJson();
    }

    private void onLoadingSwipeRefresh(){

        swipeRefreshLayout.post(
                new Runnable() {
                    @Override
                    public void run() {
                        LoadJson();
                    }
                }
        );
    }

    private void initListener() {

        recyclerAdapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(news.this, NewsDetail.class);

                Article article = articles.get(position);
                intent.putExtra("url", article.getUrl());
                intent.putExtra("title", article.getTitle());
                intent.putExtra("img", article.getUrlToImage());
                intent.putExtra("date", article.getPublishedAt());
                intent.putExtra("source", article.getSource().getName());
                intent.putExtra("author", article.getAuthor());

                startActivity(intent);


            }
        });
    }
}
