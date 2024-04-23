package com.amanda.MedicationTracker.model;

import org.springframework.cglib.core.Local;

import java.time.LocalTime;

public class PetMedication {
    private int medId;
    private int petId;
    private String dose;
    private String frequency;

    private String purpose;
    private boolean wantReminder;
    private boolean given;
    private LocalTime timeGiven;

    public PetMedication(String dose, String frequency, String purpose, boolean wantReminder, boolean given) {
        this.dose = dose;
        this.frequency = frequency;
        this.purpose = purpose;
        this.wantReminder = wantReminder;
        this.given = given;
    }

    public int getMedId() {
        return medId;
    }

    public void setMedId(int medId) {
        this.medId = medId;
    }

    public PetMedication() {
    }

    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
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

    public boolean isGiven() {
        return given;
    }

    public void setGiven(boolean given) {
        this.given = given;
    }

    public LocalTime getTimeGiven() {
        return timeGiven;
    }

    public void setTimeGiven(LocalTime timeGiven) {
        this.timeGiven = timeGiven;
    }
}
