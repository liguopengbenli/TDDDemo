package com.lig.intermediate.unittesting.ui.note;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lig.intermediate.unittesting.model.Note;
import com.lig.intermediate.unittesting.repository.NoteRepository;
import com.lig.intermediate.unittesting.ui.Resource;

import javax.inject.Inject;

import static com.lig.intermediate.unittesting.repository.NoteRepository.NOTE_TITLE_NULL;

public class noteViewModel extends ViewModel {
    private static final String TAG = "NoteViewModel";

    // inject
    private final NoteRepository noteRepository;

    // vars
    private MutableLiveData<Note> note = new MutableLiveData<>();

    @Inject
    public noteViewModel(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public LiveData<Resource<Integer>> insertNote() throws Exception{
        /*
        * Convert Rxjava to liveData
        * */
            return LiveDataReactiveStreams.fromPublisher(
                    noteRepository.insertNote(note.getValue())
            );
    }

    
    public LiveData<Note> observeNote(){
        return note;
    }

    public void setNote(Note note) throws Exception{
        if(note.getTitle() == null || note.getTitle().equals("")){
            throw new Exception(NOTE_TITLE_NULL);
        }
        this.note.setValue(note);
        
    }

}