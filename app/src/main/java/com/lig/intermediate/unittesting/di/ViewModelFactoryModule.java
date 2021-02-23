package com.lig.intermediate.unittesting.di;

import androidx.lifecycle.ViewModelProvider;

import com.lig.intermediate.unittesting.viewmodels.ViewModelProviderFactory;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class ViewModelFactoryModule {
    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelProviderFactory viewModelProviderFactory);

    /* * same as

      @Provides
      static ViewModelProvider.Factory bindfactory(ViewModelProviderFactory factory){
        return  factory;
    }

    * */

}
