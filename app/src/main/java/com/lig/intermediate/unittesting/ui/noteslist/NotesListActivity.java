package com.lig.intermediate.unittesting.ui.noteslist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.lig.intermediate.unittesting.R;
import com.lig.intermediate.unittesting.repository.NoteRepository;
import com.lig.intermediate.unittesting.ui.note.NoteActivity;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class NotesListActivity extends DaggerAppCompatActivity {
    private static  final String TAG = "NotesListActivity";

    @Inject
    String guopengString;

    @Inject
    NoteRepository noteRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);

        //Log.d(TAG, "on create :" + guopengString);
        //Log.d(TAG, "on create :" + noteRepository);
        Intent intent = new Intent(this, NoteActivity.class);
        startActivity(intent);

    }
}