package com.bukit.mygeosystem;


import android.app.Service;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Binder;

import android.os.IBinder;

import android.support.annotation.Nullable;
import android.widget.Toast;


import com.bukit.mygeosystem.ModeWeather.FiveDaysForecast;
import com.bukit.mygeosystem.ModeWeather.OpenWeather;
import com.bukit.mygeosystem.ModeYellowPage.YellowPagesReturn;
import com.bukit.mygeosystem.REST.RestClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by kevin on 12/4/2015.
 */
public class FetchGeoInfo extends Service {

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */

    final static String API_KEY_WEATHER="4b55015444eb5eca3fd36417b78c060f";
    final static String API_KEY_YELLOW="e29ssyddfm3ag52my8dsv896";
    final static String UNITS = "metric";



   public class ServerBinder extends Binder{

        /*public ServerBinder getServerBinder(){
            return this;
        }*/

        public String getLocationInfo(Location location){


            List<Address> addressList = new ArrayList<>();
            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

            try {
                addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(),1);

            } catch (IOException e) {
                e.printStackTrace();
            }

            if (addressList==null || addressList.size()==0){
                return ("Error!!!");
            }else{
                Address address = addressList.get(0);
                StringBuilder returnAddress = new StringBuilder();
                for (int i=0; i<address.getMaxAddressLineIndex(); i++){
                    returnAddress.append(address.getAddressLine(i)+"\n");
                }
                return returnAddress.toString();
            }

        }

        public void getWeatherInfo(Location location, final OnDataReadyListener listener){

            Double lat = location.getLatitude();
            Double lot = location.getLongitude();
            RestClient.getWeatherApi().getWeather(API_KEY_WEATHER,lot,lat, UNITS, new Callback<OpenWeather>() {
                @Override
                public void success(OpenWeather openWeather, Response response) {

                    listener.onWeatherDataReady(openWeather);
                }

                @Override
                public void failure(RetrofitError error) {
                    //Toast.makeText(getApplicationContext(),error.toString(), Toast.LENGTH_LONG).show();


                }


            });

        }

        public void getForecastInfo(Location location, final OnDataReadyListener listener1){

            Double lat = location.getLatitude();
            Double lon = location.getLongitude();
            RestClient.getWeatherApi().getForecast(API_KEY_WEATHER, lon, lat, UNITS, new Callback<FiveDaysForecast>() {
                @Override
                public void success(FiveDaysForecast fiveDaysForecast, Response response) {
                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                    listener1.onForecastDataReady(fiveDaysForecast);
                }

                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();


                }
            });

        }

        public void getAroundBusiness(Location location, String business,int dist, final OnDataReadyListener listener2){
            Double lat =location.getLatitude();
            Double lon =location.getLongitude();
            String where="cZ"+lon+","+lat;
            RestClient.getYellowPageService().findAroundBusiness(business,where,"json",dist,40,API_KEY_YELLOW,0, new Callback<YellowPagesReturn>(){
                @Override
                public void success(YellowPagesReturn yellowPagesReturn, Response response) {
                    listener2.onYellowPageDataready(yellowPagesReturn);
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });
        }

    }

    public ServerBinder binder=new ServerBinder();




    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return this.binder;
    }

    public interface OnDataReadyListener{
        void onWeatherDataReady(OpenWeather o);
        void onForecastDataReady(FiveDaysForecast f);
        void onYellowPageDataready(YellowPagesReturn y);
    }

}
