package com.lig.intermediate.unittesting.repository;

import com.lig.intermediate.unittesting.model.Note;
import com.lig.intermediate.unittesting.persistence.NoteDao;
import com.lig.intermediate.unittesting.ui.Resource;
import com.lig.intermediate.unittesting.util.TestUtil;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import io.reactivex.Single;

import static com.lig.intermediate.unittesting.repository.NoteRepository.INSERT_FAILURE;
import static com.lig.intermediate.unittesting.repository.NoteRepository.INSERT_SUCCESS;
import static com.lig.intermediate.unittesting.repository.NoteRepository.NOTE_TITLE_NULL;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

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


}