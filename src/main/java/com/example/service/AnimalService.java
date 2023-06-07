package com.example.service;

import com.example.persistence.binding.AnimalAddBindingModel;
import com.example.persistence.entities.AnimalsEntity;

import java.util.List;

public interface AnimalService {
    String addAnimal(AnimalAddBindingModel animalViewModel);

    String updateAnimal(long id, AnimalAddBindingModel animalViewModel);

    List<AnimalsEntity> getAllAnimals();
}
