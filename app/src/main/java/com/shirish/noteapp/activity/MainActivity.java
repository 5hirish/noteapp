package com.shirish.noteapp.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.shirish.noteapp.R;
import com.shirish.noteapp.adapter.MarkedNotesAdapter;
import com.shirish.noteapp.adapter.UnMarkedNotesAdapter;
import com.shirish.noteapp.database.NotesDatabase;
import com.shirish.noteapp.model.Note;
import com.shirish.noteapp.viewmodel.NotesViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements UnMarkedNotesAdapter.OnCheckChangedListener, MarkedNotesAdapter.OnCheckChangedListener {

    private NotesViewModel notesViewModel;
    private MarkedNotesAdapter markedNotesAdapter;
    private UnMarkedNotesAdapter unMarkedNotesAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /** Initializing the UI Components **/

        FloatingActionButton btnAddNote = findViewById(R.id.btnAddNote);
        final TextInputLayout tiNoteContent = findViewById(R.id.tiNoteContent);
        final TextInputEditText etNoteContent = findViewById(R.id.etNoteContent);
        final AppCompatSpinner spNoteCategory = findViewById(R.id.spNoteCategory);
        RecyclerView rvUnMarkedNotes = findViewById(R.id.rvNotePending);
        RecyclerView rvMarkedNotes = findViewById(R.id.rvNoteComplete);

        /** Setting up the adapters **/

        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(this,
                R.array.notes_category_array, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spNoteCategory.setAdapter(categoryAdapter);
        spNoteCategory.setSelection(0);

        unMarkedNotesAdapter = new UnMarkedNotesAdapter(new ArrayList<Note>(), this, this);
        rvUnMarkedNotes.setLayoutManager(new LinearLayoutManager(this));
        rvUnMarkedNotes.setAdapter(unMarkedNotesAdapter);
        rvUnMarkedNotes.setHasFixedSize(true);
        rvUnMarkedNotes.setNestedScrollingEnabled(false);
        rvUnMarkedNotes.setItemAnimator(new DefaultItemAnimator());

        markedNotesAdapter = new MarkedNotesAdapter(new ArrayList<Note>(), this, this);
        rvMarkedNotes.setLayoutManager(new LinearLayoutManager(this));
        rvMarkedNotes.setAdapter(markedNotesAdapter);
        rvMarkedNotes.setHasFixedSize(true);
        rvMarkedNotes.setNestedScrollingEnabled(false);
        rvMarkedNotes.setItemAnimator(new DefaultItemAnimator());

        // Setting the ViewModel to communicate between the view and model

        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);

        // Adding the observers to update the adapters on data change events

        notesViewModel.getNotesListUnMarked().observe(MainActivity.this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> noteList) {
                unMarkedNotesAdapter.updateAdapter(noteList);
            }
        });

        notesViewModel.getNotesListMarked().observe(MainActivity.this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> noteList) {
                markedNotesAdapter.updateAdapter(noteList);
            }
        });

        /** Setting up the listeners **/

        btnAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strNoteContent = etNoteContent.getText().toString();
                String strNoteCategory = spNoteCategory.getSelectedItem().toString();

                if (isNotEmpty(strNoteContent) && isNotEmpty(strNoteCategory)) {
                    tiNoteContent.setErrorEnabled(false);

                    Note newNote = new Note();
                    newNote.setNoteContent(strNoteContent);
                    newNote.setNoteCategory(strNoteCategory);
                    newNote.setCreatedAt(new Date().getTime());
                    newNote.setNoteChecked(false);

                    notesViewModel.addNote(newNote);

                    // Reset UI components to default state...
                    hideSoftKeyboard();
                    etNoteContent.setText("");
                    etNoteContent.clearFocus();
                    tiNoteContent.setHintEnabled(true);
                    spNoteCategory.setSelection(0);

                    Toast.makeText(getApplicationContext(),"Note Created...", Toast.LENGTH_SHORT).show();

                    //NotesDatabase.getDbInstance(getApplicationContext()).getNoteDao().insertNote(newNote);

                } else if (!isNotEmpty(strNoteContent)) {
                    tiNoteContent.setErrorEnabled(true);
                    tiNoteContent.setError("Add your note here...");
                } else if (!isNotEmpty(strNoteCategory)) {
                    tiNoteContent.setErrorEnabled(false);
                    Toast.makeText(getApplicationContext(),"Select a note category", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void hideSoftKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);


        }
    }

    private boolean isNotEmpty(String strData) {
        return strData != null && strData.length() > 0;
    }

    @Override
    public void onCheckboxCheckedMarkNote(Note updateNote) {

        updateNote.setNoteChecked(true);
        notesViewModel.markNote(updateNote);
    }

    @Override
    public void onCheckboxCheckedUnMarkNote(Note updateNote) {

        updateNote.setNoteChecked(false);
        notesViewModel.markNote(updateNote);
    }
}
