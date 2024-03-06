package com.amanda.MedicationTracker.controller;

import com.amanda.MedicationTracker.model.Medication;
import com.amanda.MedicationTracker.model.Pet;
import com.amanda.MedicationTracker.service.MedicationService;
import com.amanda.MedicationTracker.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pets")
public class PetController {
    private final PetService petService;

    @Autowired
    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping
    public List<Pet> getAllPets() {
        return petService.listAllPets();
    }

    @GetMapping("/{petId}")
    public Pet getPetById(@PathVariable int petId) {
        return petService.getPetById(petId);
    }

    @PostMapping("/addPet")
    public Pet addPet(@RequestBody Pet pet) {
        return petService.addPet(pet);
    }

    @PutMapping("/{petId}")
    public Pet updatePet(@PathVariable int petId, @RequestBody Pet updatedPet) {
        updatedPet.setPetId(petId);
        return petService.updatePet(updatedPet);
    }

    @DeleteMapping("/{petId}")
    public int deletePet(@PathVariable int petId) {
        return petService.deletePet(petId);
    }

    // MISSING SERVICE METHODS
}
