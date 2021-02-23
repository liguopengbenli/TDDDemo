package com.lig.intermediate.unittesting.di;

import android.app.Application;

import com.lig.intermediate.unittesting.BaseApplication;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

@Singleton //necessary because the dependency inside the module are marked Singleton
@Component(
        modules = {
                AndroidInjectionModule.class, // necessary for generating code
                AppModule.class,
                ActivityBuilderModule.class,
                ViewModelFactoryModule.class
        }
)

/*
*   We inject base application into this component
*   (base application is client, AppComponent is the server)
* */

public interface AppComponent extends AndroidInjector<BaseApplication> {

    @Component.Builder
    interface Builder{

        // after @BindsInstance, it will be available in baseApplication
        // Binding the application object with appcomponent, we can access it in all the module

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}
