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

import java.util.List;

public class SetAppointment extends AppCompatActivity {

    CalendarView calendarAppointment;
    Calendar maxDate, minDate;
    SimpleDateFormat getDate;
    int year, month, day;
    ArrayList<String> timeSchedule;
    Spinner timeSlot;
    String datePattern, doctorForAppointment, patientId;
    SharedPreferences doctorListPreference, loginInfoPrefence;
    TextView tvName;
    User doctor;
    Button btnSetAppointment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        calendarAppointment = findViewById(R.id.calendarViewAppointment);
        tvName = findViewById(R.id.tvSetAppDoctorName);
        timeSlot = findViewById(R.id.spinAppTimeSlot);
        btnSetAppointment = findViewById(R.id.btnAppSetAppointment);

        doctorListPreference = getSharedPreferences("doctorList", MODE_PRIVATE);
        loginInfoPrefence = getSharedPreferences("loginInfo", MODE_PRIVATE);
        patientId = loginInfoPrefence.getString("idNumber", null);
        doctorForAppointment = doctorListPreference.getString("doctorForAppointment", null);

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
                    }
                },
                failure -> Log.e("Amplify", "Could not query DataStore", failure)
        );

        timeSchedule = new ArrayList<String>();

        datePattern = "dd-MM-yyyy";

        List<String> fromResource = Arrays.asList(getResources().getStringArray(R.array.timeSelection));
        for (String string: fromResource) {
            timeSchedule.add(string);
        }


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

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, timeSchedule);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        timeSlot.setAdapter(adapter);

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
                }
            }
        });
    }

    public void onSetAppointment(View view) {
        Appointment item = Appointment.builder()
                .patientId(patientId)
                .doctorId(doctor.getIdNumber())
                .year(year)
                .month(month)
                .day(day)
                .timeSlot(timeSlot.getSelectedItemPosition())
                .build();
        Amplify.DataStore.save(
                item,
                success -> Log.i("Appointment", "Saved item: " + success.item().getId()),
                error -> Log.e("Appointment", "Could not save item to DataStore", error)
        );
        finish();
    }
}