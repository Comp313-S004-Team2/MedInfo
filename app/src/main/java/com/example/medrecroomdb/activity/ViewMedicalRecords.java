package com.example.medrecroomdb.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.query.Where;
import com.amplifyframework.datastore.generated.model.RecordMetadata;
import com.amplifyframework.datastore.generated.model.User;
import com.example.medrecroomdb.R;
import com.example.medrecroomdb.RVMedRecListAdapter;

import java.util.ArrayList;

public class ViewMedicalRecords extends AppCompatActivity {

    ArrayList<RecordMetadata> recordMetadatas;
    String healthCardToSearch;
    SharedPreferences sharedPreferences;
    RecyclerView rvViewMedicalRecords;
    RVMedRecListAdapter rvMedRecListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_medical_records);


        recordMetadatas = new ArrayList<RecordMetadata>();

        sharedPreferences = getSharedPreferences("doctorSearchPatient", MODE_PRIVATE);
        healthCardToSearch = sharedPreferences.getString("healthCardToSearch", null);

        rvViewMedicalRecords = findViewById(R.id.rvMedicalRecords);
        rvViewMedicalRecords.setLayoutManager(new LinearLayoutManager((this)));

        Log.i("healthCard", healthCardToSearch);


    }

    @Override
    protected void onResume() {
        super.onResume();
        recordMetadatas = new ArrayList<RecordMetadata>();
        Amplify.DataStore.query(
                RecordMetadata.class, Where.matches(RecordMetadata.PATIENT_ID.eq(healthCardToSearch)),
                items -> {
                    while (items.hasNext()) {
                        RecordMetadata item = items.next();
                        recordMetadatas.add(item);
                        Log.i("Amplify", "Id " + item.getId());
                    }
                    runOnUiThread(() -> {
                        rvMedRecListAdapter = new RVMedRecListAdapter(this, recordMetadatas);
                        rvViewMedicalRecords.setAdapter(rvMedRecListAdapter);
                    });
                },
                failure -> Log.e("Amplify", "Could not query DataStore", failure)
        );
    }

    public void onAddRecord(View view){
        Intent addRecordIntent = new Intent(this, AddRecord.class);
        startActivity(addRecordIntent);
    }
}