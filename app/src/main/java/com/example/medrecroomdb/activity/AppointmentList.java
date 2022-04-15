package com.example.medrecroomdb.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.query.Where;
import com.amplifyframework.datastore.generated.model.Appointment;
import com.example.medrecroomdb.R;
import com.example.medrecroomdb.RVAppointmentListAdapter;

import java.util.ArrayList;
import java.util.Calendar;

public class AppointmentList extends AppCompatActivity {

    RecyclerView rvAppointmentList;
    RVAppointmentListAdapter rvAppointmentListAdapter;
    SharedPreferences loginInfoPreference;
    String idNumber, userRole;
    ArrayList<Appointment> appointments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_list);


        rvAppointmentList = findViewById(R.id.rvAppointmentList);
        rvAppointmentList.setLayoutManager(new LinearLayoutManager(this));

        loginInfoPreference = getSharedPreferences("loginInfo", MODE_PRIVATE);
        idNumber = loginInfoPreference.getString("idNumber", null);
        userRole = loginInfoPreference.getString("role", null);

    }

    @Override
    protected void onResume() {
        super.onResume();
        appointments = new ArrayList<Appointment>();
        Calendar dateToday = Calendar.getInstance();
        if(userRole.matches("Patient")){
            Amplify.DataStore.query(
                    Appointment.class, Where.matches(Appointment.PATIENT_ID.eq(idNumber)),
                    items -> {
                        while (items.hasNext()) {

                            Appointment item = items.next();
                            Calendar appointmentDate = Calendar.getInstance();
                            appointmentDate.set(item.getYear(), item.getMonth(), item.getDay());
                            if(dateToday.before(appointmentDate)){
                                appointments.add(item);
                            }
                            Log.i("Appointment", "Id " + item.getId());
                        }
                        runOnUiThread(() -> {
                            rvAppointmentListAdapter = new RVAppointmentListAdapter(this, appointments, userRole);
                            rvAppointmentList.setAdapter(rvAppointmentListAdapter);
                        });


                    },
                    failure -> Log.e("Appointment", "Could not query DataStore", failure)
            );
        }else if(userRole.matches("Doctor")){
            Amplify.DataStore.query(
                    Appointment.class, Where.matches(Appointment.DOCTOR_ID.eq(idNumber)),
                    items -> {
                        while (items.hasNext()) {

                            Appointment item = items.next();
                            Calendar appointmentDate = Calendar.getInstance();
                            appointmentDate.set(item.getYear(), item.getMonth(), item.getDay());
                            if(dateToday.before(appointmentDate)){
                                appointments.add(item);
                            }
                            Log.i("Appointment", "Id " + item.getId());
                        }
                        runOnUiThread(() -> {
                            rvAppointmentListAdapter = new RVAppointmentListAdapter(this, appointments, userRole);
                            rvAppointmentList.setAdapter(rvAppointmentListAdapter);
                        });


                    },
                    failure -> Log.e("Appointment", "Could not query DataStore", failure)
            );
        }

    }

    public void onScheduleAppointment(View view){
        Intent scheduleAppointmentIntent = new Intent(this, DoctorList.class);
        startActivity(scheduleAppointmentIntent);
    }
}