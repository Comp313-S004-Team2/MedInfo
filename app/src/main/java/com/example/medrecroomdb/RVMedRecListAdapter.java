package com.example.medrecroomdb;

import android.content.Context;

import com.amplifyframework.datastore.generated.model.RecordMetadata;

import java.util.ArrayList;

public class RVMedRecListAdapter {
    private ArrayList<RecordMetadata> recordMetadata = new ArrayList<RecordMetadata>();
    private Context context;
    private String patientId;

    public RVMedRecListAdapter(Context context, String patientId) {
        this.context = context;
        this.patientId = patientId;
    }
}
