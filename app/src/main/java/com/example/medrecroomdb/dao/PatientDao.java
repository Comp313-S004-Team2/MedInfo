package com.example.medrecroomdb.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.medrecroomdb.model.Doctor;
import com.example.medrecroomdb.model.Patient;

import java.util.List;

@Dao
public interface PatientDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insert(Patient Patient);

    @Update
    void update(Patient patient);

    @Query("SELECT * FROM Patient WHERE patientId = :patientId")
    Patient findByPatientId(int patientId); // find patient based on patientId

    @Query("SELECT * FROM Patient WHERE healthcardNumber = :healthcardNumber")
    Patient findByPatientHealthcard(String healthcardNumber); // find patient based on healthcard number

    @Query("SELECT * FROM Patient WHERE email = :email")
    Patient findByPatientEmail(String email); // find patient based on email

    //Monitoring Query Result Changes with Live Data
    @Query("select * from Patient order by patientId")
    LiveData<List<Patient>> getAllPatients();
}
