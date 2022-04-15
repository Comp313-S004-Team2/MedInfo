package com.example.medrecroomdb.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.medrecroomdb.R;

public class DoctorNav extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_nav);
    }

    public void onSearchPatient(View view){
        Intent intent = new Intent(this, DoctorSearchPatientActivity.class);
        startActivity(intent);
    }

    public void onAppointments(View view){
        Intent intent = new Intent(this, AppointmentList.class);
        startActivity(intent);
    }
}