package com.amanda.MedicationTracker.model;

import org.springframework.cglib.core.Local;

import java.time.LocalTime;

public class Medication {

    private int medId;
    private String name;


    public Medication() {
    }

    public int getMedId() {
        return medId;
    }

    public void setMedId(int medId) {
        this.medId = medId;
    }


    public Medication(int medId, String name) {
        this.medId = medId;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
