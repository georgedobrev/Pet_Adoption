package com.example.service;

import com.example.persistence.binding.AnimalAddBindingModel;
import com.example.persistence.entities.AnimalsEntity;
import com.example.persistence.entities.SheltersEntity;
import com.example.persistence.entities.SizeCategoryEntity;

import java.util.List;

public interface AnimalService {

    String addAnimal(AnimalAddBindingModel animalAddBindingModel, SizeCategoryEntity sizeCategory, SheltersEntity shelters);

    String updateAnimal(long id, AnimalAddBindingModel animalViewModel);

    List<AnimalsEntity> getAllAnimals();
}
