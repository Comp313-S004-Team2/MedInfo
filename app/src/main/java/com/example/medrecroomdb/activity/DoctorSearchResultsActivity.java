package com.example.medrecroomdb.activity;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.query.Where;
import com.amplifyframework.datastore.generated.model.RecordMetadata;
import com.amplifyframework.datastore.generated.model.User;
import com.example.medrecroomdb.R;
import com.example.medrecroomdb.model.MedicalRecord;
import com.example.medrecroomdb.model.Patient;
import com.example.medrecroomdb.viewmodel.PatientViewModel;

import java.io.File;
import java.util.ArrayList;

public class DoctorSearchResultsActivity extends AppCompatActivity {

    //private PatientViewModel patientViewModel;
    private TextView tvFname, tvLName, tvAddress, tvHealthCard, tvPhone, tvEmail, tvMedRecCount;
    private User patient;
    private String idNumber;
    SharedPreferences sharedPreferences;
    ArrayList<RecordMetadata> recordMetadatas;
    //Patient patient;
    //String healthcard;
    //private ArrayList<MedicalRecord> medicalRecordsList;
    //private RecyclerView rcv;
    //private Button noteBtn;
    //Button uploadToS3;
    //private static Uri mSelectedFileUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_searchresults_contraint_layout);
        tvFname = findViewById(R.id.tvFNamePatInfo);
        tvLName = findViewById(R.id.tvLNamePatInfo);
        tvAddress = findViewById(R.id.tvAddressPatInfo);
        tvHealthCard = findViewById(R.id.tvHealthCardPatInfo);
        tvPhone = findViewById(R.id.tvPhonePatInfo);
        tvEmail = findViewById(R.id.tvEmailPatInfo);
        tvMedRecCount = findViewById(R.id.tvMedicalHistoryCount);

        sharedPreferences = getSharedPreferences("doctorSearchPatient", MODE_PRIVATE);
        idNumber = sharedPreferences.getString("healthCardToSearch", null);

        Amplify.DataStore.query(User.class, Where.matches(User.ID_NUMBER.eq(idNumber)),
                goodPosts -> {
                    if (goodPosts.hasNext()) {
                        patient = goodPosts.next();
                        Log.i("MyAmplifyApp", "Post: " +  patient);
                        runOnUiThread(() -> {
                            tvFname.setText(patient.getFirstName());
                            tvLName.setText(patient.getLastName());
                            tvAddress.setText(patient.getAddress());
                            tvHealthCard.setText(patient.getIdNumber());
                            tvPhone.setText(patient.getPhoneNumber());
                            tvEmail.setText(patient.getEmail());
                        });
                    }
                },
                failure -> Log.e("MyAmplifyApp", "Query failed.", failure)
        );

    }

    @Override
    protected void onResume() {
        super.onResume();
        recordMetadatas = new ArrayList<RecordMetadata>();
        Amplify.DataStore.query(
                RecordMetadata.class, Where.matches(RecordMetadata.PATIENT_ID.eq(idNumber)),
                items -> {
                    while (items.hasNext()) {
                        RecordMetadata item = items.next();
                        recordMetadatas.add(item);
                        Log.i("Amplify", "Id " + item.getId());
                    }
                    runOnUiThread(() -> {
                        tvMedRecCount.setText("" + recordMetadatas.size());
                    });
                },
                failure -> Log.e("Amplify", "Could not query DataStore", failure)
        );
    }

    public void onViewMedicalRecords(View view){
        Intent medicalRecordsIntent = new Intent(this, ViewMedicalRecords.class);
        startActivity(medicalRecordsIntent);
    }
}



