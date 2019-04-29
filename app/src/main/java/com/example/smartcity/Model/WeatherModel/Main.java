package com.example.smartcity.Model.WeatherModel;

public class Main {

    private double temp ;
    private float pressure ;
    private long humidity ;
    private double temp_min ;
    private double temp_max ;

    public Main(){}

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        temp = temp;
    }

    public float getPressure() {
        return pressure;
    }

    public void setPressure(long pressure) {
        pressure = pressure;
    }

    public long getHumidity() {
        return humidity;
    }

    public void setHumidity(long humidity) {
        humidity = humidity;
    }

    public double getTempMin() {
        return temp_min;
    }

    public void setTempMin(double tempMin) {
        temp_min = tempMin;
    }

    public double getTempMax() {
        return temp_max;
    }

    public void setTempMax(double tempMax) {
        temp_max = tempMax;
    }

}
