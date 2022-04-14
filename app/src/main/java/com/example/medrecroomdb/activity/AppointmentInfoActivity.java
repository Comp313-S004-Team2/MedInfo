package com.example.medrecroomdb.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.medrecroomdb.R;

public class AppointmentInfoActivity extends AppCompatActivity {

    private Button cancelBtn, rescheduleBtn;
    private TextView timeTv, dateTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_info);

        // This is how we will see the data on the appointment info
        TextView  textView=(TextView) findViewById(R.id.dateTextView);
        Bundle bundle=getIntent().getExtras();
        String data = bundle.get("data").toString();
        textView.setText(data);

        TextView  textView2=(TextView) findViewById(R.id.timeTextView);
        Bundle bundle2=getIntent().getExtras();
        String data2 = bundle2.get("time").toString();
        textView2.setText(data2);

        rescheduleBtn = findViewById(R.id.rescheduleApptButton);
        cancelBtn = findViewById(R.id.cancelApptButton);

        rescheduleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentViewMedRec = new Intent(v.getContext(), Appointment.class);
                //intentViewMedRec.putExtra("healthcardNumber", healthcardNumber);
                startActivity(intentViewMedRec);
            }
        });


        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentViewMedRec = new Intent(v.getContext(), PatientNavActivity.class);
                //intentViewMedRec.putExtra("healthcardNumber", healthcardNumber);
                startActivity(intentViewMedRec);
            }
        });
    }
}