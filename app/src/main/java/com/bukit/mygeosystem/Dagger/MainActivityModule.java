package com.bukit.mygeosystem.Dagger;


import android.location.Location;
import com.bukit.mygeosystem.FetchGeoInfo;
import com.bukit.mygeosystem.MainActivity;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

/**
 * Created by kevin on 12/5/2015.
 */
@Module
public class MainActivityModule {

    private MainActivity mainActivity;

    public MainActivityModule(MainActivity a){
        this.mainActivity=a;
    }

    @Provides
    @Singleton
    public Location providerLocation(){
        return mainActivity.getMyLocation();
    }

   @Provides
    @Singleton
    public FetchGeoInfo.ServerBinder providerBinder(){return mainActivity.getBinder();}

}
