package com.example.medrecroomdb.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.medrecroomdb.R;

import java.lang.reflect.Array;

public class Appointment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        // Week Day Dropdown
        Spinner spinnerWeekDay = findViewById(R.id.spinner_weekDay);
        ArrayAdapter<CharSequence> adapterDay = ArrayAdapter.createFromResource(this,R.array.week_date, android.R.layout.simple_spinner_item);
        adapterDay.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWeekDay.setAdapter(adapterDay);

        // Time schedule Dropdown
        Spinner spinnerTime = findViewById(R.id.spinner_timeDay);
        ArrayAdapter<CharSequence> adapterTime = ArrayAdapter.createFromResource(this,R.array.day_time, android.R.layout.simple_spinner_item);
        adapterTime.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTime.setAdapter(adapterTime);
    }

    public void confirmAppointment(View view){
        Intent confirmAppointmentIntent = new Intent(this, PatientNavActivity.class);
        startActivity(confirmAppointmentIntent);
    }
}