package com.example.medrecroomdb.activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medrecroomdb.R;
import com.example.medrecroomdb.model.MedicalRecord;
import com.example.medrecroomdb.model.Patient;
import com.example.medrecroomdb.viewmodel.PatientViewModel;
import java.util.ArrayList;

public class DoctorSearchResultsActivity extends AppCompatActivity {

    private PatientViewModel patientViewModel;
    private TextView fNameTxtView, lNameTxtView, addrTxtView, healthcardTxtView, phoneTxtView, emailTxtView, medicalTxtView;
    Patient patient;
    String healthcard;
    private ArrayList<MedicalRecord> medicalRecordsList;
    private RecyclerView rcv;

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

    }

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
}