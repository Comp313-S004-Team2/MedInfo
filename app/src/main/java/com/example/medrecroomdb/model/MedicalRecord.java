package com.example.medrecroomdb.model;

public class MedicalRecord {
    public MedicalRecord(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

}
