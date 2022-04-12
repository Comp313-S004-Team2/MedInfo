package com.example.medrecroomdb.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.medrecroomdb.R;

public class ViewMedicalRecords extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_medical_records);
    }

    public void onAddRecord(View view){
        Intent addRecordIntent = new Intent(this, AddRecord.class);
        startActivity(addRecordIntent);
    }
}