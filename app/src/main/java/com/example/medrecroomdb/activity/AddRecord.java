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
import com.amplifyframework.datastore.generated.model.RecordMetadata;
import com.example.medrecroomdb.R;

import java.io.File;
import java.time.LocalDateTime;

public class AddRecord extends AppCompatActivity {

    TextView tvUploader, tvRole;
    EditText etTitle, etDescription, etRecordPath;
    Intent chooseRecordIntent;
    String pathOfFile, extension, uploaderRole, uploaderName, uploaderId, patientId;
    SharedPreferences loginSharedPreferences;
    SharedPreferences searchPatientSharedPreference;
    Uri uri;
    File recordFile;
    Button btnUploadRecord, btnOpenRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);

        tvUploader = findViewById(R.id.tvEditRecUploader);
        tvRole = findViewById(R.id.tvEditRecUploaderRole);
        etTitle = findViewById(R.id.etEditRecTitle);
        etDescription = findViewById(R.id.etEditRecDescription);
        etRecordPath = findViewById(R.id.etEditRecRecordPath);
        loginSharedPreferences = getSharedPreferences("loginInfo", MODE_PRIVATE);
        uploaderName = loginSharedPreferences.getString("userName", null);
        uploaderRole = loginSharedPreferences.getString("role", null);
        uploaderId = loginSharedPreferences.getString("idNumber", null);
        searchPatientSharedPreference = getSharedPreferences("doctorSearchPatient", MODE_PRIVATE);
        patientId = searchPatientSharedPreference.getString("healthCardToSearch", null);
        btnUploadRecord = findViewById(R.id.btnUploadRecord);
        btnOpenRecord = findViewById(R.id.btnOpenRecord);

        tvUploader.setText(uploaderName);
        tvRole.setText(uploaderRole);

        btnOpenRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFile();
            }
        });

        btnUploadRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean uploadRecordIsError = false;
                recordFile = new File(pathOfFile);

                if(etTitle.getText().toString().isEmpty()){
                    etTitle.setError("Title is Required");
                    uploadRecordIsError = true;
                }
                if(etDescription.getText().toString().isEmpty()){
                    etDescription.setError("Description is Required");
                    uploadRecordIsError = true;
                }
                if(etRecordPath.getText().toString().isEmpty()){
                    etRecordPath.setError("No Record Selected");
                    uploadRecordIsError = true;
                }
                if(!uploadRecordIsError){
                    RecordMetadata item = RecordMetadata.builder()
                            .title(etTitle.getText().toString())
                            .description(etDescription.getText().toString())
                            .uploader(uploaderName)
                            .uploaderRole(uploaderRole)
                            .uploaderId(uploaderId)
                            .fileExtension(extension)
                            .patientId(patientId)
                            .createdOn(LocalDateTime.now().toString())
                            .build();
                    Amplify.DataStore.save(
                            item,
                            success -> {
                                Amplify.Storage.uploadFile(item.getId() + extension, recordFile,
                                        result -> Log.i("MyAmplifyApp", "Successfully uploaded: " + item.getId() + extension),
                                        error -> Log.e("MyAmplifyApp", "Upload failed", error)
                                );
                                Log.i("Amplify", "Saved item: " + success.item().getId());
                                runOnUiThread(() -> {
                                    Toast.makeText(AddRecord.this, "Record Successfully Created", Toast.LENGTH_SHORT).show();
                                });
                            },
                            error -> Log.e("Amplify", "Could not save item to DataStore", error)
                    );
                }
                finish();
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

    /*ActivityResultLauncher<Intent> openFIleResultActivityLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode() == 2){
                Intent data = result.getData();
                Log.d("Data", data.toString());
            }
        }
    });*/

}