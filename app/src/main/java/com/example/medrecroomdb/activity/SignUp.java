package com.example.medrecroomdb.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.query.Where;
import com.amplifyframework.datastore.generated.model.User;
import com.example.medrecroomdb.R;

public class SignUp extends AppCompatActivity {

    Spinner spinnerRole;
    TextView idType, tvSpinnerError;
    EditText etFname, etLName, etAddress, etEmail, etPhone, etIdNumber, etPassword;
    ArrayAdapter signUpRolesAdapter;
    String selectedItem;
    boolean isError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        etFname = findViewById(R.id.ptSignUpFName);
        etLName = findViewById(R.id.ptSignUpLName);
        etAddress = findViewById(R.id.ptSignUpAddress);
        etEmail = findViewById(R.id.ptSignUpEmail);
        etPhone = findViewById(R.id.ptSignUpPhoneNumber);
        etIdNumber = findViewById(R.id.ptSignUpIdNumber);
        etPassword = findViewById(R.id.ptSignUpPassword);
        idType = findViewById(R.id.tvSignUpIDtype);
        spinnerRole = findViewById(R.id.spinnerSignUpRole);
        tvSpinnerError = findViewById(R.id.tvSignUpSpinnerError);

        signUpRolesAdapter = ArrayAdapter.createFromResource(this, R.array.signup_role, R.layout.support_simple_spinner_dropdown_item);
        signUpRolesAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerRole.setAdapter(signUpRolesAdapter);

        spinnerRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedItem = adapterView.getItemAtPosition(i).toString();
                tvSpinnerError.setError(null);
                if(selectedItem.matches("Doctor")){
                    idType.setText("License#:");
                    etIdNumber.setHint("License Number");
                    etIdNumber.setText("");
                }
                else if(selectedItem.matches("Patient")){
                    idType.setText("Health Card#:");
                    etIdNumber.setHint("Health Card Number");
                    etIdNumber.setText("");
                }
                else if(selectedItem.matches("Medical Staff")){
                    idType.setText("Employee#:");
                    etIdNumber.setHint("Employee Number");
                    etIdNumber.setText("");
                }
                else {
                    idType.setText("ID#:");
                    etIdNumber.setHint("ID Number");
                    etIdNumber.setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    public void onSignUpAccount(View view){
        isError = false;
        if(selectedItem.matches("Role")){
            tvSpinnerError.setError("");
            isError =true;
        }
        if(etFname.getText().toString().isEmpty()){
            etFname.setError("First Name is Required");
            isError =true;
        }
        if(etLName.getText().toString().isEmpty()){
            etLName.setError("Last Name is Required");
            isError =true;
        }
        if(etAddress.getText().toString().isEmpty()){
            etAddress.setError("Address is Required");
            isError =true;
        }
        if(etEmail.getText().toString().isEmpty()){
            etEmail.setError("Email is Required");
            isError =true;
        }
        if(etPhone.getText().toString().isEmpty()){
            etPhone.setError("Phone Number is Required");
            isError =true;
        }
        if(etIdNumber.getText().toString().isEmpty()){
            if(etIdNumber.getHint().toString().matches("ID Number")){
                etIdNumber.setError("Please Select a Role");
            }else{
                etIdNumber.setError(etIdNumber.getHint().toString() +" is Required");
            }
            isError =true;
        }
        if(etPassword.getText().toString().isEmpty()){
            etPassword.setError("Password is Required");
            isError =true;
        }
        Amplify.DataStore.query(User.class, Where.matches(User.EMAIL.eq(etEmail.getText().toString()).or(User.ID_NUMBER.eq(etIdNumber.getText().toString()))),
                goodPosts -> {
                    if (goodPosts.hasNext()) {
                        User user = goodPosts.next();
                        if(user.getEmail().matches(etEmail.getText().toString())){
                            runOnUiThread(() -> {
                                etEmail.setError("Email already Exist");
                            });
                        }
                        if(user.getIdNumber().matches(etIdNumber.getText().toString())){
                            runOnUiThread(() -> {
                                etIdNumber.setError(etIdNumber.getHint().toString() + " already Exist");
                            });
                        }
                        isError = true;
                    }
                },
                failure -> Log.e("MyAmplifyApp", "Query failed.", failure)
        );
        if(!isError){
            User item = User.builder()
                    .firstName(etFname.getText().toString())
                    .lastName(etLName.getText().toString())
                    .email(etEmail.getText().toString())
                    .idNumber(etIdNumber.getText().toString())
                    .password(etPassword.getText().toString())
                    .address(etAddress.getText().toString())
                    .phoneNumber(etPhone.getText().toString())
                    .role(selectedItem)
                    .build();
            Amplify.DataStore.save(
                    item,
                    success -> {Log.i("Amplify", "Saved item: " + success.item().getId());
                    runOnUiThread(() -> {
                        Toast.makeText(this, "Sign Up Successful", Toast.LENGTH_SHORT).show();
                    });},
                    error -> Log.e("Amplify", "Could not save item to DataStore", error)
            );
            finish();
        }
    }
}