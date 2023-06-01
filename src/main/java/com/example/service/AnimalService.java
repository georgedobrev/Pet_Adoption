package com.example.service;

import com.example.persistence.entities.AnimalsEntity;
import java.util.List;

public interface AnimalService {
    void createAnimal(AnimalsEntity animal);
    AnimalsEntity getAnimalById(long animalId);
    List<AnimalsEntity> getAllAnimals();
    List<AnimalsEntity> getAnimalsByShelterId(long shelterId);
    void updateAnimal(AnimalsEntity animal);
    void deleteAnimal(AnimalsEntity animal);
}