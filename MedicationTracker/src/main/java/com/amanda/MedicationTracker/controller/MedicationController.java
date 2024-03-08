package com.amanda.MedicationTracker.controller;

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

    @PutMapping("/{medId}")
    public Medication updateMedication(@PathVariable int medId, @RequestBody Medication updatedMedication) {
        updatedMedication.setMedId(medId);
        return medicationService.updateMedication(updatedMedication);
    }


    @DeleteMapping("/{medId}")
    public int deleteMedication(@PathVariable int medId) {
        return medicationService.deleteMedication(medId);
    }

    // MISSING SERVICE METHODS

    // Endpoint to get medications by day (frequency)
//    @GetMapping("/byDay")
//    public List<Medication> getMedicationsByDay(@RequestParam String day) {
//        return medicationService.getMedicationByDay(day);
//    }

    // public List<Medication> getMedicationByPetName(String petName)


}
