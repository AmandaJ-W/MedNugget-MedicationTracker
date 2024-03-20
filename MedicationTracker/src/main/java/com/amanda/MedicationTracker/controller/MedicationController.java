package com.amanda.MedicationTracker.controller;

import com.amanda.MedicationTracker.exception.DaoException;
import com.amanda.MedicationTracker.exception.ServiceException;
import com.amanda.MedicationTracker.model.Medication;
import com.amanda.MedicationTracker.service.MedicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medications")
public class MedicationController {
    private final MedicationService medicationService;

    @Autowired
    public MedicationController(MedicationService medicationService) {
        this.medicationService = medicationService;
    }

    @GetMapping
    public List<Medication> listAllMedications() {
        return medicationService.listAllMedications();
    }

    @GetMapping("/{medId}")
    public Medication getMedicationById(@PathVariable int medId) {
        return medicationService.getMedicationById(medId);
    }

    @PostMapping("/addMedication")
    public Medication addMedication(@RequestBody Medication medication) {
        return medicationService.addMedication(medication);
    }

    @PutMapping("/update/{medId}")
    public Medication updateMedication(@PathVariable int medId, @RequestBody Medication updatedMedication) {
        updatedMedication.setMedId(medId);
        return medicationService.updateMedication(updatedMedication);
    }


    @DeleteMapping("/delete/{medId}")
    public int deleteMedication(@PathVariable int medId) {
        return medicationService.deleteMedication(medId);
    }

    @GetMapping("/getmedicationbyname")
    public List<Medication> getMedicationByName(@RequestParam String name) {
        return medicationService.getMedicationByName(name);
    }

    @GetMapping("/getmedicationbypetname")
    public List<Medication> getMedicationByPetName(@RequestParam String petName) {
        return medicationService.getMedicationByPetName(petName);
    }

    @PutMapping("/givemedication/")
    public void markMedicationAsGiven(@RequestParam int medId, @RequestParam int petId) {
        medicationService.markMedicationAsGiven(medId, petId);
    }

}
