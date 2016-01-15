package com.bukit.mygeosystem.Dagger;

import com.bukit.mygeosystem.BusinessListPresenter;

import dagger.Component;

/**
 * Created by kevin on 12/10/2015.
 */
@PerFrag
@Component
        (dependencies = MainActivityComponet.class)
public interface BusinessComponent {

    void inject(BusinessListPresenter presenter);
}
