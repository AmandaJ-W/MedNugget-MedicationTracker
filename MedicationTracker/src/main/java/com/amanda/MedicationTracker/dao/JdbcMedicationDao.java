package com.amanda.MedicationTracker.dao;

import com.amanda.MedicationTracker.exception.DaoException;
import com.amanda.MedicationTracker.exception.NotFoundException;
import com.amanda.MedicationTracker.model.Medication;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.time.LocalTime;
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

    @Override
    public Medication addMedication(Medication newMedication) {
        String insertMedicationSql = "INSERT INTO medication (name) values (?) RETURNING med_id";
        try {
            int generatedMedId = jdbcTemplate.queryForObject(insertMedicationSql,
                    int.class,
                    newMedication.getName());
                    newMedication.setMedId(generatedMedId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return newMedication;
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

//    @Override
//    public Medication markDoseAsGiven(int id, LocalTime time) {
//        Medication medicationToUpdate = getMedicationById(id);
//        if (medicationToUpdate == null) {
//            throw new NotFoundException("Error. Cannot find medication.");
//        }
//
//        medicationToUpdate.setGiven(true);
//        medicationToUpdate.setGivenTime(time);
//
//        String sql = "UPDATE medication SET given=? WHERE med_id = ?;";
//        try {
//            int rowsAffected = jdbcTemplate.update(sql, medicationToUpdate.isGiven(), medicationToUpdate.getGivenTime(), medicationToUpdate.getMedId());
//            if (rowsAffected == 0) {
//                throw new DaoException("Failed to mark dose as given.");
//            }
//        } catch (CannotGetJdbcConnectionException e) {
//            throw new DaoException("Unable to connect to server or database", e);
//        } catch (DataIntegrityViolationException e) {
//            throw new DaoException("Data integrity violation", e);
//        }
//        return medicationToUpdate;
//    }

    private Medication mapRowToMedication(SqlRowSet rs) {
        Medication medication = new Medication();
        medication.setMedId(rs.getInt("med_id"));
        medication.setName(rs.getString("name"));
        return medication;
    }

//    This is for converting to LocalTime datatype
//    private LocalTime getLocalTime(SqlRowSet rs, String columnName) {
//        Time time = rs.getTime(columnName);
//        return time != null ? time.toLocalTime() : null;
//    }
}
