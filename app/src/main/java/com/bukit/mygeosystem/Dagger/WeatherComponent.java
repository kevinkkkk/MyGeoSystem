package com.bukit.mygeosystem.Dagger;


import com.bukit.mygeosystem.WeatherPresenter;
import dagger.Component;


/**
 * Created by kevin on 12/7/2015.
 */


@PerFrag
@Component
        (dependencies = MainActivityComponet.class)

public interface WeatherComponent {

    void inject(WeatherPresenter weatherPresenter);

}
