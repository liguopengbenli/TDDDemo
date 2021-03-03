package com.lig.intermediate.unittesting.repository;

import androidx.lifecycle.MutableLiveData;

import com.lig.intermediate.unittesting.model.Note;
import com.lig.intermediate.unittesting.persistence.NoteDao;
import com.lig.intermediate.unittesting.ui.Resource;
import com.lig.intermediate.unittesting.util.InstantExecutorExtension;
import com.lig.intermediate.unittesting.util.LiveDataTestUtil;
import com.lig.intermediate.unittesting.util.TestUtil;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

import static com.lig.intermediate.unittesting.repository.NoteRepository.DELETE_FAILURE;
import static com.lig.intermediate.unittesting.repository.NoteRepository.DELETE_SUCCESS;
import static com.lig.intermediate.unittesting.repository.NoteRepository.INSERT_FAILURE;
import static com.lig.intermediate.unittesting.repository.NoteRepository.INSERT_SUCCESS;
import static com.lig.intermediate.unittesting.repository.NoteRepository.INVALID_NOTE_ID;
import static com.lig.intermediate.unittesting.repository.NoteRepository.NOTE_TITLE_NULL;
import static com.lig.intermediate.unittesting.repository.NoteRepository.UPDATE_FAILURE;
import static com.lig.intermediate.unittesting.repository.NoteRepository.UPDATE_SUCCESS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;


@ExtendWith(InstantExecutorExtension.class)
public class NoteRepositoryTest {
    private  static final Note NOTE1 = new Note(TestUtil.TEST_NOTE_1);
    private NoteRepository noteRepository;

    @Mock
    private NoteDao noteDao;

    // we want new ref before each test
    @BeforeEach
    public void initEach(){
        // MockitoAnnotations.initMocks(this); init all the mock class
        noteDao = Mockito.mock(NoteDao.class);
        noteRepository = new NoteRepository(noteDao);
    }

    /*@BeforeAll
    public void init(){

    }*/

    @Test
    void dummTest() throws  Exception {
        assertNotNull(noteDao);
        assertNotNull(noteRepository);
    }


    /*
        insert note
        verify the correct method is called
        confirm observer is triggered
        confirm new rows inserted
     */
    @Test
    void insertNote_returnRow() throws Exception {
        // Arrange
        final Long insertedRow = 1L;
        final Single<Long> returnedData = Single.just(insertedRow);
        when(noteDao.insertNote(any(Note.class))).thenReturn(returnedData);

        // Act
        final Resource<Integer> returnedValue = noteRepository.insertNote(NOTE1).blockingFirst(); // wait until inserted

        // Assert
        verify(noteDao).insertNote(any(Note.class));
        verifyNoMoreInteractions(noteDao);

        System.out.println("Returned value: " + returnedValue.data);
        assertEquals(Resource.success(1, INSERT_SUCCESS), returnedValue);

        // or test using Rxjava (another way to do it)
//        noteRepository.insertNote(NOTE1)
//                .test()
//                .await()
//                .assertValue(Resource.success(1, INSERT_SUCCESS));
    }


    /*
        Insert note
        Failure (return -1)
     */

    @Test
    void insertNote_returnFailure() throws Exception {
        // Arrange
        final Long failedInsert = -1L;
        final Single<Long> returnedData = Single.just(failedInsert);
        when(noteDao.insertNote(any(Note.class))).thenReturn(returnedData);

        // Act
        final Resource<Integer> returnedValue = noteRepository.insertNote(NOTE1).blockingFirst(); // wait until inserted

        // Assert
        verify(noteDao).insertNote(any(Note.class));
        verifyNoMoreInteractions(noteDao);

        assertEquals(Resource.error(null, INSERT_FAILURE), returnedValue);
    }


    /*
        insert note
        null title
        confirm throw exception
     */
    @Test
    void insertNote_nullTitle_throwException() throws Exception {
     Exception exception =  assertThrows(Exception.class, new Executable() {
         @Override
         public void execute() throws Throwable {
             final Note note = new Note(TestUtil.TEST_NOTE_1);
             note.setTitle(null);
             noteRepository.insertNote(note);
         }
     });

     assertEquals(NOTE_TITLE_NULL, exception.getMessage());
    }

      /*
        update note
        verify correct method is called
        confirm observer is trigger
        confirm number of rows updated
     */
      @Test
      void updateNote_returnNumRowsUpdated() throws Exception {
          //Arrange
          final int updateRow = 1;
          when(noteDao.updateNote(any(Note.class))).thenReturn(Single.just(updateRow));

          //Act
          final Resource<Integer> returnedValue = noteRepository.updateNote(NOTE1).blockingFirst();

          //Assert
          verify(noteDao).updateNote(any(Note.class));
          verifyNoMoreInteractions(noteDao);
          assertEquals(Resource.success(updateRow, UPDATE_SUCCESS), returnedValue);
      }


