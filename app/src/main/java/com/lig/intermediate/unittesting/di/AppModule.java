package com.lig.intermediate.unittesting.di;

import android.app.Application;

import androidx.room.Room;

import com.lig.intermediate.unittesting.persistence.NoteDao;
import com.lig.intermediate.unittesting.persistence.NoteDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static com.lig.intermediate.unittesting.persistence.NoteDatabase.DATABASE_NAME;

/*
* We don't declare this one as abstract
* We put all the application level dependency (live lifetime of app) in this module
* ex; retrofit instance, glide instance
*
* */

@Module
public class AppModule {

    @Singleton
    @Provides
    static NoteDatabase provideNoteDatabase(Application application){
        return Room.databaseBuilder(
                application,
                NoteDatabase.class,
                DATABASE_NAME
        ).build();
    }

    @Singleton
    @Provides
    static NoteDao provideNoteDao(NoteDatabase noteDatabase){
        return noteDatabase.getNoteDao();
    }




    //------------------ Simple exemples-------------------------//
    @Provides
    static  String example1(){
        return "guopeng inject a test2 string";
    }

    @Provides
    static boolean getApp(Application application){
        return application != null; // we access application object because this module inside appComponent
    }

    @Provides
    static boolean example2(String string){
        return string.equals("guopeng inject a test2 string"); // it will return true because this string is as dependency inside the module
    }

}
