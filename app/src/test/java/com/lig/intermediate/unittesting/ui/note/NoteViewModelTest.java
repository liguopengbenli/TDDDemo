package com.lig.intermediate.unittesting.ui.note;

import androidx.lifecycle.LiveData;

import com.lig.intermediate.unittesting.model.Note;
import com.lig.intermediate.unittesting.repository.NoteRepository;
import com.lig.intermediate.unittesting.ui.Resource;
import com.lig.intermediate.unittesting.util.InstantExecutorExtension;
import com.lig.intermediate.unittesting.util.LiveDataTestUtil;
import com.lig.intermediate.unittesting.util.TestUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import io.reactivex.Flowable;
import io.reactivex.internal.operators.single.SingleToFlowable;

import static com.lig.intermediate.unittesting.repository.NoteRepository.INSERT_SUCCESS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;


@ExtendWith(InstantExecutorExtension.class)
class NoteViewModelTest {

    private NoteViewModel noteViewModel;

    @Mock
    private NoteRepository noteRepository;

    @BeforeEach
    public void init(){
        MockitoAnnotations.initMocks(this);
        noteViewModel = new NoteViewModel(noteRepository);
    }

      /*
        can't observe a note that hasn't been set
     */

    @Test
    void observeEmptyNoteWhenNoteSet() throws Exception {
        // Arrange
        LiveDataTestUtil<Note> liveDataTestUtil = new LiveDataTestUtil<>();

        // Act
        Note note = liveDataTestUtil.getValue(noteViewModel.observeNote());

        // Assert
        assertNull(note);
    }

    /*
        Observe a note has been set and onChanged will trigger in activity
     */
    @Test
    void observeNote_whenSet() throws Exception {
        // Arrange
        Note note = new Note(TestUtil.TEST_NOTE_1);
        LiveDataTestUtil<Note> liveDataTestUtil = new LiveDataTestUtil<>();

        // Act
        noteViewModel.setNote(note);
        Note observableNote = liveDataTestUtil.getValue(noteViewModel.observeNote());

        // Assert
        assertEquals(note, observableNote);
    }

    /*
        Insert a new note and observe row returned
     */
    @Test
    void insertNote_returnRow() throws Exception {
        // Arrange
        Note note = new Note(TestUtil.TEST_NOTE_1);
        LiveDataTestUtil<Resource<Integer>> liveDataTestUtil = new LiveDataTestUtil<>();
        final int insertedRow = 1;
        Flowable<Resource<Integer>> returnedData = SingleToFlowable.just(Resource.success(insertedRow, INSERT_SUCCESS));
        Mockito.when(noteRepository.insertNote(any(Note.class))).thenReturn(returnedData);

        // Act
        noteViewModel.setNote(note);
        Resource<Integer> returnedValue = liveDataTestUtil.getValue(noteViewModel.insertNote());

        // Assert
        assertEquals(Resource.success(insertedRow, INSERT_SUCCESS), returnedValue);
    }

    /*
        insert: dont return a new row without observer
     */
    @Test
    void dontReturnInsertRowWithoutObserver() throws Exception {
        // Arrange
        Note note = new Note(TestUtil.TEST_NOTE_1);

        // Act
        noteViewModel.setNote(note);

        // Assert
        verify(noteRepository, never()).insertNote(any(Note.class)); // amazing mockito test to verify a method called how many times
    }

    /*
        set note, null title, throw exception
     */

    @Test
    void setNote_nullTitle_throwException() throws Exception {
        // Arrange
        final Note note = new Note(TestUtil.TEST_NOTE_1);
        note.setTitle(null);

        // Assert
        assertThrows(Exception.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                noteViewModel.setNote(note);
            }
        });

    }


}