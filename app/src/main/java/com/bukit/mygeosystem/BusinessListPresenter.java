package com.bukit.mygeosystem;

import android.location.Location;

import com.bukit.mygeosystem.Dagger.BusinessComponent;
import com.bukit.mygeosystem.Dagger.DaggerBusinessComponent;
import com.bukit.mygeosystem.ModeWeather.FiveDaysForecast;
import com.bukit.mygeosystem.ModeWeather.OpenWeather;
import com.bukit.mygeosystem.ModeYellowPage.YellowPagesReturn;

import javax.inject.Inject;

/**
 * Created by kevin on 12/10/2015.
 */
public class BusinessListPresenter implements FetchGeoInfo.OnDataReadyListener{
    WeatherView myView;
    BusinessComponent businessComponent;
    @Inject Location location;
    @Inject FetchGeoInfo.ServerBinder binder;

    public BusinessListPresenter(WeatherView myView){
        this.myView=myView;
        injectDJ();
    }

    public void loadData(String keyWords){
        binder.getAroundBusiness(location,keyWords,5,this);
    }

    private void injectDJ(){
        businessComponent = DaggerBusinessComponent.builder().mainActivityComponet(MainActivity.mainActivityComponet).build();
        businessComponent.inject(this);
    }

    @Override
    public void onWeatherDataReady(OpenWeather o) {

    }

    @Override
    public void onForecastDataReady(FiveDaysForecast f) {

    }

    @Override
    public void onYellowPageDataready(YellowPagesReturn y) {
        if (y==null){
            myView.showError();
        }else {
            myView.setData(y);
        }
    }
}
