package com.shirish.noteapp;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;

import com.shirish.noteapp.dao.NoteDao;
import com.shirish.noteapp.database.NotesDatabase;
import com.shirish.noteapp.model.Note;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class NoteReadWriteTest {
    private NoteDao mNoteDao;
    private NotesDatabase mDb;
    private Context context;

    @Before
    public void createDb() {
        context = InstrumentationRegistry.getTargetContext();
        mNoteDao = NotesDatabase.getDbInstance(context).getNoteDao();
    }

    @After
    public void closeDb() throws IOException {
        NotesDatabase.getDbInstance(context).close();
    }

    @Test
    public void writeUserAndReadInList() throws Exception {
        List<Note> testNotesList = new ArrayList<Note>();

        testNotesList.add(new Note("Test_1", "A", new Date().getTime(), false));
        testNotesList.add(new Note("Test_2", "A", new Date().getTime(), false));
        testNotesList.add(new Note("Test_3", "B", new Date().getTime(), false));

        for (Note testNote: testNotesList) {
            mNoteDao.insertNote(testNote);
        }

        List<Note> newNotes = mNoteDao.getAllNotes();

        assertThat(newNotes.size(), is(testNotesList.size()));
    }
}