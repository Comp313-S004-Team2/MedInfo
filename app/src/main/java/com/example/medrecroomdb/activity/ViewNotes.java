package com.example.medrecroomdb.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

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
    SharedPreferences viewMedicalRecordPreferences, loginInfoPreference;
    String recordId, userRole, userId;
    Button btnAddNote;
    Switch swtNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_notes);

        swtNotes = findViewById(R.id.switch1);

        viewMedicalRecordPreferences = getSharedPreferences("viewMedicalRecord", MODE_PRIVATE);
        loginInfoPreference = getSharedPreferences("loginInfo", MODE_PRIVATE);

        recordId = viewMedicalRecordPreferences.getString("recordToView", null);
        userRole = loginInfoPreference.getString("role",null);
        userId = loginInfoPreference.getString("idNumber", null);

        Log.i("Record ID", recordId);
        notes = new ArrayList<Note>();

        rvNotes = findViewById(R.id.rvNotes);
        rvNotes.setLayoutManager(new LinearLayoutManager(this));
        btnAddNote = findViewById(R.id.button5);

        if(userRole.matches("Patient")){
            btnAddNote.setVisibility(View.GONE);
            swtNotes.setVisibility(View.GONE);
        }

        swtNotes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                notes = new ArrayList<Note>();
                if(b){
                    Amplify.DataStore.query(
                            Note.class, Where.matches(Note.RECORD_ID.eq(recordId).and(Note.WRITER_ID.eq(userId))),
                            items -> {
                                while (items.hasNext()) {
                                    Note item = items.next();
                                    notes.add(item);
                                    Log.i("Query Success", "Id " + item.getId());
                                }
                                runOnUiThread(() -> {
                                    rvNotesListAdapter = new RVNotesListAdapter(notes, ViewNotes.this);
                                    rvNotes.setAdapter(rvNotesListAdapter);
                                });
                            },
                            failure -> Log.e("Amplify", "Could not query DataStore", failure)
                    );
                } else {
                    Amplify.DataStore.query(
                            Note.class, Where.matches(Note.RECORD_ID.eq(recordId)),
                            items -> {
                                while (items.hasNext()) {
                                    Note item = items.next();
                                    notes.add(item);
                                    Log.i("Query Success", "Id " + item.getId());
                                }
                                runOnUiThread(() -> {
                                    rvNotesListAdapter = new RVNotesListAdapter(notes, ViewNotes.this);
                                    rvNotes.setAdapter(rvNotesListAdapter);
                                });
                            },
                            failure -> Log.e("Amplify", "Could not query DataStore", failure)
                    );
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        swtNotes.setChecked(false);
        notes = new ArrayList<Note>();
        if(userRole.matches("Patient")){
            Amplify.DataStore.query(
                    Note.class, Where.matches(Note.RECORD_ID.eq(recordId).and(Note.IS_DOCTOR_ONLY.eq(false))),
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
        }else{
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

    }

    public void onAddNote(View view){
        Intent addNoteIntent = new Intent(this, AddNote.class);
        startActivity(addNoteIntent);
    }
}