// OLD ------------------------------------------------------------------------------------------------------------------------------------------------------//
        /*rcv = findViewById(R.id.rcvPatient);
        medicalRecordsList = new ArrayList<>();
        setUserInfo();
        setAdapter();

        noteBtn = (Button) findViewById(R.id.btnViewNotes);
        noteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(getApplicationContext(), NoteActivity.class);
                startActivity(intent2);
            }
        });*/

        /*try{
            Intent intent=getIntent();
            Intent receiverNotes = getIntent();
            String receivedNotesValue = receiverNotes.getStringExtra("AddNotes");
            healthcard = intent.getStringExtra("healthcardNumber");
            patientViewModel = ViewModelProviders.of(this).get(PatientViewModel.class);
            patient = patientViewModel.findByHealthcardNumber(healthcard);
            if (patient != null)
            {
                fNameTxtView = findViewById(R.id.patientResultsCFirstName);
                fNameTxtView.setText(patient.getFirstName());
                lNameTxtView = findViewById(R.id.patientResultsCLastName);
                lNameTxtView.setText(patient.getLastName());
                addrTxtView = findViewById(R.id.patientResultsCFAddressName);
                addrTxtView.setText(patient.getAddress());
                healthcardTxtView = findViewById(R.id.patientResultsCFHCard);
                healthcardTxtView.setText(patient.getHealthcardNumber());
                phoneTxtView = findViewById(R.id.pResultsPhoneNumber);
                phoneTxtView.setText(String.valueOf(patient.getPhoneNumber()));
                emailTxtView = findViewById(R.id.pResultsCEmail);
                emailTxtView.setText(patient.getEmail());
                //medicalTxtView = findViewById(R.id.medicalTextView);
                //medicalTxtView.setText(patient.);
            }
            notesTxtView = (TextView) findViewById(R.id.tvAddressPatInfo);
            notesTxtView.setText(receivedNotesValue);

        }
        catch(Exception e)
        {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }*/
        ///    private void setUserNotes(){
        ////
        ////notesTxtView = findViewById(R.id.boxEditNotes);
        ////notesTxtView.setText(boxEditNotes);
        //String notesAdd = getIntent().getStringExtra("notes"); //change here
        //    notesTxtView = findViewById(R.id.boxEditNotes);
        //                notesTxtView.setText(notesAdd); //change here
        ////    }


        /*uploadToS3 = (Button) findViewById(R.id.addMedHis);
        uploadToS3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(
                        DoctorSearchResultsActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                )
                        == PackageManager.PERMISSION_GRANTED
                ) {
                    showFileChooser(DoctorSearchResultsActivity.this);
                } else {
                //Requests permissions to be granted to this application. These permissions
                //must be requested in your manifest, they should not be granted to your app,
                //and they should have protection level
                    ActivityCompat.requestPermissions(
                            DoctorSearchResultsActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            3

                    );
                }}
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == 2){
                if(data != null){
                    try{
                        mSelectedFileUri = data.getData();
                        Log.d("MainActivity", mSelectedFileUri.toString());

                        String[] filePathColumn = new String[]{MediaStore.Images.Media.DATA};
                        // Convert Uri to File Path
                        //String[] filePathColumn = new String[]{MediaStore.Files.FileColumns._ID};
                        Cursor cursor = getContentResolver().query(
                                mSelectedFileUri,
                                filePathColumn, null, null, null
                        );
                        cursor.moveToFirst();
                        Integer columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String filepath = cursor.getString(columnIndex);
                        cursor.close();
                        String filePath = filepath;

                        uploadFile(filePath);

                    }
                    catch (Exception e){
                        e.printStackTrace();
                        Toast.makeText(
                                DoctorSearchResultsActivity.this,
                                "File Selection Failed",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                }
            }}}
    private void setAdapter() {
        recyclerAdapterPatient adapter = new recyclerAdapterPatient(medicalRecordsList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rcv.setLayoutManager(layoutManager);
        rcv.setItemAnimator(new DefaultItemAnimator());
        rcv.setAdapter(adapter);
    }

    private void setUserInfo() {
        medicalRecordsList.add(new MedicalRecord("Record 1 - Date: October 10, 2021"));
        medicalRecordsList.add(new MedicalRecord("Record 2 - Date: February 2, 2022" ));
        medicalRecordsList.add(new MedicalRecord("Record 3 - Date: March 15, 2022"));
    }



    private void uploadFile(String Uri){
        System.out.println(Uri);
        String File = Uri;

        Amplify.Storage.uploadFile(
                "Med History From Doctor Dirk Brandon",
                new File(File),
                result -> Toast.makeText(this, "File has Successfully Uploaded:" + result.getKey(), Toast.LENGTH_SHORT).show(),
                error -> Log.e("MyAmplifyApp", "Upload failed", error)

        );

    }
    public void showFileChooser(Activity activity){
        // An intent for launching the image selection of phone storage.
        Intent intent  = new Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Launches the image selection of phone storage using the constant code.
        activity.startActivityForResult(intent, 2);
    }*/
