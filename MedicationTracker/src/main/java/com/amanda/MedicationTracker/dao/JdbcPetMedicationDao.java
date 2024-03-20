package com.amanda.MedicationTracker.dao;

import com.amanda.MedicationTracker.exception.DaoException;
import com.amanda.MedicationTracker.model.PetMedication;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcPetMedicationDao implements PetMedicationDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcPetMedicationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void assignMedicationToPet(int medId, int petId, String dose, String frequency, String purpose,
                                                    boolean wantReminder, boolean given) {
        // Check if both the medication and the pet exist first
        if (medicationExists(medId) && petExists(petId)) {
            String insertPetMedicationSql = "INSERT INTO pet_medication (med_id, pet_id, dose, frequency, purpose, want_reminder, given) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try {
                jdbcTemplate.update(insertPetMedicationSql, medId, petId, dose, frequency, purpose, wantReminder, given);
            } catch (CannotGetJdbcConnectionException e) {
                throw new DaoException("Error inserting into pet_medication table", e);
            }
        } else {
            // Handle if medication or pet doesn't exist
            throw new DaoException("Medication or Pet does not exist.");
        }
    }

    private boolean medicationExists(int medId) {
        String sql = "SELECT COUNT(*) FROM medication WHERE med_id = ?";
        try {
            int count = jdbcTemplate.queryForObject(sql, Integer.class, medId);
            return count > 0;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    private boolean petExists(int petId) {
        String sql = "SELECT COUNT(*) FROM pet WHERE pet_id = ?";
        try {
            int count = jdbcTemplate.queryForObject(sql, Integer.class, petId);
            return count > 0;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    private PetMedication mapRowToPetMedication(SqlRowSet rs) {
        PetMedication petMedication = new PetMedication();
        petMedication.setPetId(rs.getInt("pet_id"));
        petMedication.setMedId(rs.getInt("med_id"));
        petMedication.setDose(rs.getString("dose"));
        petMedication.setFrequency(rs.getString("frequency"));
        petMedication.setPurpose(rs.getString("purpose"));
        petMedication.setWantReminder(rs.getBoolean("wantReminder"));
        petMedication.setGiven(rs.getBoolean("given"));
        return petMedication;
    }

}
