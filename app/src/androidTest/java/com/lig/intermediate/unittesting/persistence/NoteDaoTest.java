package com.lig.intermediate.unittesting.persistence;

import android.database.sqlite.SQLiteConstraintException;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;

import com.lig.intermediate.unittesting.model.Note;
import com.lig.intermediate.unittesting.util.LiveDataTestUtil;
import com.lig.intermediate.unittesting.util.TestUtil;

import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

// This code is reusable for room testing

public class NoteDaoTest extends NoteDatabaseTest {

    public static final String TEST_TITLE = "This is a test title"; // control + D -- copie ligne
    //public static final String TEST_TITLE = "This is a test title";
    public static final String TEST_CONTENT = "This is some test content";
    public static final String TEST_TIMESTAMP = "02-2021";

    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule(); // necessary config

    /*
        Insert, Read, Delete
     */
    @Test
    public void insertReadDelete() throws Exception{
        Note note = new Note(TestUtil.TEST_NOTE_1);

        // insert
        getNoteDao().insertNote(note).blockingGet();

        //read
        LiveDataTestUtil<List<Note>> liveDataTestUtil = new LiveDataTestUtil<>();
        List<Note> insertedNotes = liveDataTestUtil.getValue(getNoteDao().getNotes());

        assertNotNull(insertedNotes);
        assertEquals(note.getContent(), insertedNotes.get(0).getContent());
        assertEquals(note.getTimestamp(), insertedNotes.get(0).getTimestamp());
        assertEquals(note.getTitle(), insertedNotes.get(0).getTitle());


        note.setId(insertedNotes.get(0).getId());
        assertEquals(note, insertedNotes.get(0));

        //delete
        getNoteDao().deleteNote(note).blockingGet();

        //confirm db is empty
        insertedNotes = liveDataTestUtil.getValue(getNoteDao().getNotes());
        assertEquals(0, insertedNotes.size());
    }

    /*
        Insert, Read, Update, Read, Delete,
     */
    @Test
    public void insertReadUpdatedReadDelete() throws Exception{
        Note note = new Note(TestUtil.TEST_NOTE_1);

        // insert
        getNoteDao().insertNote(note).blockingGet();

        //read
        LiveDataTestUtil<List<Note>> liveDataTestUtil = new LiveDataTestUtil<>();
        List<Note> insertedNotes = liveDataTestUtil.getValue(getNoteDao().getNotes());
        assertNotNull(insertedNotes);

        assertEquals(note.getContent(), insertedNotes.get(0).getContent());
        assertEquals(note.getTimestamp(), insertedNotes.get(0).getTimestamp());
        assertEquals(note.getTitle(), insertedNotes.get(0).getTitle());


        note.setId(insertedNotes.get(0).getId());
        assertEquals(note, insertedNotes.get(0));

        // update
        note.setTitle(TEST_TITLE);
        note.setContent(TEST_CONTENT);
        note.setTimestamp(TEST_TIMESTAMP);
        getNoteDao().updateNote(note).blockingGet();

        // read
        insertedNotes = liveDataTestUtil.getValue(getNoteDao().getNotes());
        assertEquals(TEST_CONTENT, insertedNotes.get(0).getContent());
        assertEquals(TEST_TIMESTAMP, insertedNotes.get(0).getTimestamp());
        assertEquals(TEST_TITLE, insertedNotes.get(0).getTitle());
        note.setId(insertedNotes.get(0).getId());
        assertEquals(note, insertedNotes.get(0));

        //delete
        getNoteDao().deleteNote(note).blockingGet();

        //confirm db is empty
        insertedNotes = liveDataTestUtil.getValue(getNoteDao().getNotes());
        assertEquals(0, insertedNotes.size());
    }


    /*
        Insert note with null title, throw exception
     */

    @Test(expected = SQLiteConstraintException.class)
    public void insert_nullTitle_throwSQliteConstraintException() throws Exception{
        final Note note = new Note(TestUtil.TEST_NOTE_1);
        note.setTitle(null);
        //insert
        getNoteDao().insertNote(note).blockingGet();
    }


    /*
        Insert, Update with null title, throw exception
     */
    @Test(expected = SQLiteConstraintException.class)
    public void updateNote_nullTitle_throwSQliteConstraintException() throws Exception{
        Note note = new Note(TestUtil.TEST_NOTE_1);

        // insert
        getNoteDao().insertNote(note).blockingGet();

        //read
        LiveDataTestUtil<List<Note>> liveDataTestUtil = new LiveDataTestUtil<>();
        List<Note> insertedNotes = liveDataTestUtil.getValue(getNoteDao().getNotes());
        assertNotNull(insertedNotes);

        //update
        note = new Note(insertedNotes.get(0));
        note.setTitle(null);
        getNoteDao().updateNote(note).blockingGet();

    }


}