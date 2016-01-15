package com.bukit.mygeosystem.ModeWeather;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kevin on 12/7/2015.
 */
public class OpenWeather {


    private Coord coord;

    private Sys sys;

    private List<Weather> weather = new ArrayList<Weather>();

    private Main main;

    private Wind wind;

    private Rain rain;

    private Clouds clouds;

    private Long dt;

    private Long id;

    private String name;

    private Integer cod;


    public Coord getCoord() {
        return coord;
    }


    public void setCoord(Coord coord) {
        this.coord = coord;
    }


    public Sys getSys() {
        return sys;
    }


    public void setSys(Sys sys) {
        this.sys = sys;
    }


    public List<Weather> getWeather() {
        return weather;
    }


    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }


    public Main getMain() {
        return main;
    }


    public void setMain(Main main) {
        this.main = main;
    }


    public Wind getWind() {
        return wind;
    }


    public void setWind(Wind wind) {
        this.wind = wind;
    }


    public Rain getRain() {
        return rain;
    }


    public void setRain(Rain rain) {
        this.rain = rain;
    }


    public Clouds getClouds() {
        return clouds;
    }


    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }


    public Long getDt() {
        return dt;
    }


    public void setDt(Long dt) {
        this.dt = dt;
    }


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public Integer getCod() {
        return cod;
    }


    public void setCod(Integer cod) {
        this.cod = cod;
    }

}
