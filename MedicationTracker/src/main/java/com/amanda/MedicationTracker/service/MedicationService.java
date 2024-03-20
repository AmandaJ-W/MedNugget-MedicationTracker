package com.amanda.MedicationTracker.service;

import com.amanda.MedicationTracker.dao.MedicationDao;
import com.amanda.MedicationTracker.exception.DaoException;
import com.amanda.MedicationTracker.exception.ServiceException;
import com.amanda.MedicationTracker.model.Medication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicationService {

    private final MedicationDao medicationDao;

    @Autowired
    public MedicationService(MedicationDao medicationDao) {
        this.medicationDao = medicationDao;
    }

    public List<Medication> listAllMedications() {
        try {
            return medicationDao.listAllMedications();
        } catch (DaoException e) {
            throw new ServiceException("Error occurred while listing all medications", e);
        }
    }

    public Medication getMedicationById(int id) {
        try {
            return medicationDao.getMedicationById(id);
        } catch (DaoException e) {
            throw new ServiceException("Error occurred. Cannot find medication.", e);
        }
    }

    public List<Medication> getMedicationByName(String name) {
        try {
            return medicationDao.getMedicationByName(name);
        } catch (DaoException e) {
            throw new ServiceException("Error occurred. Cannot find medication.", e);
        }
    }

    public Medication addMedication(Medication medication) {
        try {
            return medicationDao.addMedication(medication);
        } catch (DaoException e) {
            throw new ServiceException("Error occurred. Cannot add medication.", e);
        }
    }

    public int deleteMedication(int medId) {
        try {
            return medicationDao.deleteMedication(medId);
        }catch (DaoException e) {
            throw new ServiceException("Error. Cannot delete medication.", e);
        }
    }

    public Medication updateMedication(Medication medicationToUpdate) {
        try {
            return medicationDao.updateMedication(medicationToUpdate);
        } catch (DaoException e) {
            throw new ServiceException("Error. Cannot update medication.", e);
        }
    }


    public List<Medication> getMedicationByPetName(String petName) {
        try {
            return medicationDao.getMedicationByPetName(petName);
        } catch (DaoException e) {
            throw new ServiceException("Error. Cannot find medication.", e);
        }
    }


    public void markMedicationAsGiven(int medId, int petId) {
        try {
            medicationDao.markMedicationAsGiven(medId, petId);
        } catch (DaoException e) {
            throw new ServiceException("Error. Couldn't mark medication dose as given.", e);
        }
    }

}
