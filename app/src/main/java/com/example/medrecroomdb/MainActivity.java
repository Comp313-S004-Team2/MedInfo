package com.example.medrecroomdb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.medrecroomdb.activity.AdminActivity;
import com.example.medrecroomdb.activity.DoctorActivity;
import com.example.medrecroomdb.activity.PatientActivity;
import com.example.medrecroomdb.activity.DoctorSearchPatientActivity;
import com.example.medrecroomdb.activity.AdminSearchUserActivity;
import com.example.medrecroomdb.activity.PatientNavActivity;
import com.example.medrecroomdb.model.Admin;
import com.example.medrecroomdb.model.Doctor;
import com.example.medrecroomdb.model.Patient;
import com.example.medrecroomdb.viewmodel.AdminViewModel;
import com.example.medrecroomdb.viewmodel.DoctorViewModel;
import com.example.medrecroomdb.viewmodel.PatientViewModel;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // Declare variables
    private DoctorViewModel doctorViewModel;
    private PatientViewModel patientViewModel;
    private AdminViewModel adminViewModel;
    EditText editTextId, editTextPassword;
    Button buttonLogin;
    String role = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up references
        doctorViewModel = ViewModelProviders.of(this).get(DoctorViewModel.class);
        patientViewModel = ViewModelProviders.of(this).get(PatientViewModel.class);
        adminViewModel = ViewModelProviders.of(this).get(AdminViewModel.class);

        editTextId = findViewById(R.id.txtLoginId);
        editTextPassword = findViewById(R.id.txtLoginPassword);

        buttonLogin = findViewById(R.id.btnLogin);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.user_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(this);

        // Set up click listener for buttonLogin
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // get doctorId, password from EditText
                    String userName = editTextId.getText().toString();
                    String password = editTextPassword.getText().toString();


                    // Set up variable doctor references of Type Doctor to find doctor by doctorId by findByDoctorId()
                    Doctor doctor = doctorViewModel.findByDoctorEmail(userName);

                    // Set up variable doctor references of Type Doctor to find doctor by doctorId by findByDoctorId()
                    Patient patient = patientViewModel.findByPatientEmail(userName);

                    // Set up variable doctor references of Type Doctor to find doctor by doctorId by findByDoctorId()
                    Admin admin = adminViewModel.findByAdminEmail(userName);

                    // Validate if nurseId and Password match the info in AppDatabase and if both are filled, return successful result
                    if (doctor != null && doctor.getEmail().equals(userName) && doctor.getPassword().equals(password) && role == "Doctor") {
                        Intent intentDoctor = new Intent(v.getContext(), DoctorSearchPatientActivity.class);
                        startActivity(intentDoctor);
                    } else if (patient != null && patient.getEmail().equals(userName) && patient.getPassword().equals(password) && role == "Patient") {
                        Intent intentPatient = new Intent(v.getContext(), PatientNavActivity.class);
                        intentPatient.putExtra("userName", userName);
                        startActivity(intentPatient);
                    } else if (admin != null && admin.getEmail().equals(userName)&& admin.getPassword().equals(password) && role == "Admin") {
                        Intent intentAdmin = new Intent(v.getContext(), AdminSearchUserActivity.class);
                        startActivity(intentAdmin);
                    } else {
                        // Otherwise, show error message
                        Toast.makeText(getApplicationContext(), "Invalid username/password/role", Toast.LENGTH_SHORT).show();
                    }
                } catch(Exception e) {
                    Toast.makeText(getApplicationContext(), "Please enter username and password.", Toast.LENGTH_SHORT).show();
                    System.out.print(e.getMessage());
                }
            }
        });
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        parent.getItemAtPosition(pos);
        Button registerButton = (Button)findViewById(R.id.btnRegister);

        switch(parent.getItemAtPosition(pos).toString())
        {
            case "Patient":
                role = "Patient";
                registerButton.setOnClickListener(new View.OnClickListener() {
                    //Implement the event handler method
                    public void onClick(View v) {
                        Intent intentPatient = new Intent(v.getContext(), PatientActivity.class);
                        startActivity(intentPatient);
                    }
                });

                break;
            case "Doctor":
                role = "Doctor";
                registerButton.setOnClickListener(new View.OnClickListener() {
                    //Implement the event handler method
                    public void onClick(View v) {
                        Intent intentDoctor = new Intent(v.getContext(), DoctorActivity.class);
                        startActivity(intentDoctor);
                    }
                });
                break;
            case "Admin":
                role = "Admin";
                registerButton = (Button) findViewById(R.id.btnRegister);
                registerButton.setOnClickListener(new View.OnClickListener() {
                    //Implement the event handler method
                    public void onClick(View v) {
                        Intent intentAdmin = new Intent(v.getContext(), AdminActivity.class);
                        startActivity(intentAdmin);
                    }
                });
                break;
            case "":
                role = "No Role Selected";
                registerButton.setOnClickListener(new View.OnClickListener() {
                    //Implement the event handler method
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(),"Please select your Role", Toast.LENGTH_SHORT).show();
                    }
                });

        }
    }

    public void onNothingSelected(AdapterView<?> parent) {

    }
}