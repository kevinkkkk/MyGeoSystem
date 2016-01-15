package com.bukit.mygeosystem;

import android.location.Location;



import com.bukit.mygeosystem.Dagger.DaggerWeatherListComponent;
import com.bukit.mygeosystem.Dagger.WeatherListComponent;
import com.bukit.mygeosystem.ModeWeather.FiveDaysForecast;
import com.bukit.mygeosystem.ModeWeather.OpenWeather;
import com.bukit.mygeosystem.ModeYellowPage.YellowPagesReturn;

import javax.inject.Inject;

/**
 * Created by kevin on 12/9/2015.
 */
public class WeatherListPresenter implements FetchGeoInfo.OnDataReadyListener {

    WeatherView myView;
    WeatherListComponent weatherListComponent;
    @Inject Location location;
    @Inject FetchGeoInfo.ServerBinder binder;

    public WeatherListPresenter(WeatherView myView){
        this.myView=myView;
        injectDJ();
    }

    public void loadData(){
        binder.getForecastInfo(location, this);
    }


    @Override
    public void onWeatherDataReady(OpenWeather o) {

    }

    @Override
    public void onForecastDataReady(FiveDaysForecast f) {
        if (f==null){
            myView.showError();
        }else{
            myView.setData(f);

        }
    }

    @Override
    public void onYellowPageDataready(YellowPagesReturn y) {

    }

    private void injectDJ(){
        weatherListComponent= DaggerWeatherListComponent.builder()
                .mainActivityComponet(MainActivity.mainActivityComponet).build();
        weatherListComponent.inject(this);
    }
}
