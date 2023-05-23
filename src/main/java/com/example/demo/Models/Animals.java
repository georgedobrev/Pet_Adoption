package com.example.demo.Models;

import com.example.demo.Models.Enums.AnimalSexEnum;
import com.example.demo.Models.Enums.SizeCategoryEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;


public class Animals {
    private int animalID;
    private int animalShelterID;
    private String animalPhoto;
    private String animalName;
@Enumerated(EnumType.STRING)
private AnimalSexEnum gender;
    private int animalAge;
  private int animalSizeCategoryID;
  private String animalCharacteristics;
@Enumerated(EnumType.STRING)
private SizeCategoryEnum size;
    public Animals() {
    }

    public Animals(int animalID, int animalShelterID, String animalPhoto, String animalName, AnimalSexEnum gender,
                   int animalAge,
                   int animalSizeCategoryID, String animalCharacteristics, SizeCategoryEnum size) {
        this.animalID = animalID;
        this.animalShelterID = animalShelterID;
        this.animalPhoto = animalPhoto;
        this.animalName = animalName;
        this.gender = gender;
        this.animalAge = animalAge;
        this.animalSizeCategoryID = animalSizeCategoryID;
        this.animalCharacteristics = animalCharacteristics;
        this.size = size;
    }

    public int getAnimalID() {
        return animalID;
    }

    public void setAnimalID(int animalID) {
        this.animalID = animalID;
    }

    public int getAnimalShelterID() {
        return animalShelterID;
    }

    public void setAnimalShelterID(int animalShelterID) {
        this.animalShelterID = animalShelterID;
    }

    public String getAnimalPhoto() {
        return animalPhoto;
    }

    public void setAnimalPhoto(String animalPhoto) {
        this.animalPhoto = animalPhoto;
    }

    public String getAnimalName() {
        return animalName;
    }

    public void setAnimalName(String animalName) {
        this.animalName = animalName;
    }

    public int getAnimalAge() {
        return animalAge;
    }

    public void setAnimalAge(int animalAge) {
        this.animalAge = animalAge;
    }

    public SizeCategoryEnum getSize() {
        return size;
    }

    public void setSize(SizeCategoryEnum size) {
        this.size = size;
    }

    public int getAnimalSizeCategoryID() {
        return animalSizeCategoryID;
    }

    public void setAnimalSizeCategoryID(int animalSizeCategoryID) {
        this.animalSizeCategoryID = animalSizeCategoryID;
    }

    public String getAnimalCharacteristics() {
        return animalCharacteristics;
    }

    public void setAnimalCharacteristics(String animalCharacteristics) {
        this.animalCharacteristics = animalCharacteristics;
    }

    public AnimalSexEnum getGender() {
        return gender;
    }

    public void setGender(AnimalSexEnum gender) {
        this.gender = gender;
    }

}
