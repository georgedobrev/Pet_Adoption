package com.example.service;

import com.example.persistence.binding.AnimalAddBindingModel;
import com.example.persistence.binding.UpdateAnimalBindingModel;
import com.example.persistence.entities.AnimalsEntity;

import java.util.List;

public interface AnimalService {

     void addAnimal(AnimalAddBindingModel animalAddBindingModel);

    AnimalsEntity updateAnimal(long id, UpdateAnimalBindingModel updateAnimalBindingModel);

    List<AnimalsEntity> getAllAnimals();


    // void updateAnimal(long id , UpdateAnimalBindingModel updateAnimalBindingModel);
}
