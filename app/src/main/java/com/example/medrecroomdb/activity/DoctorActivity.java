package com.example.medrecroomdb.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medrecroomdb.R;
import com.example.medrecroomdb.model.Doctor;
import com.example.medrecroomdb.viewmodel.DoctorViewModel;

import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DoctorActivity extends AppCompatActivity {

    private DoctorViewModel doctorViewModel;
    private Button btnAddDoctor;
    private EditText etDoctorFirstName, etDoctorLastName, etDoctorEmail, etDoctorLicense, etPassword, etDoctorAddress, etDoctorPhoneNumber;
    Doctor doctor;

    boolean allFieldsChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);

        etDoctorFirstName = findViewById(R.id.txtDoctorFirstName);
        etDoctorLastName = findViewById(R.id.txtDoctorLastName);
        etDoctorEmail = findViewById(R.id.txtDoctorEmail);
        etDoctorAddress = findViewById(R.id.txtDoctorAddress);
        etDoctorPhoneNumber = findViewById(R.id.txtDoctorPhoneNumber);
        etDoctorLicense = findViewById(R.id.txtDoctorLicense);
        etPassword = findViewById(R.id.txtPassword);

        TableLayout displayTable = (TableLayout) findViewById(R.id.displayTable);
        doctorViewModel = ViewModelProviders.of(this).get(DoctorViewModel.class);
        //
        doctor = new Doctor();
        //
        doctorViewModel.getInsertResult().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer result) {
                if (result == 1) {
                    Toast.makeText(DoctorActivity.this, "Doctor successfully saved", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DoctorActivity.this, "Error saving doctor", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // if the LiveData already has data it will delivered
        // to the observer
        doctorViewModel.getAllDoctors().observe(this, new Observer<List<Doctor>>() {
            @Override
            public void onChanged(@Nullable List<Doctor> result) {
                String output = "";
                displayTable.removeViews(1, Math.max(0, displayTable.getChildCount() - 1));
                for (Doctor doctor : result) {
                    TableRow row = new TableRow(getApplicationContext());
                    TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                    lp.weight = 1;
                    //lp.setMargins(10, 5, 10, 5);
                    row.setLayoutParams(lp);

                    TextView id = new TextView(getApplicationContext());
                    id.setTextSize(10);
                    id.setLayoutParams(lp);
                    id.setText(String.valueOf(doctor.getDoctorId()));
                    row.addView(id);

                    TextView firstName = new TextView(getApplicationContext());
                    firstName.setTextSize(10);
                    firstName.setLayoutParams(lp);
                    firstName.setText(doctor.getFirstName());
                    row.addView(firstName);

                    TextView lastName = new TextView(getApplicationContext());
                    lastName.setTextSize(10);
                    lastName.setLayoutParams(lp);
                    lastName.setText(doctor.getLastName());
                    row.addView(lastName);

                    TextView address = new TextView(getApplicationContext());
                    address.setTextSize(10);
                    address.setLayoutParams(lp);
                    address.setText(doctor.getAddress());
                    row.addView(address);

                    TextView department = new TextView(getApplicationContext());
                    department.setTextSize(10);
                    department.setLayoutParams(lp);
                    department.setText(doctor.getEmail());
                    row.addView(department);

                    TextView phone = new TextView(getApplicationContext());
                    phone.setTextSize(10);
                    phone.setLayoutParams(lp);
                    phone.setText(String.valueOf(doctor.getPhoneNumber()));
                    row.addView(phone);

                    TextView professorId = new TextView(getApplicationContext());
                    professorId.setTextSize(10);
                    professorId.setText(String.valueOf(doctor.getDoctorLicenseNumber()));
                    row.addView(professorId);

                    TextView classroom = new TextView(getApplicationContext());
                    classroom.setTextSize(10);
                    classroom.setLayoutParams(lp);
                    classroom.setText(String.valueOf(doctor.getPassword()));
                    row.addView(classroom);

                    displayTable.addView(row);
                }
            }
        });


        btnAddDoctor = findViewById(R.id.btnAddDoctor);
        btnAddDoctor.setOnClickListener(new View.OnClickListener() {
            //Implement the event handler method
            public void onClick(View v) {
                allFieldsChecked = CheckAllFields();
                if (allFieldsChecked) {
                    final int random = new Random().nextInt(100000 - 1) + 100000;
                    doctor.setDoctorId(Integer.parseInt(Integer.toString(random)));

                    doctor.setFirstName(etDoctorFirstName.getText().toString());

                    doctor.setLastName(etDoctorLastName.getText().toString());

                    doctor.setAddress(etDoctorAddress.getText().toString());

                    doctor.setPhoneNumber(Integer.parseInt(etDoctorPhoneNumber.getText().toString()));

                    doctor.setEmail(etDoctorEmail.getText().toString());

                    doctor.setDoctorLicenseNumber(etDoctorLicense.getText().toString());

                    doctor.setPassword(etPassword.getText().toString());

                    doctorViewModel.insert(doctor);

                    etDoctorFirstName.setText("");
                    etDoctorLastName.setText("");
                    etDoctorEmail.setText("");
                    etDoctorAddress.setText("");
                    etDoctorPhoneNumber.setText("");
                    etDoctorLicense.setText("");
                    etPassword.setText("");
                }
            }
        });
    }

    // function to check all text fields
    private boolean CheckAllFields() {
        if (etDoctorFirstName.length() == 0) {
            etDoctorFirstName.setError("This field is required");
            return false;
        }
        if (etDoctorLastName.length() == 0) {
            etDoctorLastName.setError("This field is required");
            return false;
        }

        if (etDoctorEmail.length() == 0) {
            etDoctorEmail.setError("This field is required");
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(etDoctorEmail.getText().toString()).matches()) {
            etDoctorEmail.setError("Please enter a valid email");
            return false;
        }
        if (etDoctorAddress.length() == 0) {
            etDoctorAddress.setError("This field is required");
            return false;
        }
        if (etDoctorPhoneNumber.length() == 0) {
            etDoctorPhoneNumber.setError("This field is required");
            return false;
        }
        if (etDoctorLicense.length() == 0) {
            etDoctorLicense.setError("This field is required");
            return false;
        }
        if (etPassword.length() == 0) {
            etPassword.setError("This field is required");
            return false;
        } else if (etPassword.length() < 8) {
            etPassword.setError("Password must be minimum 8 characters");
            return false;
        }
        return true;
    }
}