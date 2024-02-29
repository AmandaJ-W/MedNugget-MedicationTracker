package com.amanda.MedicationTracker.dao;

import com.amanda.MedicationTracker.exception.DaoException;
import com.amanda.MedicationTracker.model.Medication;
import com.amanda.MedicationTracker.model.Pet;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.ArrayList;
import java.util.List;

public class JdbcPetDao implements PetDao{
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
        }
        catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return petList;
    }


    @Override
    public Pet getPetById(int id) {
        return null;
    }

    @Override
    public Pet getPetByName(String name) {
        return null;
    }

    @Override
    public void createPet(Pet pet) {

    }

    @Override
    public void updatePet(Pet pet) {

    }

    @Override
    public void removePet(int id) {

    }

    private Pet mapRowToPet(SqlRowSet rs) {
        Pet pet = new Pet();
        pet.setPetId(rs.getInt("pet_id"));
        pet.setName(rs.getString("name"));
        pet.setTypeOfAnimal(rs.getString("type_of_animal"));
        return pet;
    }
}
