package com.amanda.MedicationTracker.service;

import com.amanda.MedicationTracker.dao.MedicationDao;
import com.amanda.MedicationTracker.dao.PetMedicationDao;
import com.amanda.MedicationTracker.exception.DaoException;
import com.amanda.MedicationTracker.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PetMedicationService {

    private final PetMedicationDao petMedicationDao;

    @Autowired
    public PetMedicationService(PetMedicationDao petMedicationDao) {
        this.petMedicationDao = petMedicationDao;
    }

    public void assignMedicationToPet(int medId, int petId) {
        try {
            petMedicationDao.assignMedicationToPet(medId, petId);
        } catch (DaoException e) {
            throw new ServiceException("Error. Something has gone wrong.", e);
        }
    }
}
