package com.example.medrecroomdb.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.medrecroomdb.model.Doctor;

import java.util.List;

@Dao
public interface DoctorDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insert(Doctor Doctor);

    @Update
    void update(Doctor doctor);

    @Query("SELECT * FROM Doctor WHERE doctorId = :doctorId")
    Doctor findByDoctorId(int doctorId); // find patient based on doctorId

    @Query("SELECT * FROM Doctor WHERE email = :email")
    Doctor findByDoctorEmail(String email); // find patient based on email

    @Query("select * from Doctor order by doctorId")
    LiveData<List<Doctor>> getAllDoctors();
}
