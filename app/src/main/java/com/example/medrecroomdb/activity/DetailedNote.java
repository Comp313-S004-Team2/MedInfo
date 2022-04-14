package com.example.medrecroomdb.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.query.Where;
import com.amplifyframework.datastore.generated.model.Note;
import com.example.medrecroomdb.R;

public class DetailedNote extends AppCompatActivity {

    TextView tvCreatedBy, tvCreatedOn, tvRecordId, tvIsDoctorOnly, tvNote;
    Button btnEdit, btnDelete;
    SharedPreferences notesListPreference, loginPreference;
    String noteToView, userName, userRole, userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_note);


        tvCreatedBy = findViewById(R.id.tvDetNotCreatedBy);
        tvCreatedOn = findViewById(R.id.tvDetNotCreatedOn);
        tvRecordId = findViewById(R.id.tvDetNotRecordId);
        tvIsDoctorOnly = findViewById(R.id.tvDetNotIsDoctorOnly);
        tvNote = findViewById(R.id.tvDetNotNote);
        btnEdit = findViewById(R.id.btnDetNotEdit);
        btnDelete = findViewById(R.id.btnDetNotDelete);

        notesListPreference = getSharedPreferences("notesList", MODE_PRIVATE);
        loginPreference = getSharedPreferences("loginInfo", MODE_PRIVATE);
        noteToView = notesListPreference.getString("noteToView", null);
        userName = loginPreference.getString("userName", null);
        userRole = loginPreference.getString("role", null);
        userId = loginPreference.getString("idNumber", null);

        Amplify.DataStore.query(
                Note.class, Where.matches(Note.ID.eq(noteToView)),
                items -> {
                    if (items.hasNext()) {
                        Note item = items.next();
                        Log.i("Amplify", "Id " + item.getId());
                        runOnUiThread(() -> {
                            if(item.getWriterRole().matches("Doctor")){
                                tvCreatedBy.setText( "Dr. " + item.getWriterName());
                            } else {
                                tvCreatedBy.setText(item.getWriterName());
                            }
                            tvCreatedOn.setText(item.getCreatedOn());
                            tvRecordId.setText(item.getRecordId());
                            if(item.getIsDoctorOnly()){
                                tvIsDoctorOnly.setText("Yes");
                            } else {
                                tvIsDoctorOnly.setText("No");
                            }
                            tvNote.setText(item.getNote());

                            if(!userId.matches(item.getWriterId())){
                                btnEdit.setVisibility(View.INVISIBLE);
                                btnDelete.setVisibility(View.INVISIBLE);
                            }

                        });
                    }
                },
                failure -> Log.e("Amplify", "Could not query DataStore", failure)
        );

    }

    public void onOk(View view){
        finish();
    }
    public void onEdit(View view){

    }
    public void onDelete(View view){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Delete").setMessage("Are you sure you want to delete this note?")
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Amplify.DataStore.query(
                        Note.class, Where.matches(Note.ID.eq(noteToView)),
                        items -> {
                            if (items.hasNext()) {
                                Note item = items.next();
                                Amplify.DataStore.delete(item,
                                        deleted -> Log.i("Amplify", "Deleted item."),
                                        failure -> Log.e("Amplify", "Delete failed.", failure)
                                );
                                Log.i("Amplify", "Id " + item.getId());
                            }
                            runOnUiThread(() -> {
                                finish();
                            });
                        },
                        failure -> Log.e("Amplify", "Could not query DataStore", failure)
                );
            }
        }).show();
        alertDialog.create();

        Amplify.DataStore.query(
                Note.class, Where.matches(Note.ID.eq(noteToView)),
                items -> {
                    if (items.hasNext()) {
                        Note item = items.next();
                        Amplify.DataStore.delete(item,
                                deleted -> Log.i("Amplify", "Deleted item."),
                                failure -> Log.e("Amplify", "Delete failed.", failure)
                        );
                        Log.i("Amplify", "Id " + item.getId());
                    }
                },
                failure -> Log.e("Amplify", "Could not query DataStore", failure)
        );
    }
}