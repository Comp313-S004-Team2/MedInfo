package com.example.medrecroomdb.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.medrecroomdb.R;

public class Empty extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);
        finish();
    }
}