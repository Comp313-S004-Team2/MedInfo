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
import com.example.medrecroomdb.model.Admin;
import com.example.medrecroomdb.model.Doctor;
import com.example.medrecroomdb.model.Patient;
import com.example.medrecroomdb.viewmodel.AdminViewModel;
import com.example.medrecroomdb.viewmodel.DoctorViewModel;
import com.example.medrecroomdb.viewmodel.PatientViewModel;

import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdminActivity extends AppCompatActivity {

    private AdminViewModel adminViewModel;
    private Button btnAddAdmin;
    private EditText etAdminFirstName, etAdminLastName, etAdminEmail, etAdminEmployeeNumber, etPassword, etAdminAddress, etAdminPhoneNumber;
    Admin admin;

    // one boolean to check all fields
    boolean allFieldsChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        etAdminFirstName = findViewById(R.id.txtAdminFirstName);
        etAdminLastName = findViewById(R.id.txtAdminLastName);
        etAdminEmail = findViewById(R.id.txtAdminEmail);
        etAdminAddress = findViewById(R.id.txtAdminAddress);
        etAdminPhoneNumber = findViewById(R.id.txtAdminPhoneNumber);
        etAdminEmployeeNumber = findViewById(R.id.txtAdminEmployeeNumber);
        etPassword = findViewById(R.id.txtPassword);

        TableLayout displayTable = (TableLayout) findViewById(R.id.displayTable);
        adminViewModel = ViewModelProviders.of(this).get(AdminViewModel.class);
        //


        admin = new Admin();
        //
        adminViewModel.getInsertResult().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer result) {
                if (result == 1) {
                    Toast.makeText(AdminActivity.this, "Admin successfully saved", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AdminActivity.this, "Error saving admin", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // if the LiveData already has data it will delivered
        // to the observer
        adminViewModel.getAllAdmins().observe(this, new Observer<List<Admin>>() {
            @Override
            public void onChanged(@Nullable List<Admin> result) {
                String output = "";
                displayTable.removeViews(1, Math.max(0, displayTable.getChildCount() - 1));
                for (Admin admin : result) {
                    TableRow row = new TableRow(getApplicationContext());
                    TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                    lp.weight = 1;
                    //lp.setMargins(10, 5, 10, 5);
                    row.setLayoutParams(lp);

                    TextView id = new TextView(getApplicationContext());
                    id.setTextSize(10);
                    id.setLayoutParams(lp);
                    id.setText(String.valueOf(admin.getAdminId()));
                    row.addView(id);

                    TextView firstName = new TextView(getApplicationContext());
                    firstName.setTextSize(10);
                    firstName.setLayoutParams(lp);
                    firstName.setText(admin.getFirstName());
                    row.addView(firstName);

                    TextView lastName = new TextView(getApplicationContext());
                    lastName.setTextSize(10);
                    lastName.setLayoutParams(lp);
                    lastName.setText(admin.getLastName());
                    row.addView(lastName);

                    TextView address = new TextView(getApplicationContext());
                    address.setTextSize(10);
                    address.setLayoutParams(lp);
                    address.setText(admin.getAddress());
                    row.addView(address);

                    TextView department = new TextView(getApplicationContext());
                    department.setTextSize(10);
                    department.setLayoutParams(lp);
                    department.setText(admin.getEmail());
                    row.addView(department);

                    TextView professorId = new TextView(getApplicationContext());
                    professorId.setTextSize(10);
                    professorId.setLayoutParams(lp);
                    professorId.setText(String.valueOf(admin.getEmployeeId()));
                    row.addView(professorId);

                    TextView phone = new TextView(getApplicationContext());
                    phone.setTextSize(10);
                    phone.setLayoutParams(lp);
                    phone.setText(String.valueOf(admin.getPhoneNumber()));
                    row.addView(phone);

                    TextView classroom = new TextView(getApplicationContext());
                    classroom.setTextSize(10);
                    classroom.setLayoutParams(lp);
                    classroom.setText(String.valueOf(admin.getPassword()));
                    row.addView(classroom);

                    displayTable.addView(row);
                }
            }
        });
        btnAddAdmin = findViewById(R.id.btnAddAdmin);
        btnAddAdmin.setOnClickListener(new View.OnClickListener() {
            //Implement the event handler method
            public void onClick(View v) {
                allFieldsChecked = CheckAllFields();
                if (allFieldsChecked) {
                    final int random = new Random().nextInt(100000 - 1) + 100000;
                    admin.setAdminId(Integer.parseInt(Integer.toString(random)));

                    admin.setFirstName(etAdminFirstName.getText().toString());

                    admin.setLastName(etAdminLastName.getText().toString());

                    admin.setAddress(etAdminAddress.getText().toString());

                    admin.setPhoneNumber(Integer.parseInt(etAdminPhoneNumber.getText().toString()));

                    admin.setEmail(etAdminEmail.getText().toString());

                    admin.setEmployeeId(etAdminEmployeeNumber.getText().toString());

                    admin.setPassword(etPassword.getText().toString());

                    adminViewModel.insert(admin);

                    etAdminFirstName.setText("");
                    etAdminLastName.setText("");
                    etAdminEmail.setText("");
                    etAdminAddress.setText("");
                    etAdminPhoneNumber.setText("");
                    etAdminEmployeeNumber.setText("");
                    etPassword.setText("");
                    finish();
                }
            }
        });

    }

    // function to check all text fields
    private boolean CheckAllFields() {
        if (etAdminFirstName.length() == 0) {
            etAdminFirstName.setError("This field is required");
            return false;
        }
        if (etAdminLastName.length() == 0) {
            etAdminLastName.setError("This field is required");
            return false;
        }
        if (etAdminEmail.length() == 0) {
            etAdminEmail.setError("This field is required");
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(etAdminEmail.getText().toString()).matches()) {
            etAdminEmail.setError("Please enter a valid email");
            return false;
        }
        if (etAdminAddress.length() == 0) {
            etAdminAddress.setError("This field is required");
            return false;
        }
        if (etAdminPhoneNumber.length() == 0) {
            etAdminPhoneNumber.setError("This field is required");
            return false;
        }
        if (etAdminEmployeeNumber.length() == 0) {
            etAdminEmployeeNumber.setError("This field is required");
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