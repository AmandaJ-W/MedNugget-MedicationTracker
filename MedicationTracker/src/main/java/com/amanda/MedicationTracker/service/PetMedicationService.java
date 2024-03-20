package com.amanda.MedicationTracker.service;

import com.amanda.MedicationTracker.dao.MedicationDao;
import com.amanda.MedicationTracker.dao.PetMedicationDao;
import com.amanda.MedicationTracker.exception.DaoException;
import com.amanda.MedicationTracker.exception.ServiceException;
import com.amanda.MedicationTracker.model.PetMedication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PetMedicationService {

    private final PetMedicationDao petMedicationDao;

    @Autowired
    public PetMedicationService(PetMedicationDao petMedicationDao) {
        this.petMedicationDao = petMedicationDao;
    }

    public void assignMedicationToPet(PetMedication petMedication) {
        try {
            petMedicationDao.assignMedicationToPet(
                    petMedication.getMedId(),
                    petMedication.getPetId(),
                    petMedication.getDose(),
                    petMedication.getFrequency(),
                    petMedication.getPurpose(),
                    petMedication.isWantReminder(),
                    petMedication.isGiven()
            );
        } catch (DaoException e) {
            throw new ServiceException("Error assigning medication to pet.", e);
        }
    }

}
