package com.example.smartcity.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.smartcity.Adapter.WeatherForecastAdapter;
import com.example.smartcity.Common.WeatherCommon;
import com.example.smartcity.Model.WeatherModel.WeatherForecastResult;
import com.example.smartcity.R;
import com.example.smartcity.Api.IOpenWeatherMap;
import com.example.smartcity.Api.WeatherRetrofitClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForecastFragment extends Fragment {

    CompositeDisposable compositeDisposable;
    IOpenWeatherMap mService;

    TextView txt_city_name, txt_geo_coord;
    RecyclerView recycler_forecast;

    static ForecastFragment instance;

    public static ForecastFragment getInstance(){
        if(instance == null)
            instance = new ForecastFragment();
        return instance;
    }


    public ForecastFragment() {
        compositeDisposable = new CompositeDisposable();
        Retrofit retrofit = WeatherRetrofitClient.getInstance();
        mService = retrofit.create(IOpenWeatherMap.class);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View itemView = inflater.inflate(R.layout.fragment_forecast,container,false);

        txt_city_name = (TextView)itemView.findViewById(R.id.txt_city_name);
        txt_geo_coord = (TextView)itemView.findViewById(R.id.txt_geo_coord);

        recycler_forecast = (RecyclerView)itemView.findViewById(R.id.recycler_forecast);
        recycler_forecast.setHasFixedSize(true);
        recycler_forecast.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        getForecastWeatherInformation();
        
        return itemView;
    }

    private void getForecastWeatherInformation() {

        compositeDisposable.add(mService.getForecastWeatherByLatLng(
                String.valueOf(WeatherCommon.current_location.getLatitude()),
                String.valueOf(WeatherCommon.current_location.getLongitude()),
                WeatherCommon.APP_ID,
                "metric")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WeatherForecastResult>() {
                    @Override
                    public void accept(WeatherForecastResult weatherForecastResult) throws Exception {
                        displayForecastWeather(weatherForecastResult);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("@ERROR","" + throwable.getMessage());
                    }
                })

        );
    }

    private void displayForecastWeather(WeatherForecastResult weatherForecastResult) {
        txt_city_name.setText(new StringBuilder(weatherForecastResult.city.name));
        txt_geo_coord.setText(new StringBuilder(weatherForecastResult.city.coord.toString()));

        WeatherForecastAdapter adapter = new WeatherForecastAdapter(getContext(),weatherForecastResult);
        recycler_forecast.setAdapter(adapter);
    }

}
