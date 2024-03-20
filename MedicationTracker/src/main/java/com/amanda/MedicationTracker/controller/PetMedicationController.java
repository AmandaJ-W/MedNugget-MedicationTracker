package com.amanda.MedicationTracker.controller;

import com.amanda.MedicationTracker.exception.DaoException;
import com.amanda.MedicationTracker.exception.ServiceException;
import com.amanda.MedicationTracker.model.PetMedication;
import com.amanda.MedicationTracker.service.MedicationService;
import com.amanda.MedicationTracker.service.PetMedicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/petmedications")
public class PetMedicationController {

    private final PetMedicationService petMedicationService;

    @Autowired
    public PetMedicationController(PetMedicationService petMedicationService) {
        this.petMedicationService = petMedicationService;
    }


    @PostMapping("/assignMedicationToPet")
    public void assignMedicationToPet(@RequestBody PetMedication petMedication) {
        try {
            petMedicationService.assignMedicationToPet(petMedication);
        } catch (DaoException e) {
            throw new ServiceException("Error assigning medication to pet", e);
        }
    }
}
