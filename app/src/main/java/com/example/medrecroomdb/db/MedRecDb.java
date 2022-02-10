package com.example.medrecroomdb.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.medrecroomdb.dao.AdminDao;
import com.example.medrecroomdb.dao.DoctorDao;
import com.example.medrecroomdb.dao.PatientDao;
import com.example.medrecroomdb.model.Admin;
import com.example.medrecroomdb.model.Doctor;
import com.example.medrecroomdb.model.Patient;

@Database(entities = {Patient.class, Doctor.class, Admin.class}, version = 1, exportSchema = false)
public abstract class MedRecDb extends RoomDatabase {
    //
    private static volatile MedRecDb INSTANCE;
    private static final String DATABASE_NAME = "StudentDB";
    public abstract PatientDao patientDao();
    public abstract DoctorDao doctorDao();
    public abstract AdminDao adminDao();
    //
    public static synchronized MedRecDb getInstance(Context context) {
        if (INSTANCE == null) {
            //Create database object
            INSTANCE = Room.databaseBuilder(context,
                    MedRecDb.class, DATABASE_NAME)
                    .allowMainThreadQueries() // To access database on main thread for testing
                    .build();
        }
        return INSTANCE;
    }
}
