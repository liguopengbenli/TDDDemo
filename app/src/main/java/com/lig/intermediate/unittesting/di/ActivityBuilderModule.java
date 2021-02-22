package com.lig.intermediate.unittesting.di;

import com.lig.intermediate.unittesting.NotesListActivity;

import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;


/* Here we tell dagger NotesListActivity is potential client that we can inject into

*  So we add all the activities here to declare before injection
*/
@Module
public abstract class ActivityBuilderModule {
    @ContributesAndroidInjector
    abstract NotesListActivity contributeNotesListActivity();

    /*
      // we can add here but keep organized we add in app module
    @Provides
    static  String example1(){
        return "guopeng inject a test string";
    }*/


}
