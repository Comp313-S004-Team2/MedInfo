package com.example.medrecroomdb.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.query.Where;
import com.amplifyframework.datastore.generated.model.RecordMetadata;
import com.amplifyframework.datastore.generated.model.User;
import com.example.medrecroomdb.R;

import java.io.File;

public class EditRecord extends AppCompatActivity {

    TextView tvUploader, tvRole;
    EditText etTitle, etDescription, etRecordPath;
    Intent chooseRecordIntent;
    String pathOfFile, extension, uploaderRole, uploaderName, uploaderId, patientId, recordId;
    SharedPreferences detailedRecordPreference;
    Uri uri;
    File recordFile;
    Button btnUploadRecord, btnOpenRecord;
    RecordMetadata record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);

        tvUploader = findViewById(R.id.tvEditRecUploader);
        tvRole = findViewById(R.id.tvEditRecUploaderRole);
        etTitle = findViewById(R.id.etEditRecTitle);
        etDescription = findViewById(R.id.etEditRecDescription);
        etRecordPath = findViewById(R.id.etEditRecRecordPath);
        btnUploadRecord = findViewById(R.id.btnUploadRecord);
        btnOpenRecord = findViewById(R.id.btnOpenRecord);

        btnUploadRecord.setText("Save Record");

        detailedRecordPreference = getSharedPreferences("detailedRecord", MODE_PRIVATE);
        recordId = detailedRecordPreference.getString("recordToEdit", null);

        Amplify.DataStore.query(
                RecordMetadata.class, Where.id(recordId),
                items -> {
                    if (items.hasNext()) {
                        RecordMetadata item = items.next();
                        record = item;
                        Log.i("Amplify", "Id " + item.getId());
                        runOnUiThread(() -> {
                            tvUploader.setText(record.getUploader());
                            tvRole.setText(record.getUploaderRole());
                            etTitle.setText(record.getTitle());
                            etDescription.setText(record.getDescription());
                            extension = record.getFileExtension();
                        });
                    }
                },
                failure -> Log.e("Amplify", "Could not query DataStore", failure)
        );

        btnUploadRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isError = false;
                if(etTitle.getText().toString().isEmpty()){
                    etTitle.setError("Title is Required");
                    isError = true;
                }
                if(etDescription.getText().toString().isEmpty()){
                    etDescription.setError("Description is Required");
                    isError = true;
                }
                if(!isError){
                    if(!etRecordPath.getText().toString().isEmpty()){
                        Amplify.Storage.remove(
                                record.getId() + record.getFileExtension(),
                                result -> {
                                    Amplify.DataStore.query(
                                            RecordMetadata.class, Where.id(recordId),
                                            items -> {
                                                if (items.hasNext()) {
                                                    RecordMetadata original = items.next();
                                                    RecordMetadata edited = original.copyOfBuilder()
                                                            .title(etTitle.getText().toString())
                                                            .description(etTitle.getText().toString())
                                                            .fileExtension(extension)
                                                            .build();
                                                    Amplify.DataStore.save(edited,
                                                            updated -> {
                                                                    Amplify.Storage.uploadFile(record.getId() + record.getFileExtension(), new File(pathOfFile),
                                                                            success -> Log.i("MyAmplifyApp", "Successfully uploaded: " + record.getId() + record.getFileExtension()),
                                                                            error -> Log.e("MyAmplifyApp", "Upload failed", error)
                                                                    );
                                                                Log.i("MyAmplifyApp", "Updated a post.");
                                                            },
                                                            failure -> Log.e("MyAmplifyApp", "Update failed.", failure)
                                                    );
                                                }
                                            },
                                            failure -> Log.e("Amplify", "Could not query DataStore", failure)
                                    );
                                    Log.i("MyAmplifyApp", "Successfully removed: " + result.getKey());
                                },
                                error -> Log.e("MyAmplifyApp", "Remove failure", error)
                        );
                    }else{
                        Amplify.DataStore.query(
                                RecordMetadata.class, Where.id(recordId),
                                items -> {
                                    if (items.hasNext()) {
                                        RecordMetadata original = items.next();
                                        RecordMetadata edited = original.copyOfBuilder()
                                                .title(etTitle.getText().toString())
                                                .description(etDescription.getText().toString())
                                                .fileExtension(extension)
                                                .build();
                                        Amplify.DataStore.save(edited,
                                                updated -> Log.i("MyAmplifyApp", "Updated a post."),
                                                failure -> Log.e("MyAmplifyApp", "Update failed.", failure)
                                        );
                                    }
                                },
                                failure -> Log.e("Amplify", "Could not query DataStore", failure)
                        );
                    }
                }
                finish();
            }
        });

        btnOpenRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFile();
            }
        });
    }

    void openFile(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            chooseRecordIntent = new Intent(Intent.ACTION_PICK);
            chooseRecordIntent.setType("*/*");
            startActivityForResult(chooseRecordIntent, 2);
        }else{
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 2) {
                if (data != null) {
                    try {
                        uri = data.getData();
                        //Cursor cursor = getContentResolver().query(mSelectedFileUri, DocumentsContract.Document.)
                        Log.d("MainActivity", uri.getPath());
                        String[] filePathColumn = new String[]{MediaStore.MediaColumns.DATA};
                        // Convert Uri to File Path
                        //String[] filePathColumn = new String[]{MediaStore.Files.FileColumns._ID};
                        Cursor cursor = getContentResolver().query(
                                uri,
                                filePathColumn, null, null, null
                        );
                        cursor.moveToFirst();
                        Integer columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String filepath = cursor.getString(columnIndex);
                        cursor.close();
                        String filePath = filepath;
                        pathOfFile = filePath;
                        etRecordPath.setText(filePath);
                        //Log.i("filepath", filepath);
                        String[] splitForExtension = filepath.split("\\.");
                        extension = "." + splitForExtension[splitForExtension.length - 1];
                        Log.i("Extension", extension);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }
}