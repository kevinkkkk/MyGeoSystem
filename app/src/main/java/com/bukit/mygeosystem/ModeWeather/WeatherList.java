package com.bukit.mygeosystem.ModeWeather;

/**
 * Created by kevin on 12/9/2015.
 */
public class WeatherList {

    String date;
    Double temp_max;
    Double temp_low;
    String weatherIcon;
    Double windSpeed;

    public WeatherList(String date, Double max, Double low, String icon, Double speed){
        this.date = date;
        this.temp_max = max;
        this.temp_low = low;
        this.weatherIcon = icon;
        this.windSpeed = speed;
    }

    public String getDate(){
        return date;
    }

    public String getWeatherIcon(){
        return weatherIcon;
    }

    public Double getTemp_max(){
        return temp_max;
    }

    public  Double getTemp_low(){
        return temp_low;
    }

    public Double getWindSpeed(){
        return windSpeed;
    }
}