    /*
        update note
        Failure (-1)
     */

    @Test
    void updateNote_returnFailure() throws Exception {
        // Arrange
        final int failedInsert = -1;
        final Single<Integer> returnedData = Single.just(failedInsert);
        when(noteDao.updateNote(any(Note.class))).thenReturn(returnedData);

        // Act
        final Resource<Integer> returnedValue = noteRepository.updateNote(NOTE1).blockingFirst();

        // Assert
        verify(noteDao).updateNote(any(Note.class));
        verifyNoMoreInteractions(noteDao);

        assertEquals(Resource.error(null, UPDATE_FAILURE), returnedValue);
    }

      /*
        update note
        null title
        throw exception
      */
      @Test
      void updateNote_nullTitle_throwException() throws Exception {

          Exception exception = assertThrows(Exception.class, new Executable() {
              @Override
              public void execute() throws Throwable {
                  final Note note  = new Note(TestUtil.TEST_NOTE_1);
                  note.setTitle(null);
                  noteRepository.updateNote(note);
              }
          });

          assertEquals(NOTE_TITLE_NULL, exception.getMessage());
      }


      /*
        delete note
        null id
        throw exception
     */

    @Test
    void deleteNote_nullId_throwException() throws Exception {
        Exception exception = assertThrows(Exception.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                final Note note = new Note(TestUtil.TEST_NOTE_1);
                note.setId(-1);
                noteRepository.deleteNote(note);
            }
        });

        assertEquals(INVALID_NOTE_ID, exception.getMessage());
    }

    /*
        delete note
        delete success
        return Resource.success with deleted row
     */

    @Test
    void deleteNote_deleteSuccess_returnResourceSuccess() throws Exception {
        // Arrange
        final int deletedRow = 1;
        Resource<Integer> successResponse = Resource.success(deletedRow, DELETE_SUCCESS);
        LiveDataTestUtil<Resource<Integer>> liveDataTestUtil = new LiveDataTestUtil<>();
        when(noteDao.deleteNote(any(Note.class))).thenReturn(Single.just(deletedRow));

        // Act
        Resource<Integer> observedResponse = liveDataTestUtil.getValue(noteRepository.deleteNote(NOTE1));

        // Assert
        assertEquals(successResponse, observedResponse);
    }


    /*
        delete note
        delete failure
        return Resource.error
     */
    @Test
    void deleteNote_deleteFailure_returnResourceError() throws Exception {
        // Arrange
        final int deletedRow = -1;
        Resource<Integer> errorResponse = Resource.error(null, DELETE_FAILURE);
        LiveDataTestUtil<Resource<Integer>> liveDataTestUtil = new LiveDataTestUtil<>();
        when(noteDao.deleteNote(any(Note.class))).thenReturn(Single.just(deletedRow));

        // Act
        Resource<Integer> observedResponse = liveDataTestUtil.getValue(noteRepository.deleteNote(NOTE1));

        // Assert
        assertEquals(errorResponse, observedResponse);
    }


    /*
        retrieve notes
        return list of notes
     */

    @Test
    void getNotes_returnListWithNotes() throws Exception {
        // Arrange
        List<Note> notes = TestUtil.TEST_NOTES_LIST;
        LiveDataTestUtil<List<Note>> liveDataTestUtil = new LiveDataTestUtil<>();
        MutableLiveData<List<Note>> returnedData = new MutableLiveData<>();
        returnedData.setValue(notes);
        when(noteDao.getNotes()).thenReturn(returnedData);

        // Act
        List<Note> observedData = liveDataTestUtil.getValue(noteRepository.getNotes());

        // Assert
        assertEquals(notes, observedData);
    }

    /*
        retrieve notes
        return empty list
     */

    @Test
    void getNotes_returnEmptyList() throws Exception {
        // Arrange
        List<Note> notes = new ArrayList<>();
        LiveDataTestUtil<List<Note>> liveDataTestUtil = new LiveDataTestUtil<>();
        MutableLiveData<List<Note>> returnedData = new MutableLiveData<>();
        returnedData.setValue(notes);
        when(noteDao.getNotes()).thenReturn(returnedData);

        // Act
        List<Note> observedData = liveDataTestUtil.getValue(noteRepository.getNotes());

        // Assert
        assertEquals(notes, observedData);
    }



}