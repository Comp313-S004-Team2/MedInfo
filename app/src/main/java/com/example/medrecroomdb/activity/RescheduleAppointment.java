package com.example.medrecroomdb.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.TextView;

import com.amazonaws.services.kms.model.TagException;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.query.Where;
import com.amplifyframework.datastore.generated.model.Appointment;
import com.amplifyframework.datastore.generated.model.User;
import com.example.medrecroomdb.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class RescheduleAppointment extends AppCompatActivity {

    SharedPreferences appointmentListPreference;
    String appointmentToReschedule;
    Appointment appointment;
    TextView tvDoctorName;
    CalendarView cvAppointment;
    Calendar appointmentDate, minDate, maxDate;
    Button btnSetAppointment;
    int year, month, day;
    Spinner spnrTimeSlot;
    ArrayList<String> timeSchedule, availableTimeSlots;
    ArrayAdapter<String> adapter;
    ArrayList<Appointment> appointments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        tvDoctorName = findViewById(R.id.tvSetAppDoctorName);
        cvAppointment = findViewById(R.id.calendarViewAppointment);
        btnSetAppointment = findViewById(R.id.btnAppSetAppointment);
        spnrTimeSlot = findViewById(R.id.spinAppTimeSlot);

        timeSchedule = new ArrayList<String>();
        availableTimeSlots = new ArrayList<String>();
        appointments = new ArrayList<Appointment>();

        List<String> fromResource = Arrays.asList(getResources().getStringArray(R.array.timeSelection));
        for (String string: fromResource) {
            timeSchedule.add(string);
            availableTimeSlots.add(string);
        }
        Log.i("TimeSlots", availableTimeSlots.toString());

        btnSetAppointment.setText("Reschedule");
        appointmentDate = Calendar.getInstance();
        minDate = Calendar.getInstance();
        maxDate = Calendar.getInstance();
        minDate.add(Calendar.DATE, 1);
        maxDate.add(Calendar.DATE, 22);
        if(minDate.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
            minDate.add(Calendar.DATE, 1);
        }else if(minDate.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY){
            minDate.add(Calendar.DATE, 2);
        }
        year = minDate.get(Calendar.YEAR);
        month = minDate.get(Calendar.MONTH);
        day = minDate.get(Calendar.DATE);
        cvAppointment.setMinDate(minDate.getTimeInMillis());
        cvAppointment.setMaxDate(maxDate.getTimeInMillis());

        cvAppointment.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                year = i;
                month = i1;
                day = i2;
                availableTimeSlots = new ArrayList<String>(timeSchedule);
                Amplify.DataStore.query(
                        Appointment.class, Where.matches(Appointment.DOCTOR_ID.eq(appointment.getDoctorId())
                        .and(Appointment.DAY.eq(day))),
                        items -> {
                            while (items.hasNext()) {
                                Appointment item = items.next();
                                appointments.add(item);
                                if(appointment.getTimeSlot() != item.getTimeSlot()){
                                    availableTimeSlots.remove(timeSchedule.get(item.getTimeSlot()));
                                }
                                Log.i("Amplify", "Id " + item.getId());
                                runOnUiThread(() -> {
                                    adapter = new ArrayAdapter<String>(RescheduleAppointment.this, R.layout.support_simple_spinner_dropdown_item, availableTimeSlots);
                                    adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                                    spnrTimeSlot.setAdapter(adapter);
                                });
                            }
                        },
                        failure -> Log.e("Amplify", "Could not query DataStore", failure)
                );

                Log.i("Year", year + "");
                Log.i("Month", month + "");
                Log.i("Day", day + "");

            }
        });

        appointmentListPreference = getSharedPreferences("appointmentList", MODE_PRIVATE);
        appointmentToReschedule = appointmentListPreference.getString("appointmentToReschedule", null);

        Amplify.DataStore.query(
                Appointment.class, Where.id(appointmentToReschedule),
                items -> {
                    if (items.hasNext()) {
                        appointment = items.next();
                        appointmentDate.set(appointment.getYear(), appointment.getMonth(), appointment.getDay());
                        Log.i("Appointment", "Id " + appointment.getId());
                        runOnUiThread(() -> {
                            tvDoctorName.setText("Dr. " + appointment.getDoctorName());
                            cvAppointment.setDate(appointmentDate.getTimeInMillis());
                        });
                        Log.i("After runOnUi", "Here");
                        Amplify.DataStore.query(
                                Appointment.class, Where.matches(Appointment.DOCTOR_ID.eq(appointment.getDoctorId())
                                .and(Appointment.YEAR.eq(appointment.getYear()))
                                .and(Appointment.MONTH.eq(appointment.getMonth()))
                                .and(Appointment.DAY.eq(appointment.getDay()))),
                                apps -> {
                                    while (apps.hasNext()) {
                                        Appointment item = apps.next();
                                        appointments.add(item);
                                        if(appointment.getTimeSlot() != item.getTimeSlot()){
                                            availableTimeSlots.remove(timeSchedule.get(item.getTimeSlot()));
                                        }
                                        Log.i("Amplify", "Id " + item.getId());
                                        runOnUiThread(() -> {
                                            adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, availableTimeSlots);
                                            adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                                            spnrTimeSlot.setAdapter(adapter);
                                            spnrTimeSlot.setSelection(availableTimeSlots.indexOf(timeSchedule.get(appointment.getTimeSlot())));
                                        });
                                    }
                                },
                                failure -> Log.e("Amplify", "Could not query DataStore", failure)
                        );
                    }
                },
                failure -> Log.e("Appointment", "Could not query DataStore", failure)
        );

        btnSetAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Amplify.DataStore.query(Appointment.class, Where.id(appointmentToReschedule),
                        matches -> {
                            if (matches.hasNext()) {
                                Appointment original = matches.next();
                                Appointment edited = original.copyOfBuilder()
                                        .year(year)
                                        .month(month)
                                        .day(day)
                                        .timeSlot(spnrTimeSlot.getSelectedItemPosition())
                                        .build();
                                Amplify.DataStore.save(edited,
                                        updated -> Log.i("MyAmplifyApp", "Updated a post."),
                                        failure -> Log.e("MyAmplifyApp", "Update failed.", failure)
                                );
                            }
                        },
                        failure -> Log.e("MyAmplifyApp", "Query failed.", failure)
                );
                finish();
            }
        });

    }
}