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
    public void assignMedicationToPet(int medId, int petId) {
        // Check if both the medication and the pet exist
        if (medicationExists(medId) && petExists(petId)) {
            String insertPetMedicationSql = "INSERT INTO pet_medication (med_id, pet_id) VALUES (?, ?)";
            try {
                jdbcTemplate.update(insertPetMedicationSql, medId, petId);
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
        // Assuming you have other properties in the PetMedication class, you can set them here if needed
        return petMedication;
    }

}
