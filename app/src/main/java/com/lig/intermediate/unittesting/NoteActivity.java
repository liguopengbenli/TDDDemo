package com.lig.intermediate.unittesting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import dagger.android.support.DaggerAppCompatActivity;

public class NoteActivity extends DaggerAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
    }
}