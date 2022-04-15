package com.example.medrecroomdb.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.query.Where;
import com.amplifyframework.datastore.generated.model.Appointment;
import com.amplifyframework.datastore.generated.model.User;
import com.example.medrecroomdb.R;

import java.util.Date;
import java.util.List;

public class SetAppointment extends AppCompatActivity {

    CalendarView calendarAppointment;
    Calendar maxDate, minDate;
    int year, month, day;
    ArrayList<String> timeSchedule, availableTimeSlots;
    Spinner spnrtimeSlots;
    String datePattern, doctorForAppointment, patientId, patientName;
    SharedPreferences doctorListPreference, loginInfoPrefence;
    TextView tvName;
    User doctor;
    Button btnSetAppointment;
    ArrayList<Appointment> appointments;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        /*Declare at Class scope
        Calendar getDate;
        Date convertedToDate;
        Then at onCreate or any other lifecycle
        getDate = Calendar.getInstance();
        getDate.set(year, month, day);
        convertedToDate = new Date(getDate.getTimeInMillis());*/

        /*Declare at Class scope
        ArrayList<String> timeSchedule;
        Then at onCreate or any other lifecycle
        timeSchedule = new ArrayList<String>();
        List<String> fromResource = Arrays.asList(getResources().getStringArray(R.array.timeSelection));
        for (String string: fromResource) {
            timeSchedule.add(string);
        }
        String theTimeSlot = timeSchedule.get(timeSlot);*/

        appointments = new ArrayList<Appointment>();

        calendarAppointment = findViewById(R.id.calendarViewAppointment);
        tvName = findViewById(R.id.tvSetAppDoctorName);
        spnrtimeSlots = findViewById(R.id.spinAppTimeSlot);
        btnSetAppointment = findViewById(R.id.btnAppSetAppointment);

        doctorListPreference = getSharedPreferences("doctorList", MODE_PRIVATE);
        loginInfoPrefence = getSharedPreferences("loginInfo", MODE_PRIVATE);
        patientId = loginInfoPrefence.getString("idNumber", null);
        patientName = loginInfoPrefence.getString("userName", null);
        doctorForAppointment = doctorListPreference.getString("doctorForAppointment", null);
        Log.i("Doctors Id", doctorForAppointment);

        timeSchedule = new ArrayList<String>();
        availableTimeSlots = new ArrayList<String>();

        datePattern = "dd-MM-yyyy";

        List<String> fromResource = Arrays.asList(getResources().getStringArray(R.array.timeSelection));
        for (String string: fromResource) {
            timeSchedule.add(string);
            availableTimeSlots.add(string);
        }


        Amplify.DataStore.query(
                User.class, Where.matches(User.ID_NUMBER.eq(doctorForAppointment)),
                items -> {
                    if (items.hasNext()) {
                        User item = items.next();
                        doctor = item;
                        Log.i("User", "Id " + item.getId());
                        runOnUiThread(() -> {
                            tvName.setText("Dr." + item.getFirstName() + " " + item.getLastName());
                        });
                        Amplify.DataStore.query(
                                Appointment.class, Where.matches(Appointment.DOCTOR_ID.eq(doctor.getIdNumber())
                                        .and(Appointment.YEAR.eq(year))
                                        .and(Appointment.MONTH.eq(month))
                                        .and(Appointment.DAY.eq(day))),
                                apps -> {
                                    while (apps.hasNext()) {
                                        Appointment app = apps.next();
                                        appointments.add(app);
                                        availableTimeSlots.remove(timeSchedule.get(app.getTimeSlot()));
                                        Log.i("Amplify", "Id " + app.getId());
                                    }
                                    runOnUiThread(() -> {
                                        adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, availableTimeSlots);
                                        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                                        spnrtimeSlots.setAdapter(adapter);
                                    });

                                },
                                failure -> Log.e("Amplify", "Could not query DataStore", failure)
                        );
                    }
                },
                failure -> Log.e("Amplify", "Could not query DataStore", failure)
        );

        minDate = Calendar.getInstance();
        maxDate = Calendar.getInstance();
        minDate.add(Calendar.DATE, 2);
        maxDate.add(Calendar.DATE, 23);
        if(minDate.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY){
            minDate.add(Calendar.DATE, 2);
        }else if(minDate.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
            minDate.add(Calendar.DATE, 1);
        }
        Log.i("Day", "" + minDate.get(Calendar.DATE));
        Log.i("Month", "" + minDate.get(Calendar.MONTH));
        Log.i("Year", "" + minDate.get(Calendar.YEAR));
        day = minDate.get(Calendar.DATE);
        month = minDate.get(Calendar.MONTH);
        year = minDate.get(Calendar.YEAR);

        calendarAppointment.setMinDate(minDate.getTimeInMillis());
        calendarAppointment.setMaxDate(maxDate.getTimeInMillis());
        calendarAppointment.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {

                btnSetAppointment.setClickable(true);
                year = i;
                month = i1;
                day = i2;
                Calendar checkDate = Calendar.getInstance();
                checkDate.set(i, i1, i2);
                if(checkDate.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || checkDate.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY){
                    btnSetAppointment.setClickable(false);
                    calendarAppointment.setDate(minDate.getTimeInMillis());
                    Toast.makeText(SetAppointment.this, "Weekends are unavailable", Toast.LENGTH_SHORT).show();
                }else{
                    Amplify.DataStore.query(
                            Appointment.class, Where.matches(Appointment.DOCTOR_ID.eq(doctor.getIdNumber())
                                    .and(Appointment.YEAR.eq(year))
                                    .and(Appointment.MONTH.eq(month))
                                    .and(Appointment.DAY.eq(day))),
                            items -> {
                                availableTimeSlots = new ArrayList<>(timeSchedule);
                                adapter = new ArrayAdapter<String>(SetAppointment.this, R.layout.support_simple_spinner_dropdown_item, availableTimeSlots);
                                adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                                while (items.hasNext()) {
                                    Appointment item = items.next();
                                    appointments.add(item);
                                    availableTimeSlots.remove(timeSchedule.get(item.getTimeSlot()));
                                    Log.i("Amplify", "Id " + item.getId());
                                }
                                runOnUiThread(() -> {
                                    spnrtimeSlots.setAdapter(adapter);
                                });

                            },
                            failure -> Log.e("Amplify", "Could not query DataStore", failure)
                    );
                }
            }
        });
        btnSetAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Appointment item = Appointment.builder()
                        .patientId(patientId)
                        .doctorId(doctor.getIdNumber())
                        .year(year)
                        .month(month)
                        .day(day)
                        .timeSlot(timeSchedule.indexOf(spnrtimeSlots.getSelectedItem().toString()))
                        .patientName(patientName)
                        .doctorName(doctor.getFirstName() + " " + doctor.getLastName())
                        .build();
                Amplify.DataStore.save(
                        item,
                        success -> Log.i("Appointment", "Saved item: " + success.item().getId()),
                        error -> Log.e("Appointment", "Could not save item to DataStore", error)
                );
                finish();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}