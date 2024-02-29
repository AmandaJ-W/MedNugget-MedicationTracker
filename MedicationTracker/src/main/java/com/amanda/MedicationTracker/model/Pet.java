package com.amanda.MedicationTracker.model;

public class Pet {

    private int petId;

    public Pet() {
    }

    private String name; // pet's name
    private String typeOfAnimal; // dog, cat, etc.

    public Pet(int petId, String name, String typeOfAnimal) {
        this.petId = petId;
        this.name = name;
        this.typeOfAnimal = typeOfAnimal;
    }

    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeOfAnimal() {
        return typeOfAnimal;
    }

    public void setTypeOfAnimal(String typeOfAnimal) {
        this.typeOfAnimal = typeOfAnimal;
    }
}
