package com.bukit.mygeosystem.REST;

import com.bukit.mygeosystem.ModeWeather.FiveDaysForecast;
import com.bukit.mygeosystem.ModeWeather.OpenWeather;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by kevin on 12/7/2015.
 */
public interface RestService {

    @GET("/weather")
    void getWeather(@Query("APPID") String API_KEY, @Query("lon") Double longitude,@Query("lat") Double latitude,
                    @Query("units") String units, Callback<OpenWeather> cb);

    @GET("/forecast")
    void getForecast(@Query("APPID") String API_KEY, @Query("lon") Double longitude, @Query("lat") Double latitude,
                     @Query("units") String units, Callback<FiveDaysForecast> cb);


}
