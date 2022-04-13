package com.example.medrecroomdb.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.query.Where;
import com.amplifyframework.datastore.generated.model.Note;
import com.example.medrecroomdb.R;
import com.example.medrecroomdb.RVNotesListAdapter;

import java.util.ArrayList;

public class ViewNotes extends AppCompatActivity {

    RecyclerView rvNotes;
    RVNotesListAdapter rvNotesListAdapter;
    ArrayList<Note> notes;
    SharedPreferences viewMedicalRecordPreferences;
    String recordId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_notes);

        viewMedicalRecordPreferences = getSharedPreferences("viewMedicalRecord", MODE_PRIVATE);
        recordId = viewMedicalRecordPreferences.getString("recordToView", null);

        Log.i("Record ID", recordId);
        notes = new ArrayList<Note>();

        rvNotes = findViewById(R.id.rvNotes);
        rvNotes.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onResume() {
        super.onResume();
        notes = new ArrayList<Note>();
        Amplify.DataStore.query(
                Note.class, Where.matches(Note.RECORD_ID.eq(recordId)),
                items -> {
                    while (items.hasNext()) {
                        Note item = items.next();
                        notes.add(item);
                        Log.i("Query Success", "Id " + item.getId());
                    }
                    runOnUiThread(() -> {
                        rvNotesListAdapter = new RVNotesListAdapter(notes, this);
                        rvNotes.setAdapter(rvNotesListAdapter);
                    });
                },
                failure -> Log.e("Amplify", "Could not query DataStore", failure)
        );
    }

    public void onAddNote(View view){
        Intent addNoteIntent = new Intent(this, AddNote.class);
        startActivity(addNoteIntent);
    }
}