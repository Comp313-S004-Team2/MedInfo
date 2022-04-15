package com.example.medrecroomdb.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.query.Where;
import com.amplifyframework.datastore.generated.model.Note;
import com.example.medrecroomdb.R;

public class EditNote extends AppCompatActivity {

    EditText etNote;
    CheckBox cbIsDoctorOnly;
    Button btnUpdate;
    SharedPreferences detailedNotePreference;
    String noteId;
    Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        etNote = findViewById(R.id.etAddNote);
        cbIsDoctorOnly = findViewById(R.id.cbAddNoteIsDoctorOnly);
        btnUpdate = findViewById(R.id.btnAddNoteSave);

        btnUpdate.setText("Update");

        detailedNotePreference = getSharedPreferences("detailedNote", MODE_PRIVATE);
        noteId = detailedNotePreference.getString("noteToEdit", null);

        Amplify.DataStore.query(
                Note.class, Where.id(noteId),
                items -> {
                    while (items.hasNext()) {
                        Note item = items.next();
                        note = item;
                        Log.i("Amplify", "Id " + item.getId());
                        runOnUiThread(() -> {
                            etNote.setText(note.getNote());
                            cbIsDoctorOnly.setChecked(note.getIsDoctorOnly());
                        });
                    }
                },
                failure -> Log.e("Amplify", "Could not query DataStore", failure)
        );

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isError = false;
                if(etNote.getText().toString().isEmpty()){
                    etNote.setError("Note is required");
                    isError = true;
                }
                if(!isError){
                    Amplify.DataStore.query(
                            Note.class, Where.id(noteId),
                            items -> {
                                if (items.hasNext()) {
                                    Note item = items.next();
                                    Note edited = item.copyOfBuilder()
                                            .note(etNote.getText().toString())
                                            .isDoctorOnly(cbIsDoctorOnly.isChecked())
                                            .build();
                                    Amplify.DataStore.save(
                                            edited,
                                            success -> {
                                                Log.i("Amplify", "Item updated: " + success.item().getId());
                                                runOnUiThread(() -> {
                                                    Toast.makeText(EditNote.this, "Note Successfully Edited", Toast.LENGTH_SHORT).show();
                                                });
                                            },
                                            error -> Log.e("Amplify", "Could not save item to DataStore", error)
                                    );
                                    Log.i("Amplify", "Id " + item.getId());
                                }
                            },
                            failure -> Log.e("Amplify", "Could not query DataStore", failure)
                    );
                }
                finish();
            }
        });

    }
}