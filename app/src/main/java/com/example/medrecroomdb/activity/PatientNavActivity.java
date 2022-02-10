package com.example.medrecroomdb.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.medrecroomdb.R;
import com.example.medrecroomdb.model.Patient;
import com.example.medrecroomdb.viewmodel.AdminViewModel;
import com.example.medrecroomdb.viewmodel.PatientViewModel;

public class PatientNavActivity extends AppCompatActivity {
    // Declare variables
    private PatientViewModel patientViewModel;
    private Button button_viewMedRec, button_updateInfo, button_bookAppt;
    String userName;
    Integer id;
    String healthcardNumber;
    Patient patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_nav);

        Intent intent=getIntent();
        userName = intent.getStringExtra("userName");



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
                Intent intentViewMedRec = new Intent(v.getContext(), PatientViewMedRecActivity.class);
                startActivity(intentViewMedRec);
            }
        });

    }

}
