package com.example.service.impl;

import com.example.persistence.binding.AnimalAddBindingModel;
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
    public  String addAnimal() {

        SizeCategoryEntity size = sizeCategoryRepository.findByCategory(animalAddBindingModel.getAnimalSize());
        SheltersEntity shelter = shelterRepository.findByShelterName(animalAddBindingModel.getShelterName()).orElseThrow(
                () -> new RuntimeException("No shelter found with this name: " + animalAddBindingModel.getShelterName()));
        AnimalsEntity animal = animalMapper.toEntity(animalAddBindingModel , sizeCategory ,shelters);
        animal.setSizeCategory(size);
        animal.setShelter(shelter);
        animalRepository.save(animal);
        return animalRepository;
    }

  @Override
  public String updateAnimal(long id, AnimalAddBindingModel animalAddBindingModel) {
      AnimalsEntity existingAnimal = animalRepository.findById(id).orElseThrow(
              () -> new RuntimeException("No animal found with id " + id));
   //   AnimalsEntity updatedAnimal = animalMapper.toEntity(animalAddBindingModel);
   //   updatedAnimal.setAnimalID(existingAnimal.getAnimalID());
    //  updatedAnimal.setShelter(existingAnimal.getShelter());
    //  animalRepository.save(updatedAnimal);
      return "redirect:/animals";
  }

  @Override
  public List<AnimalsEntity> getAllAnimals() {
      return animalRepository.findAll();
    }
}


