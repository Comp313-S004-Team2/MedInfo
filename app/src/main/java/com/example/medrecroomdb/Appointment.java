package com.example.medrecroomdb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.query.Where;
import com.amplifyframework.datastore.generated.model.Doctor;
import com.amplifyframework.datastore.generated.model.User;
import com.example.medrecroomdb.activity.DoctorSearchPatientActivity;
import com.example.medrecroomdb.activity.PatientNavActivity;

public class Appointment extends AppCompatActivity {

    Doctor doctorName;
    TextView txtDoctorName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_appointment );

        txtDoctorName = findViewById(R.id.txtDoctorTest);
        Amplifyy.initializeAmplify(Appointment.this);

        Amplify.DataStore.query(
                Doctor.class,

                items -> {
                    while (items.hasNext()) {
                        Doctor item = items.next();
                        doctorName = item;
                        Log.i("Amplify", "Id " + item.getId());
                    }
                    //txtDoctorName.setText( doctorName.getFirstName().toString());
                    runOnUiThread(() -> {txtDoctorName.setText(doctorName.getFirstName().toString());});
                    //doctorName.getFirstName().toString()
                },
                failure -> Log.e("Amplify", "Could not query DataStore", failure)
        );



        /*Amplify.DataStore.query( User.class,
                Where.matches(User.EMAIL.eq(userName).and(User.PASSWORD.eq(password))),
                goodPosts -> {
                    if (goodPosts.hasNext()) {
                        User user = goodPosts.next();
                        if(user.getRole() == "Doctor") {
                            Intent intentDoctor = new Intent(v.getContext(), DoctorSearchPatientActivity.class);
                            startActivity(intentDoctor);
                        }
                        else if(user.getRole() == "Patient"){
                            Intent intentPatient = new Intent(v.getContext(), PatientNavActivity.class);
                            startActivity(intentPatient);
                        }
                    }
                },
                failure -> Log.e("MyAmplifyApp", "Query failed.", failure)
        );*/


    }
}