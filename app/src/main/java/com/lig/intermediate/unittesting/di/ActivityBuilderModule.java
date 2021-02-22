package com.lig.intermediate.unittesting.di;

import com.lig.intermediate.unittesting.NotesListActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilderModule {
    @ContributesAndroidInjector
    abstract NotesListActivity contributeNotesListActivity();
}
