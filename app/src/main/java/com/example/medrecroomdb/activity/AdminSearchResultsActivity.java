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
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import com.amplifyframework.core.Amplify;
import com.example.medrecroomdb.R;
import com.example.medrecroomdb.model.Doctor;
import com.example.medrecroomdb.model.Patient;
import com.example.medrecroomdb.viewmodel.DoctorViewModel;
import com.example.medrecroomdb.viewmodel.PatientViewModel;

import java.io.File;

public class AdminSearchResultsActivity extends AppCompatActivity {

    PatientViewModel patientViewModel;
    DoctorViewModel doctorViewModel;
    Patient patient;
    Doctor doctor;
    String strId;
    Integer id;
    private EditText editText_UserId, editText_firstName, editText_lastName, editText_address, editText_email, editText_phoneNumber;
    private Button btnUpdateUser;
    private String userType;

    Button uploadBtnPatient;
    private static Uri mSelectedFileUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_searchresults);

        populateFields();

        btnUpdateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentSearchResult = new Intent(view.getContext(), UpdateUserActivity.class);
                intentSearchResult.putExtra("userId", id.toString());
                intentSearchResult.putExtra("userType", userType);
                startActivity(intentSearchResult);
            }
        });

        uploadBtnPatient = (Button) findViewById(R.id.addMedHistory);
        uploadBtnPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(
                        AdminSearchResultsActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                )
                        == PackageManager.PERMISSION_GRANTED
                ) {
                    showFileChooser(AdminSearchResultsActivity.this);
                } else {
                /*Requests permissions to be granted to this application. These permissions
                 must be requested in your manifest, they should not be granted to your app,
                 and they should have protection level*/
                    ActivityCompat.requestPermissions(
                            AdminSearchResultsActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            3

                    );
                }}
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        populateFields();

    }

    public void populateFields(){
        btnUpdateUser = findViewById(R.id.btnAdminSearchResultsUpdateUser);


        try{
            Intent intent=getIntent();
            strId = intent.getStringExtra("userId");
            id = Integer.parseInt(strId);
            patientViewModel = ViewModelProviders.of(this).get(PatientViewModel.class);
            patient = patientViewModel.findByPatientId(id);
            doctorViewModel= ViewModelProviders.of(this).get(DoctorViewModel.class);
            doctor = doctorViewModel.findByDoctorId(id);
            if (patient != null && patient.getPatientId() == id)
            {
                userType = "patient";
                editText_UserId = findViewById(R.id.editText_UserId);
                editText_UserId.setText(String.valueOf(patient.getPatientId()));
                editText_firstName = findViewById(R.id.editText_firstName);
                editText_firstName.setText(patient.getFirstName());
                editText_lastName = findViewById(R.id.editText_lastName);
                editText_lastName.setText(patient.getLastName());
                editText_address = findViewById(R.id.editText_address);
                editText_address.setText(patient.getAddress());
                editText_email = findViewById(R.id.editText_email);
                editText_email.setText(patient.getEmail());
                editText_phoneNumber = findViewById(R.id.editText_phoneNumber);
                editText_phoneNumber.setText(String.valueOf(patient.getPhoneNumber()));

                editText_UserId.setEnabled(false);
                editText_firstName.setEnabled(false);
                editText_lastName.setEnabled(false);
                editText_address.setEnabled(false);
                editText_email.setEnabled(false);
                editText_phoneNumber.setEnabled(false);

            }
            else{
                userType = "doctor";
                editText_UserId = findViewById(R.id.editText_UserId);
                editText_UserId.setText(String.valueOf(doctor.getDoctorId()));
                editText_firstName = findViewById(R.id.editText_firstName);
                editText_firstName.setText(doctor.getFirstName());
                editText_lastName = findViewById(R.id.editText_lastName);
                editText_lastName.setText(doctor.getLastName());
                editText_address = findViewById(R.id.editText_address);
                editText_address.setText(doctor.getAddress());
                editText_email = findViewById(R.id.editText_email);
                editText_email.setText(doctor.getEmail());
                editText_phoneNumber = findViewById(R.id.editText_phoneNumber);
                editText_phoneNumber.setText(String.valueOf(doctor.getPhoneNumber()));

                editText_UserId.setEnabled(false);
                editText_firstName.setEnabled(false);
                editText_lastName.setEnabled(false);
                editText_address.setEnabled(false);
                editText_email.setEnabled(false);
                editText_phoneNumber.setEnabled(false);
            }
        }
        catch(Exception e)
        {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }


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
                                AdminSearchResultsActivity.this,
                                "File Selection Failed",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                }
            }
        }
    }

    private void uploadFile(String Uri){
        System.out.println(Uri);
        String File = Uri;

        Amplify.Storage.uploadFile(
                "Upload File From Admin",
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

