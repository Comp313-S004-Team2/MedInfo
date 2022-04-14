package com.example.medrecroomdb.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.medrecroomdb.R;

import java.lang.reflect.Array;

public class Appointment extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        // Day schedule dropdown
        final Spinner spinnerWeekDay = findViewById(R.id.spinner_weekDay);
        Button confirmBtn = (Button)findViewById((R.id.confirmBtnSchedule));
        spinnerWeekDay.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapterDay = ArrayAdapter.createFromResource(this,R.array.week_date, android.R.layout.simple_spinner_item);
        adapterDay.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWeekDay.setAdapter(adapterDay);

        // Time schedule Dropdown
        final Spinner spinnerTime = findViewById(R.id.spinner_timeDay);
        spinnerTime.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapterTime = ArrayAdapter.createFromResource(this,R.array.day_time, android.R.layout.simple_spinner_item);
        adapterTime.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTime.setAdapter(adapterTime);

        // can delete this too
        SharedPreferences sp = getSharedPreferences("myprefs", Context.MODE_PRIVATE);
        // delete until here.

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Appointment.this,PatientNavActivity.class);
                intent.putExtra("data",String.valueOf(spinnerWeekDay.getSelectedItem()));
                intent.putExtra("time",String.valueOf(spinnerTime.getSelectedItem()));

                // When adding to database, you can delete this part:
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("day",String.valueOf(spinnerWeekDay.getSelectedItem()));
                editor.putString("hour",String.valueOf(spinnerTime.getSelectedItem()));
                editor.commit();
                // delete until this line.

                startActivity(intent);
            }
        });
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}