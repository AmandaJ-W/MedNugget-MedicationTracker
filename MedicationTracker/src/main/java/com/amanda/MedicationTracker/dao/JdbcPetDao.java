package com.amanda.MedicationTracker.dao;

import com.amanda.MedicationTracker.exception.DaoException;
import com.amanda.MedicationTracker.model.Medication;
import com.amanda.MedicationTracker.model.Pet;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.ArrayList;
import java.util.List;

public class JdbcPetDao implements PetDao {
    private final JdbcTemplate jdbcTemplate;

    public JdbcPetDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Pet> listAllPets() {
        List<Pet> petList = new ArrayList<>();
        String sql = "SELECT * FROM pet;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                Pet pet = mapRowToPet(results);
                petList.add(pet);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return petList;
    }


    @Override
    public Pet getPetById(int id) {
        Pet pet = null;
        String sql = "SELECT * FROM pet WHERE pet_id = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            if (results.next()) {
                pet = mapRowToPet(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return pet;
    }

    @Override
    public Pet getPetByName(String name) {
        Pet pet = null;
        String sql = "SELECT * FROM pet WHERE name = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            if (results.next()) {
                pet = mapRowToPet(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return pet;
    }

    @Override
    public Pet addPet(Pet newPet) {
        String sql = "INSERT INTO pet (name, type_of_animal) value (?, ?) RETURNING pet_id;";
        try {
            int generatedPetId = jdbcTemplate.queryForObject(sql, int.class, newPet.getName(), newPet.getTypeOfAnimal(),
                    newPet.getPetId());
            newPet.setPetId(generatedPetId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return newPet;
    }

    @Override
    public Pet updatePet(Pet petToUpdate) {
        String sql = "UPDATE pet SET name=?, type_of_animal=? WHERE pet_id=?;";
        try {
            int rowsAffected = jdbcTemplate.update(sql, petToUpdate.getName(), petToUpdate.getTypeOfAnimal(),
                    petToUpdate.getPetId());
            if (rowsAffected == 0) {
                throw new DaoException("This pet does not exist in database. Unable to update.");
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return petToUpdate;
    }


    @Override
    public int deletePet(int petId) {
        int affectedRows = 0;
        String removeFromJoinerSql = "DELETE FROM pet_medication WHERE pet_medication.pet_id=?;";
        String sql = "DELETE FROM pet WHERE pet.pet_id=?;";
        try {
            affectedRows += jdbcTemplate.update(removeFromJoinerSql, petId);
            affectedRows += jdbcTemplate.update(sql, petId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return affectedRows;
    }

    private Pet mapRowToPet(SqlRowSet rs) {
        Pet pet = new Pet();
        pet.setPetId(rs.getInt("pet_id"));
        pet.setName(rs.getString("name"));
        pet.setTypeOfAnimal(rs.getString("type_of_animal"));
        return pet;
    }
}
