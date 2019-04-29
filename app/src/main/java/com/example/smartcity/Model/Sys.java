package com.example.smartcity.Model;

public class Sys {

    private long type ;
    private long id ;
    private double message ;
    private String country ;
    private long sunrise ;
    private long sunset ;

    public Sys(){}

    public long getType() {
        return type;
    }

    public void setType(long type) {
        type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        id = id;
    }

    public double getMessage() {
        return message;
    }

    public void setMessage(double message) {
        message = message;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        country = country;
    }

    public long getSunrise() {
        return sunrise;
    }

    public void setSunrise(long sunrise) {
        sunrise = sunrise;
    }

    public long getSunset() {
        return sunset;
    }

    public void setSunset(long sunset) {
        sunset = sunset;
    }
}
