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

    // Create a new pet
    void createPet(Pet pet);

    // Update a pet
    void updatePet(Pet pet);

    // Remove a pet
    void removePet(int id);

}
