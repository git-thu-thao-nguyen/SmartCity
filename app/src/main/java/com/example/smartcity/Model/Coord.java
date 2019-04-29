package com.example.smartcity.Model;

public class Coord {

    private double lon ;
    private double lat ;

    public Coord(){}

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        lon = lon;
    }

    @Override
    public String toString(){
        return new StringBuilder("[").append(this.lat).append(',').append(this.lon).append(']').toString();
    }
}
