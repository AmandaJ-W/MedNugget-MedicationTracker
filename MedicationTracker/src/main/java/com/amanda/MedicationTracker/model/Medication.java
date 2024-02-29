package com.amanda.MedicationTracker.model;

import java.time.LocalTime;

public class Medication {

    private int medId;
    private String name;
    private String dose;
    private String frequency;
    private LocalTime time;

    private String purpose;
    private boolean wantReminder;

    public Medication() {
    }

    public int getMedId() {
        return medId;
    }

    public void setMedId(int medId) {
        this.medId = medId;
    }

    public Medication(int medId, String name, String dose, String frequency, LocalTime time, String purpose, boolean wantReminder) {
        this.medId = medId;
        this.name = name;
        this.dose = dose;
        this.frequency = frequency;
        this.time = time;
        this.purpose = purpose;
        this.wantReminder = wantReminder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public boolean isWantReminder() {
        return wantReminder;
    }

    public void setWantReminder(boolean wantReminder) {
        this.wantReminder = wantReminder;
    }
}
