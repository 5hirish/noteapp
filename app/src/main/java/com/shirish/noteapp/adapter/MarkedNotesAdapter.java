package com.shirish.noteapp.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.shirish.noteapp.R;
import com.shirish.noteapp.model.Note;

import java.util.List;

public class MarkedNotesAdapter extends RecyclerView.Adapter<MarkedNotesAdapter.NoteViewHolder> {

    private List<Note> noteList;
    private OnCheckChangedListener checkedChangeListener;
    private Context context;

    public MarkedNotesAdapter(List<Note> noteList, OnCheckChangedListener checkedChangeListener, Context context) {
        this.noteList = noteList;
        this.checkedChangeListener = checkedChangeListener;
        this.context = context;
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NoteViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final NoteViewHolder holder, int position) {
        final Note note = noteList.get(position);
        holder.cbNoteStatus.setChecked(note.isNoteChecked());
        holder.tvNoteContent.setText(note.getNoteContent());
        holder.tvNoteCategory.setText(note.getNoteCategory());

        holder.tvNoteContent.setPaintFlags(holder.tvNoteContent.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        holder.cbNoteStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkedChangeListener.onCheckboxCheckedUnMarkNote(note);
            }
        });
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public void updateAdapter(List<Note> noteList) {
        this.noteList = noteList;
        notifyDataSetChanged();
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder {

        private TextView tvNoteContent, tvNoteCategory;
        private AppCompatCheckBox cbNoteStatus;

        NoteViewHolder(View view) {
            super(view);

            cbNoteStatus = (AppCompatCheckBox) view.findViewById(R.id.cbNoteMarked);
            tvNoteContent = (TextView) view.findViewById(R.id.tvNoteContent);
            tvNoteCategory = (TextView) view.findViewById(R.id.tvNoteCategory);
        }
    }

    public interface OnCheckChangedListener {
        void onCheckboxCheckedUnMarkNote(Note updateNote);
    }
}
