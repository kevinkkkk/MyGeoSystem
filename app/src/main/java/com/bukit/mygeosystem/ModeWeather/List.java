package com.bukit.mygeosystem.ModeWeather;

import java.util.ArrayList;

/**
 * Created by kevin on 12/8/2015.
 */
public class List {


    private Integer dt;

    private Main main;

    private java.util.List<Weather> weather = new ArrayList();

    private Clouds clouds;

    private Wind wind;

    private Sys_ sys;

    private String dt_txt;

    private Rain rain;

    private Snow snow;


    public Integer getDt() {
        return dt;
    }


    public void setDt(Integer dt) {
        this.dt = dt;
    }


    public Main getMain() {
        return main;
    }


    public void setMain(Main main) {
        this.main = main;
    }


    public java.util.List<Weather> getWeather() {
        return weather;
    }


    public void setWeather(java.util.List<Weather> weather) {
        this.weather = weather;
    }


    public Clouds getClouds() {
        return clouds;
    }


    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }


    public Wind getWind() {
        return wind;
    }


    public void setWind(Wind wind) {
        this.wind = wind;
    }


    public Sys_ getSys() {
        return sys;
    }


    public void setSys(Sys_ sys) {
        this.sys = sys;
    }


    public String getDtTxt() {
        return dt_txt;
    }


    public void setDtTxt(String dtTxt) {
        this.dt_txt = dtTxt;
    }


    public Rain getRain() {
        return rain;
    }


    public void setRain(Rain rain) {
        this.rain = rain;
    }

    public Snow getSnow() {
        return snow;
    }


    public void setSnow(Snow snow) {
        this.snow = snow;
    }

}