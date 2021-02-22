package com.lig.intermediate.unittesting.di;

import dagger.Module;
import dagger.Provides;

/*
* We don't declare this one as abstract
* We put all the application level dependency (live lifetime of app) in this module
* ex; retrofit instance, glide instance
*
* */

@Module
public class AppModule {
    @Provides
    static  String example1(){
        return "guopeng inject a test2 string";
    }
}
