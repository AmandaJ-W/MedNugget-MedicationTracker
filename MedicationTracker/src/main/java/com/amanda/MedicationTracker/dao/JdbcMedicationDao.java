package com.amanda.MedicationTracker.dao;

import com.amanda.MedicationTracker.exception.DaoException;
import com.amanda.MedicationTracker.model.Medication;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcMedicationDao implements MedicationDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcMedicationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Medication> listAllMedications() {
       List<Medication> medicationList = new ArrayList<>();
       String sql = "SELECT * FROM medication;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                Medication medication = mapRowToMedication(results);
                medicationList.add(medication);
            }
        }
        catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return medicationList;
    }

    @Override
    public Medication getMedicationById(int medId) {
        Medication medication = null;
        String sql = "SELECT * FROM medication WHERE med_id = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            if (results.next()) {
                medication = mapRowToMedication(results);
            }
        }
        catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return medication;
    }

    @Override
    public List<Medication> getMedicationByName(String name) {
        List<Medication> medicationsByNameList = new ArrayList<>();
        String sql = "SELECT * FROM medication WHERE name ILIKE ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, "%" + name + "%");
            while (results.next()) {
                Medication medication = mapRowToMedication(results);
                medicationsByNameList.add(medication);
            }
        }
        catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return medicationsByNameList;
    }

    //not working
    @Override
    public Medication addMedication(Medication newMedication) {
        String insertMedicationSql = "INSERT INTO medication (name) VALUES (?) RETURNING med_id";
        try {
            Integer generatedMedId = jdbcTemplate.queryForObject(
                    insertMedicationSql,
                    new Object[]{newMedication.getName()},
                    Integer.class
            );
            if (generatedMedId != null) {
                newMedication.setMedId(generatedMedId);
            } else {
                throw new DaoException("Failed to retrieve generated medication ID");
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataAccessException e) {
            throw new DaoException("Data access exception occurred", e);
        }
        return newMedication;
    }


//    @Override
//    public void assignMedicationToPet(int medId, int petId) {
//        // Check if both the medication and the pet exist
//        if (medicationExists(medId) && petExists(petId)) {
//            String insertPetMedicationSql = "INSERT INTO pet_medication (pet_id, med_id) VALUES (?, ?)";
//            try {
//                jdbcTemplate.update(insertPetMedicationSql, medId, petId);
//            } catch (CannotGetJdbcConnectionException e) {
//                throw new DaoException("Error inserting into pet_medication table", e);
//            }
//        } else {
//            // Handle if medication or pet doesn't exist
//            throw new DaoException("Medication or Pet does not exist.");
//        }
//    }
//
//    // methods to check that pet and medication exist
//    private boolean medicationExists(int medId) {
//        String sql = "SELECT COUNT(*) FROM medication WHERE med_id = ?";
//        try {
//            int count = jdbcTemplate.queryForObject(sql, Integer.class, medId);
//            return count > 0;
//        } catch (EmptyResultDataAccessException e) {
//            return false;
//        }
//    }

    private boolean petExists(int petId) {
        String sql = "SELECT COUNT(*) FROM pet WHERE pet_id = ?";
        try {
            int count = jdbcTemplate.queryForObject(sql, Integer.class, petId);
            return count > 0;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    @Override
    public int deleteMedication(int medId) {
        int affectedRows = 0;
        String removeFromJoinerSql = "DELETE FROM pet_medication WHERE pet_medication.med_id = ?;";
        String sql = "DELETE FROM medication WHERE medication.med_id = ?;";
        try {
            affectedRows += jdbcTemplate.update(removeFromJoinerSql, medId);
            affectedRows += jdbcTemplate.update(sql, medId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return affectedRows;
    }

    @Override
    public Medication updateMedication(Medication updatedMedication) {
        Medication medicationToUpdate = null;
        String sql = "UPDATE medication SET name=? WHERE med_id = ?;";
        try {
            int rowsAffected = jdbcTemplate.update(sql, updatedMedication.getName(), updatedMedication.getMedId());

            if (rowsAffected == 0) {
                throw new DaoException("This medication does not exist in the database. Unable to update.");
            } else {
                medicationToUpdate = getMedicationById(updatedMedication.getMedId());
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return medicationToUpdate;
    }


    @Override
    public List<Medication> getMedicationByPetName(String petName) {
        List<Medication> medicationsByPetName = new ArrayList<>();
        try {
        String joinerSql = "SELECT m.* FROM medication m " +
                "INNER JOIN pet_medication pm ON m.med_id = pm.med_id " +
                "INNER JOIN pet p ON pm.pet_id = p.pet_id " +
                "WHERE pet.name = ?";

        SqlRowSet results = jdbcTemplate.queryForRowSet(joinerSql, petName);
        while (results.next()) {
            Medication medication = mapRowToMedication(results);
            medicationsByPetName.add(medication);
        }
    }
        catch (CannotGetJdbcConnectionException e) {
        throw new DaoException("Unable to connect to server or database", e);
    }
        return medicationsByPetName;
    }


    private Medication mapRowToMedication(SqlRowSet rs) {
        Medication medication = new Medication();
        medication.setMedId(rs.getInt("med_id"));
        medication.setName(rs.getString("name"));
        return medication;
    }


}
