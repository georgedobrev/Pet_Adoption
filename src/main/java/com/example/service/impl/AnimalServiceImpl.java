package com.example.service.impl;

import com.example.persistence.binding.AnimalAddBindingModel;
import com.example.persistence.binding.UpdateAnimalBindingModel;
import com.example.persistence.entities.AnimalsEntity;
import com.example.persistence.entities.SheltersEntity;
import com.example.persistence.entities.SizeCategoryEntity;
import com.example.persistence.repositories.AnimalRepository;
import com.example.persistence.repositories.ShelterRepository;
import com.example.mapper.AnimalMapper;
import com.example.persistence.repositories.SizeCategoryRepository;
import com.example.persistence.view.AnimalViewModel;
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
    private final SizeCategoryRepository sizeCategoryRepository;


    @Override
    public void addAnimal(AnimalAddBindingModel animalAddBindingModel) {
        SizeCategoryEntity size = sizeCategoryRepository.findByCategory(animalAddBindingModel.getAnimalSize());
        SheltersEntity shelter = shelterRepository.findByShelterName(animalAddBindingModel.getShelterName()).orElseThrow(
                () -> new RuntimeException("No shelter found with this name: " + animalAddBindingModel.getShelterName()));
        AnimalsEntity animal = animalMapper.toAnimalEntity(animalAddBindingModel, size, shelter);
        animalRepository.save(animal);

    }

    @Override
    public AnimalsEntity updateAnimal(long id, UpdateAnimalBindingModel updateAnimalBindingModel) {
        AnimalsEntity existingAnimal = animalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No animal found with id " + id));
        SizeCategoryEntity sizeCategory = sizeCategoryRepository.findByCategory(updateAnimalBindingModel.getSizeCategory());
        AnimalsEntity updatedAnimal = animalMapper.updateAnimalEntity(updateAnimalBindingModel, existingAnimal, sizeCategory);
        return animalRepository.save(updatedAnimal);

    }

    @Override
    public AnimalViewModel getAnimalById(long id) {
        AnimalsEntity existingAnimal = animalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No animal found with id " + id));
        AnimalViewModel animalViewModel = animalMapper.toAnimalViewModel(existingAnimal);

        return animalViewModel;
    }

    @Override
    public List<AnimalsEntity> getAllAnimals() {
        return animalRepository.findAll();
    }

    @Override
    public List<AnimalsEntity> getAnimalsByShelterId(long id) {
        SheltersEntity shelter = shelterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No shelter found with id " + id));
        return animalRepository.findByShelter_ShelterID(shelter.getShelterID());

    }
}


