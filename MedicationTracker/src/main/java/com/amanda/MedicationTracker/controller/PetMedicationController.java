package com.amanda.MedicationTracker.controller;

import com.amanda.MedicationTracker.exception.DaoException;
import com.amanda.MedicationTracker.exception.ServiceException;
import com.amanda.MedicationTracker.service.MedicationService;
import com.amanda.MedicationTracker.service.PetMedicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/petmedications")
public class PetMedicationController {

    private final PetMedicationService petMedicationService;

    @Autowired
    public PetMedicationController(PetMedicationService petMedicationService) {
        this.petMedicationService = petMedicationService;
    }

    @PostMapping("/assignMedicationToPet")
    public void assignMedicationToPet(@RequestParam int medId, @RequestParam int petId) {
        try {
            petMedicationService.assignMedicationToPet(medId, petId);
        } catch (DaoException e) {
            throw new ServiceException("Error assigning medication to pet", e);
        }
    }
}
