package com.example.smartcity.Api;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherRetrofitClient {

    private static Retrofit instance;

    // https://api.openweathermap.org/data/2.5/weather?lat=43.605066&lon=3.9060819&appid=9cea38fe2f03a6c6bf5621ed52aad107
    // https://api.openweathermap.org/data/2.5/weather?lat=37.4219983&lon=-122.084&appid=9cea38fe2f03a6c6bf5621ed52aad107

    // https://api.openweathermap.org/data/2.5/forecast?lat=43.605066&lon=3.9060819&appid=9cea38fe2f03a6c6bf5621ed52aad107
    // https://api.openweathermap.org/data/2.5/forecast?lat=37.4219983&lon=-122.084&appid=9cea38fe2f03a6c6bf5621ed52aad107



    public static Retrofit getInstance(){
        if(instance==null)
            instance = new Retrofit.Builder()
                    .baseUrl("https://api.openweathermap.org/data/2.5/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        return instance;
    }
}
