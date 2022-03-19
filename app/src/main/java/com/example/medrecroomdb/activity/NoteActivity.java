package com.example.medrecroomdb.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.medrecroomdb.R;

public class NoteActivity extends AppCompatActivity {

    private Button confirmNoteBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);


        confirmNoteBtn = (Button) findViewById(R.id.confirmBtn);
        confirmNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(getApplicationContext(), DoctorSearchResultsActivity.class);
                startActivity(intent2);
            }
        });



    }
}
