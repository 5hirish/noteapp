package com.shirish.noteapp.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.shirish.noteapp.model.Note;

import java.util.List;

@Dao
public interface NoteDao {

    @Query("SELECT * FROM note_data")
    List<Note> getAllNotes();

    @Query("SELECT * FROM note_data WHERE note_id = :noteId LIMIT 1")
    Note getOneNote(int noteId);

    @Query("SELECT * FROM note_data WHERE note_checked = 1 ORDER BY note_created_at DESC")
    LiveData<List<Note>> getAllMarkedNotes();

    @Query("SELECT * FROM note_data WHERE note_checked = 0 ORDER BY note_created_at DESC")
    LiveData<List<Note>> getAllUnMarkedNotes();

    @Query("SELECT * FROM note_data WHERE note_category LIKE :noteCategory ORDER BY note_created_at DESC")
    LiveData<List<Note>> getAllNotesWithCategory(String noteCategory);

    @Insert
    long insertNote(Note noteData);

    @Update
    int updateNote(Note noteData);

}
