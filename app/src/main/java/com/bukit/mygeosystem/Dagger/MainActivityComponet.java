package com.bukit.mygeosystem.Dagger;


import android.location.Location;
import com.bukit.mygeosystem.FetchGeoInfo;
import javax.inject.Singleton;
import dagger.Component;

/**
 * Created by kevin on 12/5/2015.
 */
@Singleton
@Component
        (modules = MainActivityModule.class)

public interface MainActivityComponet {

       //WeatherComponent plus();

    Location getLocation();

    FetchGeoInfo.ServerBinder getBinder();


}
