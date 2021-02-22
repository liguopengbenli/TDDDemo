package com.lig.intermediate.unittesting.di;

import android.app.Application;

import com.lig.intermediate.unittesting.BaseApplication;

import dagger.Binds;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

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
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}
