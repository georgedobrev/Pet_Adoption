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
        AnimalsEntity animal = animalMapper.toEntity(animalAddBindingModel, size, shelter);
        animalRepository.save(animal);

    }

    @Override
    public void updateAnimal(long id, UpdateAnimalBindingModel updateAnimalBindingModel) {
        AnimalsEntity existingAnimal = animalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No animal found with id " + id));
        SizeCategoryEntity sizeCategory = sizeCategoryRepository.findByCategory(updateAnimalBindingModel.getAnimalSize());

        AnimalsEntity updatedAnimal = animalMapper.updateEntity(updateAnimalBindingModel, existingAnimal, sizeCategory);
        animalRepository.save(updatedAnimal);
    }

  @Override
  public List<AnimalsEntity> getAllAnimals() {
      return animalRepository.findAll();
  }
}


