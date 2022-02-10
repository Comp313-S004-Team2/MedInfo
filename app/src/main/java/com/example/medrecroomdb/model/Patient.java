package com.example.medrecroomdb.model;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = {"healthcardNumber"},
        unique = true), @Index(value = {"email"},
        unique = true)})

public class Patient {
    @PrimaryKey
    private int patientId;

    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private int phoneNumber;
    private String healthcardNumber;
    private String password;

    public int getPatientId() {
        return patientId;
    }
    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getHealthcardNumber() {
        return healthcardNumber;
    }
    public void setHealthcardNumber(String healthcardNumber) {
        this.healthcardNumber = healthcardNumber;
    }

    public String getPassword() {return password; }
    public void setPassword(String password) { this.password = password; }
}