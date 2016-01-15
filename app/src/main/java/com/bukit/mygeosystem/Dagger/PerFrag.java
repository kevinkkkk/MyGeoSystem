package com.bukit.mygeosystem.Dagger;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by kevin on 12/7/2015.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerFrag {
}
