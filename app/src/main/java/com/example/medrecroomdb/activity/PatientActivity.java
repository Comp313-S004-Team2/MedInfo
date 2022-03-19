package com.example.medrecroomdb.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medrecroomdb.MainActivity;
import com.example.medrecroomdb.R;
import com.example.medrecroomdb.model.Patient;
import com.example.medrecroomdb.viewmodel.PatientViewModel;

import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatientActivity extends AppCompatActivity {

    private PatientViewModel patientViewModel;
    private Button btnAddPatient;
    private EditText etPatientFirstName, etPatientLastName, etPatientAddress, etPatientEmail, etPatientPhoneNumber, etPatientHealthcardNumber, etPassword;
    Patient patient;

    boolean allFieldsChecked = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);

        etPatientFirstName = findViewById(R.id.txtPatientFirstName);
        etPatientLastName = findViewById(R.id.txtPatientLastName);
        etPatientAddress = findViewById(R.id.txtPatientAddress);
        etPatientEmail = findViewById(R.id.txtPatientEmail);
        etPatientPhoneNumber = findViewById(R.id.txtPatientPhoneNumber);
        etPatientHealthcardNumber = findViewById(R.id.txtpatientHealthcard);
        etPassword = findViewById(R.id.txtPassword);

        TableLayout displayTable = (TableLayout) findViewById(R.id.displayTable);
        patientViewModel = ViewModelProviders.of(this).get(PatientViewModel.class);
        //
        patient = new Patient();
        //
        patientViewModel.getInsertResult().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer result) {
                if (result == 1) {
                    Toast.makeText(PatientActivity.this, "Patient registration successful", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PatientActivity.this, "Error registering patient", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // if the LiveData already has data it will delivered
        // to the observer
        patientViewModel.getAllPatients().observe(this, new Observer<List<Patient>>() {
            @Override
            public void onChanged(@Nullable List<Patient> result) {
                String output = "";
                displayTable.removeViews(1, Math.max(0, displayTable.getChildCount() - 1));
                for (Patient patient : result) {
                    TableRow row = new TableRow(getApplicationContext());
                    TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                    lp.weight = 1;
                    //lp.setMargins(10, 5, 10, 5);

                    row.setLayoutParams(lp);

                    TextView id = new TextView(getApplicationContext());
                    id.setTextSize(10);
                    id.setLayoutParams(lp);
                    id.setText(String.valueOf(patient.getPatientId()));
                    row.addView(id);

                    TextView firstName = new TextView(getApplicationContext());
                    firstName.setTextSize(10);
                    firstName.setLayoutParams(lp);
                    firstName.setText(patient.getFirstName());
                    row.addView(firstName);

                    TextView lastName = new TextView(getApplicationContext());
                    lastName.setTextSize(10);
                    lastName.setLayoutParams(lp);
                    lastName.setText(patient.getLastName());
                    row.addView(lastName);

                    TextView address = new TextView(getApplicationContext());
                    address.setTextSize(10);
                    address.setLayoutParams(lp);
                    address.setText(patient.getAddress());
                    row.addView(address);

                    TextView department = new TextView(getApplicationContext());
                    department.setTextSize(10);
                    //department.setLayoutParams(lp);
                    department.setText(patient.getEmail());
                    row.addView(department);

                    TextView phone = new TextView(getApplicationContext());
                    phone.setTextSize(10);
                    phone.setLayoutParams(lp);
                    phone.setText(String.valueOf(patient.getPhoneNumber()));
                    row.addView(phone);

                    TextView professorId = new TextView(getApplicationContext());
                    professorId.setTextSize(10);
                    professorId.setLayoutParams(lp);
                    professorId.setText(String.valueOf(patient.getHealthcardNumber()));
                    row.addView(professorId);

                    TextView classroom = new TextView(getApplicationContext());
                    classroom.setTextSize(10);
                    classroom.setLayoutParams(lp);
                    classroom.setText(String.valueOf(patient.getPassword()));
                    row.addView(classroom);

                    displayTable.addView(row);
                }
            }
        });


        btnAddPatient = findViewById(R.id.btnAddPatient);
        btnAddPatient.setOnClickListener(new View.OnClickListener() {
            //Implement the event handler method
            public void onClick(View v) {
                allFieldsChecked = CheckAllFields();
                if (allFieldsChecked) {
                    final int random = new Random().nextInt(100000 - 1) + 100000;
                    patient.setPatientId(Integer.parseInt(Integer.toString(random)));

                    patient.setFirstName(etPatientFirstName.getText().toString());

                    patient.setLastName(etPatientLastName.getText().toString());

                    patient.setAddress(etPatientAddress.getText().toString());

                    patient.setEmail(etPatientEmail.getText().toString());

                    patient.setPhoneNumber(Integer.parseInt(etPatientPhoneNumber.getText().toString()));

                    patient.setHealthcardNumber(etPatientHealthcardNumber.getText().toString());

                    patient.setPassword(etPassword.getText().toString());

                    patientViewModel.insert(patient);

                    etPatientFirstName.setText("");
                    etPatientLastName.setText("");
                    etPatientAddress.setText("");
                    etPatientEmail.setText("");
                    etPatientPhoneNumber.setText("");
                    etPatientHealthcardNumber.setText("");
                    etPassword.setText("");
                    finish();
                }
            }

        });

    }

    // function to check all text fields
    private boolean CheckAllFields() {
        try {
            if (etPatientFirstName.length() == 0) {
            etPatientFirstName.setError("This field is required");
            return false;
            }
            if (etPatientLastName.length() == 0) {
                etPatientLastName.setError("This field is required");
                return false;
            }
            if (etPatientEmail.length() == 0) {
                etPatientEmail.setError("This field is required");
                return false;
            }
            if (etPatientAddress.length() == 0) {
                etPatientAddress.setError("This field is required");
                return false;
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(etPatientEmail.getText().toString()).matches()) {
                etPatientEmail.setError("Please enter a valid email");
                return false;
            }
            if (etPatientPhoneNumber.length() == 0) {
                etPatientPhoneNumber.setError("This field is required");
                return false;
            }
            if (etPatientHealthcardNumber.length() == 0) {
                etPatientHealthcardNumber.setError("This field is required");
                return false;
            }
            if (etPassword.length() == 0) {
                etPassword.setError("This field is required");
                return false;
            } else if (etPassword.length() < 8) {
                etPassword.setError("Password must be minimum 8 characters");
                return false;
            }

        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(),e.getMessage().toString(),Toast.LENGTH_LONG);
        }
        return true;
    }
}