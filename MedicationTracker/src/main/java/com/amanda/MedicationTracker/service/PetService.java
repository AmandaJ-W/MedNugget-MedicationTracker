package com.amanda.MedicationTracker.service;


import com.amanda.MedicationTracker.dao.PetDao;
import com.amanda.MedicationTracker.exception.DaoException;
import com.amanda.MedicationTracker.exception.ServiceException;
import com.amanda.MedicationTracker.model.Pet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {
    private final PetDao petDao;

    @Autowired
    public PetService(PetDao petDao) {
        this.petDao = petDao;
    }

    public List<Pet> listAllPets() {
        try {
            return petDao.listAllPets();
        } catch (DaoException e) {
            throw new ServiceException("Error occurred while listing all pets", e);
        }
    }

    public Pet getPetById(int id) {
        try {
            return petDao.getPetById(id);
        } catch (DaoException e) {
            throw new ServiceException("Error occurred while listing all pets", e);
        }
    }

    public Pet getPetByName(String name) {
        try {
            return petDao.getPetByName(name);
        } catch (DaoException e) {
            throw new ServiceException("Error. Cannot find pet.", e);
        }
    }

    public Pet addPet(Pet newPet) {
        try {
            return petDao.addPet(newPet);
        } catch (DaoException e) {
            throw new ServiceException("Error. Cannot find pet.", e);
        }
    }

    public Pet updatePet(Pet petToUpdate) {
        try {
        return petDao.updatePet(petToUpdate);
        } catch (DaoException e) {
            throw new ServiceException("Error. Cannot update pet.", e);
        }
    }

    public int deletePet(int petId) {
        try {
            return petDao.deletePet(petId);
        }catch (DaoException e) {
            throw new ServiceException("Error. Cannot delete pet.", e);
        }
    }

}
