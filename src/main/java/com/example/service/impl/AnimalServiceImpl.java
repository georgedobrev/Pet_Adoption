package com.example.service.impl;

import com.example.persistence.entities.AnimalsEntity;
import com.example.persistence.repositories.AnimalRepository;
import com.example.service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimalServiceImpl implements AnimalService {

    private AnimalRepository animalRepository;

    @Autowired
    public AnimalServiceImpl(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    @Override
    public void createAnimal(AnimalsEntity animal) {
        animalRepository.save(animal);
    }

    @Override
    public AnimalsEntity getAnimalById(long animalId) {
        return animalRepository.findById(animalId).orElse(null);
    }

    @Override
    public List<AnimalsEntity> getAllAnimals() {
        return animalRepository.findAll();
    }

    @Override
    public List<AnimalsEntity> getAnimalsByShelterId(long shelterId) {
        return animalRepository.findByShelter_ShelterID(shelterId);
    }

    @Override
    public void updateAnimal(AnimalsEntity animal) {
        animalRepository.save(animal);
    }

    @Override
    public void deleteAnimal(AnimalsEntity animal) {
        animalRepository.delete(animal);
    }
}