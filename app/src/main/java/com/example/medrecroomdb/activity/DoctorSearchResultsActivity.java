package com.example.medrecroomdb.activity;
import android.Manifest;
import android.app.Activity;
import android.content.Intent;
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
import com.example.medrecroomdb.R;
import com.example.medrecroomdb.model.MedicalRecord;
import com.example.medrecroomdb.model.Patient;
import com.example.medrecroomdb.viewmodel.PatientViewModel;

import java.io.File;
import java.util.ArrayList;

public class DoctorSearchResultsActivity extends AppCompatActivity {

    private PatientViewModel patientViewModel;
    private TextView fNameTxtView, lNameTxtView, addrTxtView, healthcardTxtView, phoneTxtView, emailTxtView, medicalTxtView;
    Patient patient;
    String healthcard;
    private ArrayList<MedicalRecord> medicalRecordsList;
    private RecyclerView rcv;
    private Button noteBtn;
    Button uploadToS3;
    private static Uri mSelectedFileUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_searchresults_contraint_layout);
        rcv = findViewById(R.id.rcvPatient);
        medicalRecordsList = new ArrayList<>();
        setUserInfo();
        setAdapter();



        try{
            Intent intent=getIntent();
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
        }
        catch(Exception e)
        {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }

        uploadToS3 = (Button) findViewById(R.id.addMedHis);
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
                /*Requests permissions to be granted to this application. These permissions
                 must be requested in your manifest, they should not be granted to your app,
                 and they should have protection level*/
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
        medicalRecordsList.add(new MedicalRecord("Record 1 - Date: 08/21"));
        medicalRecordsList.add(new MedicalRecord("Record 2 - Date: 09/21" ));
        medicalRecordsList.add(new MedicalRecord("Record 3 - Date: 10/21"));
    }



    private void uploadFile(String Uri){
        System.out.println(Uri);
        String File = Uri;

        Amplify.Storage.uploadFile(
                "Med History From Doctor",
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
    }
}