package com.example.medrecroomdb.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.query.Where;
import com.amplifyframework.datastore.generated.model.User;
import com.example.medrecroomdb.R;
import com.example.medrecroomdb.RCDoctorListAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DoctorList extends AppCompatActivity {

    private ArrayList<User> doctors;
    private RecyclerView rvDoctorList;
    private RCDoctorListAdapter rcDoctorListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);
        rvDoctorList = findViewById(R.id.rvDoctorList);
        rvDoctorList.setLayoutManager(new LinearLayoutManager(this));
        doctors = new ArrayList<User>();

        Amplify.DataStore.query(User.class, Where.matches(User.ROLE.eq("Doctor")),
                doctor -> {
                    while (doctor.hasNext()) {
                        User doc = doctor.next();
                        doctors.add(doc);
                        Log.i("User", doc.toString());
                    }
                    runOnUiThread(() -> {
                        rcDoctorListAdapter = new RCDoctorListAdapter(doctors, this);
                        rvDoctorList.setAdapter(rcDoctorListAdapter);
                    });
                },
                failure -> Log.e("MyAmplifyApp", "Query failed.", failure)
        );
    }
}