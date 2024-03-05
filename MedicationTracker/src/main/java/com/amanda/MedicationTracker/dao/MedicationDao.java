package com.amanda.MedicationTracker.dao;

import com.amanda.MedicationTracker.model.Medication;

import java.util.List;

public interface MedicationDao {

    // List all medications
    List<Medication> listAllMedications();
    // Get medication by ID
    Medication getMedicationById(int medId);
    // Get medication by name
    List<Medication> getMedicationByName(String name);
    // Add medication
    Medication addMedication(Medication newMedication);
    // Remove medication
    int deleteMedication(int medId); // returns num rows

    // Update medication
    Medication updateMedication(Medication medication);
    List<Medication> getMedicationByPetName(String petName);
}
