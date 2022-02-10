package com.example.medrecroomdb.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.medrecroomdb.R;
import com.example.medrecroomdb.model.Patient;
import com.example.medrecroomdb.viewmodel.AdminViewModel;
import com.example.medrecroomdb.viewmodel.PatientViewModel;

public class PatientViewMedRecActivity extends AppCompatActivity {
    // Declare variables
    private PatientViewModel patientViewModel;
    private TextView healthcardTxtView, appHistoryTxtView, medsTxtView, doctorTxtView, allergyTxtView, medRecTxtView, docnotesTxtView, recoTxtView;
    Patient patient;
    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_viewmedrec);
        try {
            Intent intent=getIntent();
            userName = intent.getStringExtra("userName");
            patientViewModel = ViewModelProviders.of(this).get(PatientViewModel.class);
            patient = patientViewModel.findByPatientEmail(userName);

            if (patient != null)
            {
                healthcardTxtView = findViewById(R.id.hcardTextView);
                healthcardTxtView.setText(patient.getHealthcardNumber());
                appHistoryTxtView = findViewById(R.id.apphistoryTextView);
                appHistoryTxtView.setText(patient.getEmail());
                medsTxtView = findViewById(R.id.medicationsTextView);
                medsTxtView.setText(patient.getEmail());
                doctorTxtView = findViewById(R.id.doctorTextView);
                doctorTxtView.setText(patient.getEmail());
                allergyTxtView = findViewById(R.id.allergyTextView);
                allergyTxtView.setText(patient.getEmail());
                medRecTxtView = findViewById(R.id.medrecTextView);
                medRecTxtView.setText(patient.getEmail());
//                docnotesTxtView = findViewById(R.id.medicalTextView);
//                docnotesTxtView.setText(patient.getEmail());
//                recoTxtView = findViewById(R.id.medicalTextView);
//                recoTxtView.setText(patient.getEmail());
            }
        }
        catch(Exception e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

}
