package com.example.medrecroomdb.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Note;
import com.example.medrecroomdb.R;

import java.time.LocalDateTime;

public class AddNote extends AppCompatActivity {

    EditText etNote;
    CheckBox cbIsDoctorOnly;
    SharedPreferences viewMedicalRecordPreferences;
    SharedPreferences loginInfoPreferences;
    String recordId, userRole, userName, userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        etNote = findViewById(R.id.etAddNote);
        cbIsDoctorOnly = findViewById(R.id.cbAddNoteIsDoctorOnly);

        loginInfoPreferences = getSharedPreferences("loginInfo", MODE_PRIVATE);
        viewMedicalRecordPreferences = getSharedPreferences("viewMedicalRecord", MODE_PRIVATE);
        recordId = viewMedicalRecordPreferences.getString("recordToView", null);
        userId = loginInfoPreferences.getString("idNumber", null);
        userRole = loginInfoPreferences.getString("role", null);
        userName = loginInfoPreferences.getString("userName", null);

        Log.i("User ID", userId);
        Log.i("Record ID", recordId);
        Log.i("User Role", userRole);
        Log.i("User Name", userName);

    }
    public void onSaveNote(View view){
        boolean isError = false;
        if(etNote.getText().toString().isEmpty()){
            etNote.setError("Note is required");
        }
        if(!isError){
            Note item = Note.builder()
                    .recordId(recordId)
                    .note(etNote.getText().toString())
                    .isDoctorOnly(cbIsDoctorOnly.isChecked())
                    .writerRole(userRole)
                    .writerName(userName)
                    .writerId(userId)
                    .createdOn(LocalDateTime.now().toString())
                    .build();
            Amplify.DataStore.save(
                    item,
                    success -> Log.i("Amplify", "Saved item: " + success.item().getId()),
                    error -> Log.e("Amplify", "Could not save item to DataStore", error)
            );
            finish();
        }

    }
}