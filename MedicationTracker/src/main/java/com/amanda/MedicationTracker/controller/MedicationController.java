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

    @GetMapping("/{id}")
    public Medication getMedicationById(@PathVariable int id) {
        return medicationService.getMedicationById(id);
    }

    @PostMapping("/add")
    public Medication addMedication(@RequestBody Medication medication) {
        return medicationService.addMedication(medication);
    }

    @PutMapping("/{id}")
    public Medication updateMedication(@PathVariable int id, @RequestBody Medication updatedMedication) {
        updatedMedication.setMedId(id);
        return medicationService.updateMedication(updatedMedication);
    }


    @DeleteMapping("/{id}")
    public int deleteMedication(@PathVariable int id) {
        return medicationService.deleteMedication(id);
    }

    // Endpoint to get medications by day (frequency)
//    @GetMapping("/byDay")
//    public List<Medication> getMedicationsByDay(@RequestParam String day) {
//        return medicationService.getMedicationByDay(day);
//    }

}
