package com.amanda.MedicationTracker.dao;

import com.amanda.MedicationTracker.exception.DaoException;
import com.amanda.MedicationTracker.model.Medication;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

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
        String insertMedicationSql = "INSERT INTO medication (name, dose, frequency, time_of_dose, purpose, want_reminder) values (?,?,?,?,?,?) RETURNING user_id";
        try {
            int generatedMedId = jdbcTemplate.queryForObject(insertMedicationSql,
                    int.class,
                    newMedication.getName(),
                    newMedication.getDose(),
                    newMedication.getFrequency(),
                    newMedication.getTime(),
                    newMedication.getPurpose(),
                    newMedication.isWantReminder());
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
        String sql = "UPDATE medication SET name=?, dose=?, frequency=?, time=?, purpose=?, wantReminder=? WHERE med_id = ?;";
        try {

            Time time = updatedMedication.getTime() != null ? Time.valueOf(updatedMedication.getTime()) : null;
            int rowsAffected = jdbcTemplate.update(sql, updatedMedication.getName(), updatedMedication.getDose(), updatedMedication.getFrequency(),
                    time, updatedMedication.getPurpose(), updatedMedication.isWantReminder(), updatedMedication.getMedId());

            if (rowsAffected == 0) {
                throw new DaoException("This medication does not exist in database. Unable to update.");
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

    private Medication mapRowToMedication(SqlRowSet rs) {
        Medication medication = new Medication();
        medication.setMedId(rs.getInt("med_id"));
        medication.setName(rs.getString("name"));
        medication.setDose(rs.getString("dose"));
        medication.setFrequency(rs.getString("frequency"));
        medication.setTime(getLocalTime(rs, "time"));
        medication.setPurpose(rs.getString("purpose"));
        medication.setWantReminder(rs.getBoolean("want_reminder"));
        return medication;
    }

    // This is for converting to LocalTime datatype
    private LocalTime getLocalTime(SqlRowSet rs, String columnName) {
        Time time = rs.getTime(columnName);
        return time != null ? time.toLocalTime() : null;
    }
}
