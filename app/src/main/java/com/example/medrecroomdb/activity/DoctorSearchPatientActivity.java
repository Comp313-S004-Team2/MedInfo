package com.example.medrecroomdb.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.medrecroomdb.R;
import com.example.medrecroomdb.model.Admin;
import com.example.medrecroomdb.model.Doctor;
import com.example.medrecroomdb.model.Patient;
import com.example.medrecroomdb.viewmodel.AdminViewModel;
import com.example.medrecroomdb.viewmodel.DoctorViewModel;
import com.example.medrecroomdb.viewmodel.PatientViewModel;

public class DoctorSearchPatientActivity extends AppCompatActivity {
    // Declare variables
    private PatientViewModel patientViewModel;
    private Button btnSearchPatient;
    private EditText editText_pHealthcardNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_searchpatient);

        // Set up references
        patientViewModel = ViewModelProviders.of(this).get(PatientViewModel.class);
        btnSearchPatient = findViewById(R.id.btn_searchPatient);
        editText_pHealthcardNumber = findViewById(R.id.editText_patientHealthcardNumber);

        // Set up click listener for Search Button
        btnSearchPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //
                    String healthcardNumber = editText_pHealthcardNumber.getText().toString();

                    // Set up variable doctor references of Type Doctor to find doctor by doctorId by findByHealthcardNumber()
                    Patient patient = patientViewModel.findByHealthcardNumber(healthcardNumber);

                    // Validate if nurseId and Password match the info in AppDatabase and if both are filled, return successful result
                    if (patient != null && patient.getHealthcardNumber().equals(healthcardNumber)){
                        Intent intentSearchResult = new Intent(v.getContext(), DoctorSearchResultsActivity.class);
                        intentSearchResult.putExtra("healthcardNumber", healthcardNumber);
                        Toast.makeText(getApplicationContext(), healthcardNumber, Toast.LENGTH_SHORT).show();
                        startActivity(intentSearchResult);
                    } else {
                        // Otherwise, show error message
                        Toast.makeText(getApplicationContext(), "Invalid healthcard number.", Toast.LENGTH_SHORT).show();
                    }
                } catch(Exception e) {
                    Toast.makeText(getApplicationContext(), "Please enter healthcard number.", Toast.LENGTH_SHORT).show();
                    System.out.print(e.getMessage());
                }
            }
        });
    }
}
