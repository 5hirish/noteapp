package com.shirish.noteapp.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.shirish.noteapp.dao.NoteDao;
import com.shirish.noteapp.model.Note;

@Database(entities = {Note.class}, version = 1)
public abstract class NotesDatabase extends RoomDatabase {

    private static final String DB_NAME = "note_app.db";
    private static volatile NotesDatabase dbInstance;

    // Single to avoid multiple expensive db instances
    public static synchronized NotesDatabase getDbInstance(Context context) {
        if (dbInstance == null) {
            dbInstance = createInstance(context);
        }
        return dbInstance;
    }

    private static NotesDatabase createInstance(final Context context) {
        return Room.databaseBuilder(context, NotesDatabase.class, DB_NAME).build();
    }

    public abstract NoteDao getNoteDao();
}
