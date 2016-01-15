package com.bukit.mygeosystem.REST;


import android.content.pm.ResolveInfo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * Created by kevin on 12/7/2015.
 */
public class RestClient {

    final static String BASE_URL_OPENWEATHER = "http://api.openweathermap.org/data/2.5";
    final static String BASE_URL_YELLOWPAGE = "http://api.sandbox.yellowapi.com";

    private static RestService weatherApi;
    private static YellowPageService yellowPageService;


    static{
        initialWeatherRestClient();
        initialYellowRestClient();
    }

    private RestClient(){

    }

    public static RestService getWeatherApi(){
        return weatherApi;
    }

    public static YellowPageService getYellowPageService(){
        return yellowPageService;
    }


    private static void initialWeatherRestClient(){
        RestAdapter.Builder builder = new RestAdapter.Builder().setEndpoint(BASE_URL_OPENWEATHER)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addHeader("Accept", "application/json");
                    }
                });

        RestAdapter adapter = builder.build();
        weatherApi = adapter.create(RestService.class);


    }

   private static void initialYellowRestClient(){
       RestAdapter.Builder builder = new RestAdapter.Builder().setEndpoint(BASE_URL_YELLOWPAGE)
               .setLogLevel(RestAdapter.LogLevel.FULL)
               .setRequestInterceptor(new RequestInterceptor() {
                   @Override
                   public void intercept(RequestFacade request) {
                       request.addHeader("Accept", "application/json");
                   }
               });

       RestAdapter adapter = builder.build();
       yellowPageService = adapter.create(YellowPageService.class);
   }



}
