package com.bukit.mygeosystem;

import android.location.Location;


import com.bukit.mygeosystem.Dagger.DaggerWeatherComponent;
import com.bukit.mygeosystem.Dagger.WeatherComponent;
import com.bukit.mygeosystem.ModeWeather.FiveDaysForecast;
import com.bukit.mygeosystem.ModeWeather.OpenWeather;
import com.bukit.mygeosystem.ModeYellowPage.YellowPagesReturn;

import javax.inject.Inject;

/**
 * Created by kevin on 12/7/2015.
 */
public class WeatherPresenter implements FetchGeoInfo.OnDataReadyListener{

    @Inject Location location;
    @Inject FetchGeoInfo.ServerBinder binder;

    WeatherView myView;
    WeatherComponent weatherComponent;

    public WeatherPresenter(WeatherView myView){
        this.myView = myView;
        injectDJ();
    }

    public void loadData(){

        binder.getWeatherInfo(location, this);

    }

    private void injectDJ(){

        //weatherComponent=((MainActivity)((WeatherFrag)myView).getActivity()).mainActivityComponet.plus();
        //weatherComponent.inject(this);

        weatherComponent= DaggerWeatherComponent.builder().mainActivityComponet(MainActivity.mainActivityComponet).build();
        weatherComponent.inject(this);

    }

    @Override
    public void onWeatherDataReady(OpenWeather w) {

        if (w == null) {
            myView.showError();
        } else {
            myView.setData(w);
        }
    }

    @Override
    public void onForecastDataReady(FiveDaysForecast f) {

    }

    @Override
    public void onYellowPageDataready(YellowPagesReturn y) {

    }
}
