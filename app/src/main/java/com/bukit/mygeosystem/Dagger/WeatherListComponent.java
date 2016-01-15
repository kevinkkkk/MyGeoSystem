package com.bukit.mygeosystem.Dagger;


import com.bukit.mygeosystem.WeatherListPresenter;

import dagger.Component;

/**
 * Created by kevin on 12/9/2015.
 */
@PerFrag
@Component
        (dependencies = MainActivityComponet.class)
public interface WeatherListComponent {

    void inject(WeatherListPresenter weatherListPresenter);
}
