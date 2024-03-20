package com.amanda.MedicationTracker.dao;

public interface PetMedicationDao {

    // Associate a medication with a pet
    void assignMedicationToPet(int medId, int petId, String dose, String frequency, String purpose,
                               boolean wantReminder, boolean given);
}
