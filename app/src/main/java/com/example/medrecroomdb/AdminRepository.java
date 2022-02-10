package com.example.medrecroomdb;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.medrecroomdb.dao.AdminDao;
import com.example.medrecroomdb.dao.DoctorDao;
import com.example.medrecroomdb.dao.PatientDao;
import com.example.medrecroomdb.db.MedRecDb;
import com.example.medrecroomdb.model.Admin;
import com.example.medrecroomdb.model.Doctor;
import com.example.medrecroomdb.model.Patient;

import java.util.List;

public class AdminRepository {
    private final AdminDao adminDao;
    private MutableLiveData<Integer> insertResult = new MutableLiveData<>();
    private LiveData<List<Admin>> adminList;
    private LiveData<Admin> admin;

    public AdminRepository(Context context) {
        //create a database object
        MedRecDb db = MedRecDb.getInstance(context);
        //create an interface object
        adminDao = db.adminDao();
        //call interface method
        adminList = adminDao.getAllAdministrators();
    }

    // returns query results as LiveData object
    public LiveData<List<Admin>> getAdminList() {
        return adminList;
    }

//    // Find admin, this is called from ViewModel
    public Admin findByAdminId(int adminId) { return adminDao.findByAdminId(adminId); }

    // Create a wrapper findByAdminEmail() method that calls the Repository’s findByAdminEmail() method
    public Admin findByAdminEmail(String email) { return adminDao.findByAdminEmail(email); }

    //inserts a person asynchronously
    public void insert(Admin admin) {
        insertAsync(admin);
    }

    // returns insert results as LiveData object
    public LiveData<Integer> getInsertResult() {
        return insertResult;
    }

    private void insertAsync(final Admin admin) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    adminDao.insert(admin);
                    insertResult.postValue(1);
                } catch (Exception e) {
                    insertResult.postValue(0);
                }
            }
        }).start();
    }
}

