package com.example.smartcity.Model;

import java.util.List;

public class WeatherResult {

    private Coord coord ;
    private List<Weather> weather ;
    private String base ;
    private Main main ;
    private long visibility ;
    private Wind wind ;
    private Clouds clouds ;
    private long dt ;
    private Sys sys ;
    private long id ;
    private String name ;
    private long cod ;

    public WeatherResult() {
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        coord = coord;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        weather = weather;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        base = base;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        main = main;
    }

    public long getVisibility() {
        return visibility;
    }

    public void setVisibility(long visibility) {
        visibility = visibility;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        wind = wind;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        clouds = clouds;
    }

    public long getDt() {
        return dt;
    }

    public void setDt(long dt) {
        dt = dt;
    }

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        sys = sys;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        name = name;
    }

    public long getCod() {
        return cod;
    }

    public void setCod(long cod) {
        cod = cod;
    }
}
