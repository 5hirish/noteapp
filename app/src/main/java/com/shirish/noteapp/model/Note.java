package com.shirish.noteapp.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


@Entity(tableName = "note_data")
public class Note {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "note_id")
    private int noteId;

    @ColumnInfo(name = "note_content")
    @NonNull
    private String noteContent;

    @ColumnInfo(name = "note_category")
    @NonNull
    private String noteCategory;

    @ColumnInfo(name = "note_created_at")
    private long createdAt;

    @ColumnInfo(name = "note_checked")
    private boolean noteChecked;


    public Note(@NonNull String noteContent, @NonNull String noteCategory, long createdAt, boolean noteChecked) {
        this.noteContent = noteContent;
        this.noteCategory = noteCategory;
        this.createdAt = createdAt;
        this.noteChecked = noteChecked;
    }

    public Note() {

    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    @NonNull
    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(@NonNull String noteContent) {
        this.noteContent = noteContent;
    }

    @NonNull
    public String getNoteCategory() {
        return noteCategory;
    }

    public void setNoteCategory(@NonNull String noteCategory) {
        this.noteCategory = noteCategory;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isNoteChecked() {
        return noteChecked;
    }

    public void setNoteChecked(boolean noteChecked) {
        this.noteChecked = noteChecked;
    }
}
