package com.example.medrecroomdb.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.medrecroomdb.AdminRepository;
import com.example.medrecroomdb.DoctorRepository;
import com.example.medrecroomdb.PatientRepository;
import com.example.medrecroomdb.model.Admin;
import com.example.medrecroomdb.model.Doctor;
import com.example.medrecroomdb.model.Patient;
import java.util.List;

public class AdminViewModel extends AndroidViewModel {
    private AdminRepository adminRepository;
    private LiveData<Integer> insertResult;
    private LiveData<List<Admin>> allAdmins;

    public AdminViewModel(@NonNull Application application) {
        super(application);
        adminRepository = new AdminRepository(application);
        insertResult = adminRepository.getInsertResult();
        allAdmins = adminRepository.getAdminList();
    }
    //calls repository to insert a classroom
    public void insert(Admin admin) {
        adminRepository.insert(admin);
    }
    //gets insert results as LiveData object
    public LiveData<Integer> getInsertResult() {
        return insertResult;
    }
    //returns query results as live data object
    public LiveData<List<Admin>> getAllAdmins() { return allAdmins; }

//    // Create a wrapper findByAdminId() method that calls the Repository’s findByAdminId() method
    public Admin findByAdminId(int adminId) { return adminRepository.findByAdminId(adminId); }

    // Create a wrapper findByAdminEmail() method that calls the Repository’s findByAdminEmail() method
    public Admin findByAdminEmail(String email) { return adminRepository.findByAdminEmail(email); }
}
