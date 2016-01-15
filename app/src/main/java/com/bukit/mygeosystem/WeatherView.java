package com.bukit.mygeosystem;


/**
 * Created by kevin on 12/6/2015.
 */
public interface WeatherView<T> {

    void setData(T w);
    void showError();

}
