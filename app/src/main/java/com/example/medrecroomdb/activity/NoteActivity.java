package com.example.medrecroomdb.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.medrecroomdb.R;
import com.example.medrecroomdb.model.Patient;

public class NoteActivity extends AppCompatActivity {

    private TextView fNameTxtView, lNameTxtView, addrTxtView, healthcardTxtView, phoneTxtView, emailTxtView, medicalTxtView,notesTxtView;
    Patient patient;
    private Button confirmNoteBtn;
    private EditText patientNotes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        //notes = findViewById(R.id.boxEditNotes);// change 1
        patientNotes = (EditText) findViewById(R.id.boxEditNotes); // change 2
        confirmNoteBtn = (Button) findViewById(R.id.confirmBtn);
        confirmNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* change here */

                Intent addNotesIntent = new Intent(view.getContext(), DoctorSearchResultsActivity.class);
                addNotesIntent.putExtra("AddNotes",patientNotes.getText().toString());
                startActivity(addNotesIntent);
                /* Original code is here
                //String notesAdd = notes.getText().toString(); // change 2
                //Intent intent2 = new Intent(getApplicationContext(), DoctorSearchResultsActivity.class); // original code
                //intent2.putExtra("notes", notesAdd);//change here
                // startActivity(intent2); // original code
                */
                //
            }
        });

    }
}
