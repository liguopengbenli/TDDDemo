package com.lig.intermediate.unittesting.di;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.lig.intermediate.unittesting.ui.note.NoteViewModel;
import com.lig.intermediate.unittesting.viewmodels.ViewModelProviderFactory;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;

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


    @Binds
    @IntoMap
    @ViewModelKey(NoteViewModel.class)
    public abstract ViewModel bindNoteViewModel(NoteViewModel noteViewModel);


}
