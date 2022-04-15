package com.example.medrecroomdb.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.core.Amplify;
import com.example.medrecroomdb.R;
import com.example.medrecroomdb.model.Patient;
import com.example.medrecroomdb.viewmodel.PatientViewModel;

import java.io.File;

public class PatientNavActivity extends AppCompatActivity {
    // Declare variables
    private PatientViewModel patientViewModel;
    private Button button_viewMedRec, button_updateInfo, button_bookAppt;
    String userName;
    Integer id;
    String healthcardNumber, userId;
    Patient patient;
    Button uploadBtnPatient;
    private static Uri mSelectedFileUri = null;
    SharedPreferences loginInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_nav);

        loginInfo = getSharedPreferences("loginInfo", MODE_PRIVATE);
        userId = loginInfo.getString("idNumber", null);

        /*uploadBtnPatient = (Button) findViewById(R.id.uploadBtnPatient);
        uploadBtnPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(
                        PatientNavActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                )
                        == PackageManager.PERMISSION_GRANTED
                ) {
                    showFileChooser(PatientNavActivity.this);
                } else {
                 //Requests permissions to be granted to this application. These permissions
                 //must be requested in your manifest, they should not be granted to your app,
                 //and they should have protection level
                    ActivityCompat.requestPermissions(
                            PatientNavActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            3

                    );
                }}
        });

        patientViewModel = ViewModelProviders.of(this).get(PatientViewModel.class);
        patient = patientViewModel.findByPatientEmail(userName);
        id = patient.getPatientId();
        healthcardNumber = patient.getHealthcardNumber();

        button_viewMedRec = findViewById(R.id.btn_viewMedicalRecord);
        button_updateInfo = findViewById(R.id.btn_updateInfo);
        button_bookAppt = findViewById(R.id.btn_schedule);

        button_viewMedRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentViewMedRec = new Intent(v.getContext(), DoctorSearchResultsActivity.class);
                intentViewMedRec.putExtra("healthcardNumber", healthcardNumber);
                startActivity(intentViewMedRec);
            }
        });

        button_updateInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentViewMedRec = new Intent(v.getContext(), AdminSearchResultsActivity.class);
                intentViewMedRec.putExtra("userType", "patient");
                intentViewMedRec.putExtra("userId", id.toString());
//                Toast.makeText(getApplicationContext(), id.toString(), Toast.LENGTH_SHORT).show();
                startActivity(intentViewMedRec);
            }
        });

        button_bookAppt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intentViewMedRec = new Intent(v.getContext(), PatientViewMedRecActivity.class);
                Intent intentViewMedRec = new Intent(v.getContext(), Appointment.class);
                startActivity(intentViewMedRec);
            }
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
                                PatientNavActivity.this,
                                "File Selection Failed",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                }
            }
        }*/
    }

    public void onAppointments(View view){
        Intent scheduleAppointmentIntent = new Intent(this, AppointmentList.class);
        startActivity(scheduleAppointmentIntent);
    }

    public void onViewMedicalRecords(View view){
        Intent intent = new Intent(this, ViewMedicalRecords.class);
        SharedPreferences sharedPreferences = getSharedPreferences("doctorSearchPatient", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("healthCardToSearch", userId);
        editor.commit();
        startActivity(intent);
    }

    private void uploadFile(String Uri){
        System.out.println(Uri);
        String File = Uri;

        Amplify.Storage.uploadFile(
                "Upload File from Patient",
                new File(File),
                result -> Toast.makeText(this, "File has Successfully Uploaded: " + result.getKey(), Toast.LENGTH_SHORT).show(),
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
