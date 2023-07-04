package com.example.service;

import com.example.persistence.binding.AnimalAddBindingModel;
import com.example.persistence.binding.UpdateAnimalBindingModel;
import com.example.persistence.entities.AnimalsEntity;
import com.example.persistence.view.AnimalViewModel;

import java.util.List;

public interface AnimalService {

    void addAnimal(AnimalAddBindingModel animalAddBindingModel);

    AnimalsEntity updateAnimal(long id, UpdateAnimalBindingModel updateAnimalBindingModel);

    AnimalViewModel getAnimalById(long id);

    List<AnimalsEntity> getAllAnimals();


    List<AnimalsEntity> getAnimalsByShelterId(long id);

}
