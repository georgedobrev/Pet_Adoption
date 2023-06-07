package com.example.service.impl;

import com.example.persistence.binding.AnimalAddBindingModel;
import com.example.persistence.entities.AnimalsEntity;
import com.example.persistence.entities.SheltersEntity;
import com.example.persistence.repositories.AnimalRepository;
import com.example.persistence.repositories.ShelterRepository;
import com.example.mapper.AnimalMapper;
import com.example.service.AnimalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@RequiredArgsConstructor
@Service

public class AnimalServiceImpl implements AnimalService {
    private final AnimalRepository animalRepository;
    private final ShelterRepository shelterRepository;
    private final AnimalMapper animalMapper;


    @Override
    public String addAnimal(AnimalAddBindingModel animalViewModel) {
        AnimalsEntity animal = animalMapper.toEntity(animalViewModel);
        SheltersEntity shelter = shelterRepository.findById(animalViewModel.getShelterID()).orElseThrow(
                () -> new RuntimeException("No shelter found with id " + animalViewModel.getShelterID()));
        animal.setShelter(shelter);
        animalRepository.save(animal);
        return "redirect:/";
    }

    @Override
    public String updateAnimal(long id, AnimalAddBindingModel animalViewModel) {
        AnimalsEntity existingAnimal = animalRepository.findById(id).orElseThrow(
                () -> new RuntimeException("No animal found with id " + id));
        AnimalsEntity updatedAnimal = animalMapper.toEntity(animalViewModel);
        updatedAnimal.setAnimalID(existingAnimal.getAnimalID());
        updatedAnimal.setShelter(existingAnimal.getShelter());
        animalRepository.save(updatedAnimal);
        return "redirect:/animals";
    }

    @Override
    public List<AnimalsEntity> getAllAnimals() {
        return animalRepository.findAll();
    }
}


