package com.example.medrecroomdb.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.medrecroomdb.R;

public class StaffNav extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_nav);
    }

    public void onSearchPatient(View view){
        Intent intent = new Intent(this, DoctorSearchPatientActivity.class);
        sharedPreferences = getSharedPreferences("staffNav", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isFromUploadedRecords", false);
        editor.commit();
        startActivity(intent);
    }

    public void onMedicalRecords(View view){
        Intent intent = new Intent(this, ViewMedicalRecords.class);
        sharedPreferences = getSharedPreferences("staffNav", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isFromUploadedRecords", true);
        editor.commit();
        startActivity(intent);
    }
}