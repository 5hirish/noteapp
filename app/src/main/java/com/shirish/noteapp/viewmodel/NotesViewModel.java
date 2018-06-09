package com.shirish.noteapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.shirish.noteapp.database.NotesDatabase;
import com.shirish.noteapp.model.Note;

import java.util.List;

public class NotesViewModel extends AndroidViewModel{

    private final LiveData<List<Note>> notesListMarked;
    private final LiveData<List<Note>> notesListUnMarked;

    private NotesDatabase notesDatabase;

    public NotesViewModel(@NonNull Application application) {
        super(application);

        notesDatabase = NotesDatabase.getDbInstance(this.getApplication());

        notesListMarked = notesDatabase.getNoteDao().getAllMarkedNotes();
        notesListUnMarked = notesDatabase.getNoteDao().getAllUnMarkedNotes();
    }

    public LiveData<List<Note>> getNotesListMarked() {
        return notesListMarked;
    }

    public LiveData<List<Note>> getNotesListUnMarked() {
        return notesListUnMarked;
    }

    public void addNote(Note note) {
        //return notesDatabase.getNoteDao().insertNote(note);
        new insertNoteAsyncTask(notesDatabase).execute(note);
    }

    public void markNote(Note markedNote) {
        //return notesDatabase.getNoteDao().updateNote(markedNote);
        new updateNoteAsyncTask(notesDatabase).execute(markedNote);
    }

    private static class insertNoteAsyncTask extends AsyncTask<Note, Void, Long> {

        private NotesDatabase notesDatabase;

        insertNoteAsyncTask(NotesDatabase notesDatabase) {
            this.notesDatabase = notesDatabase;
        }

        @Override
        protected Long doInBackground(final Note... notes) {
            return notesDatabase.getNoteDao().insertNote(notes[0]);
        }

    }

    private static class updateNoteAsyncTask extends AsyncTask<Note, Void, Integer> {

        private NotesDatabase notesDatabase;

        updateNoteAsyncTask(NotesDatabase notesDatabase) {
            this.notesDatabase = notesDatabase;
        }

        @Override
        protected Integer doInBackground(final Note... notes) {
            return notesDatabase.getNoteDao().updateNote(notes[0]);
        }

    }

}
