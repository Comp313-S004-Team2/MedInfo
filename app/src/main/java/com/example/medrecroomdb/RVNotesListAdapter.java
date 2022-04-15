package com.example.medrecroomdb;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.datastore.generated.model.Note;
import com.example.medrecroomdb.activity.DetailedNote;

import java.util.ArrayList;

public class RVNotesListAdapter extends RecyclerView.Adapter<RVNotesListAdapter.RVNotesViewHolder> {
    private ArrayList<Note> notes;
    private Context context;
    SharedPreferences notesListPreference;

    public RVNotesListAdapter(ArrayList<Note> notes, Context context) {
        this.notes = notes;
        this.context = context;
    }

    @NonNull
    @Override
    public RVNotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_card_view, parent, false);
        return new RVNotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RVNotesViewHolder holder, int position) {
        holder.tvCreatedOn.setText(notes.get(position).getCreatedOn());
        if(notes.get(position).getWriterRole().matches("Doctor")){
            holder.tvCreatedBy.setText("Dr. " + notes.get(position).getWriterName());
        }else{
            holder.tvCreatedBy.setText(notes.get(position).getWriterName());
        }
        holder.tvNote.setText(notes.get(position).getNote());
        holder.tvNoteId.setText(notes.get(position).getId());
        holder.cvNote.setOnClickListener(view -> {
            notesListPreference = context.getSharedPreferences("notesList", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = notesListPreference.edit();
            editor.putString("noteToView", notes.get(position).getId().toString());
            editor.commit();
            Intent noteIntent = new Intent(context, DetailedNote.class);
            context.startActivity(noteIntent);
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class RVNotesViewHolder extends RecyclerView.ViewHolder{

        TextView tvCreatedBy, tvCreatedOn, tvNote, tvNoteId;
        CardView cvNote;

        public RVNotesViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCreatedBy = itemView.findViewById(R.id.tvNotesCreatedBy);
            tvCreatedOn = itemView.findViewById(R.id.tvNotesCreatedOn);
            tvNote = itemView.findViewById(R.id.tvNotesNote);
            tvNoteId = itemView.findViewById(R.id.tvNotesId);
            cvNote = itemView.findViewById(R.id.cvNote);
        }
    }
}
