package com.example.service;

import com.example.persistence.binding.AnimalAddBindingModel;
import com.example.persistence.entities.SheltersEntity;
import com.example.persistence.view.AddAnimalViewModel;

import java.util.List;

public interface ServiceAnimal {
    List<SheltersEntity> getAllShelters();
    AddAnimalViewModel getAddAnimalViewModel();
    String addAnimal(AnimalAddBindingModel animalViewModel);
    AddAnimalViewModel getAnimalForEdit(long id);
    String updateAnimal(long id, AnimalAddBindingModel animalViewModel);
}