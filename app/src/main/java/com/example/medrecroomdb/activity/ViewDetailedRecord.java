package com.example.medrecroomdb.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.query.Where;
import com.amplifyframework.datastore.generated.model.Note;
import com.amplifyframework.datastore.generated.model.RecordMetadata;
import com.amplifyframework.storage.options.StorageDownloadFileOptions;
import com.example.medrecroomdb.R;
import com.example.medrecroomdb.RVNotesListAdapter;
import com.example.medrecroomdb.model.MedicalRecord;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ViewDetailedRecord extends AppCompatActivity {

    String medRecId, userId;
    RecordMetadata recordMetadata;
    TextView tvTitle, tvDescription, tvUploader, tvUploadDate, tvNumberOfNotes, tvRecordId;
    SharedPreferences viewMedRecPreference, loginInfoPreference;
    ArrayList<Note> notes;
    Button btnEdit, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_detailed_record);

        
        viewMedRecPreference = getSharedPreferences("viewMedicalRecord", MODE_PRIVATE);
        loginInfoPreference = getSharedPreferences("loginInfo", MODE_PRIVATE);
        medRecId = viewMedRecPreference.getString("recordToView", null);
        userId = loginInfoPreference.getString("idNumber", null);

        notes = new ArrayList<Note>();

        tvTitle = findViewById(R.id.tvDetRecTitle);
        tvDescription = findViewById(R.id.tvDetRecDescription);
        tvUploader = findViewById(R.id.tvDetRecUploader);
        tvUploadDate = findViewById(R.id.tvDetRecUploadDate);
        tvNumberOfNotes = findViewById(R.id.tvDetRecNotesNumber);
        tvRecordId = findViewById(R.id.tvDetRecRcordId);
        tvDescription.setMovementMethod(new ScrollingMovementMethod());
        btnDelete = findViewById(R.id.btnMedRecDelete);
        btnEdit = findViewById(R.id.btnMedRecEdit);



    }

    @Override
    protected void onResume() {
        super.onResume();
        notes = new ArrayList<Note>();
        Amplify.DataStore.query(
                Note.class, Where.matches(Note.RECORD_ID.eq(medRecId)),
                items -> {
                    while (items.hasNext()) {
                        Note item = items.next();
                        notes.add(item);
                        Log.i("Query Success", "Id " + item.getId());
                    }
                    runOnUiThread(() -> {
                        tvNumberOfNotes.setText("" + notes.size());
                    });
                },
                failure -> Log.e("Amplify", "Could not query DataStore", failure)
        );

        Amplify.DataStore.query(
                RecordMetadata.class, Where.matches(RecordMetadata.ID.eq(medRecId)),
                items -> {
                    if (items.hasNext()) {
                        recordMetadata = items.next();
                        Log.i("Amplify", "Id " + recordMetadata.getId());
                        runOnUiThread(() -> {
                            tvRecordId.setText(recordMetadata.getId());
                            tvTitle.setText(recordMetadata.getTitle());
                            tvDescription.setText(recordMetadata.getDescription());
                            tvUploader.setText(recordMetadata.getUploader());
                            tvUploadDate.setText(LocalDateTime.now().toString());
                            if(!userId.matches(recordMetadata.getUploaderId())){
                                btnEdit.setVisibility(View.INVISIBLE);
                                btnDelete.setVisibility(View.INVISIBLE);
                            }
                        });
                    }
                },
                failure -> Log.e("Amplify", "Could not query DataStore", failure)
        );
    }

    public void onViewNotes(View view){
        Intent viewNotesIntent = new Intent(this, ViewNotes.class);
        startActivity(viewNotesIntent);
    }

    public void onEdit(View view){

    }
    public void onDelete(View view){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Delete").setMessage("Are you sure you want to delete this record? This will also Delete all its related notes.")
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Amplify.DataStore.query(
                        RecordMetadata.class, Where.matches(RecordMetadata.ID.eq(medRecId)),
                        items -> {
                            if (items.hasNext()) {
                                RecordMetadata item = items.next();
                                Amplify.Storage.remove(
                                        item.getId() + item.getFileExtension(),
                                        result -> Log.i("MyAmplifyApp", "Successfully removed: " + result.getKey()),
                                        error -> Log.e("MyAmplifyApp", "Remove failure", error)
                                );
                                Amplify.DataStore.query(
                                        Note.class, Where.matches(Note.ID.eq(item.getId())),
                                        notes -> {
                                            while (notes.hasNext()) {
                                                Note note = notes.next();
                                                Amplify.DataStore.delete(note,
                                                        deleted -> Log.i("Note", "Deleted item."),
                                                        failure -> Log.e("Note", "Delete failed.", failure)
                                                );
                                                Log.i("Amplify", "Id " + note.getId());
                                            }
                                        },
                                        failure -> Log.e("Amplify", "Could not query DataStore", failure)
                                );
                                Amplify.DataStore.delete(item,
                                        deleted -> Log.i("Record", "Deleted item."),
                                        failure -> Log.e("Record", "Delete failed.", failure)
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
    }
    public void onDownload(View view){
        Amplify.Storage.downloadFile(
                recordMetadata.getId() + recordMetadata.getFileExtension(),
                new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/" + recordMetadata.getId() + recordMetadata.getFileExtension()),
                StorageDownloadFileOptions.defaultInstance(),
                progress -> Log.i("MyAmplifyApp", "Fraction completed: " + progress.getFractionCompleted()),
                result -> {
                    runOnUiThread(() -> {
                        Toast.makeText(this, "File Downloaded Successfully", Toast.LENGTH_SHORT).show();
                    });
                    Log.i("MyAmplifyApp", "Successfully downloaded: " + result.getFile().getName());},
                error -> Log.e("MyAmplifyApp",  "Download Failure", error)
        );
    }
}