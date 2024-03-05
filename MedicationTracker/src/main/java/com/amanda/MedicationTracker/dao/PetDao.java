package com.amanda.MedicationTracker.dao;

import com.amanda.MedicationTracker.model.Pet;

import java.util.List;

public interface PetDao {
    // List all pets
    List<Pet> listAllPets();

    // Get a pet by id
    Pet getPetById(int id);

    // Get a pet by name
    Pet getPetByName(String name);

    // Add a new pet
    Pet addPet(Pet newPet);

    // Update a pet
    Pet updatePet(Pet petToUpdate);

    // Remove a pet
    int deletePet(int id);

    public List<Pet> findPetsByMedicationName(String medName);
}
