package com.example.service.impl;

import com.example.persistence.binding.AnimalAddBindingModel;
import com.example.persistence.entities.AnimalsEntity;
import com.example.persistence.entities.SheltersEntity;
import com.example.persistence.view.AddAnimalViewModel;
import com.example.repositories.AnimalRepository;
import com.example.repositories.ShelterRepository;
import com.example.repositories.SizeCategoryRepository;
import com.example.service.ServiceAnimal;
import com.example.service.ShelterService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimalServiceImpl implements ServiceAnimal {
    private final AnimalRepository animalRepository;
    private final ShelterService shelterService;
    private final SizeCategoryRepository sizeCategoryRepository;
    private final ShelterRepository shelterRepository;



    public AnimalServiceImpl(AnimalRepository animalRepository,  SizeCategoryRepository sizeCategoryRepository , ShelterRepository shelterRepository,ShelterService shelterService) {
        this.animalRepository = animalRepository;
        this.shelterService = shelterService;
        this.sizeCategoryRepository = sizeCategoryRepository;
        this.shelterRepository = shelterRepository;
    }

    @Override
    public List<SheltersEntity> getAllShelters() {
        return shelterService.getAllShelters();
    }

    @Override
    public AddAnimalViewModel getAddAnimalViewModel() {
        return new AddAnimalViewModel();
    }

    @Override
    public String addAnimal(AnimalAddBindingModel animalViewModel) {
        AnimalsEntity animal = new AnimalsEntity();
        animal.setAnimalName(animalViewModel.getAnimalName());
        animal.setAnimalSpecies(animalViewModel.getAnimalSpecies());
        animal.setGender(animalViewModel.getAnimalGender());
        animal.setAnimalAge(animalViewModel.getAnimalAge());
        animal.setSizeCategory(sizeCategoryRepository.findByCategory(animalViewModel.getAnimalSize()));
        animal.setAnimalCharacteristics(animalViewModel.getAnimalCharacteristics());
       SheltersEntity shelter = shelterRepository.findById(Long.valueOf(animalViewModel.getShelterID())).orElseThrow(
               () -> new RuntimeException("No shelter found with id " + animalViewModel.getShelterID()));
       animal.setShelter(shelter);
        animalRepository.save(animal);
        return "redirect:/";
    }

    @Override
    public AddAnimalViewModel getAnimalForEdit(long id) {
        AnimalsEntity animalEntity = animalRepository.findById(id).orElseThrow();
        AddAnimalViewModel animal = new AddAnimalViewModel();
        animal.setAnimalName(animalEntity.getAnimalName());
        animal.setAnimalSpecies(animalEntity.getAnimalSpecies());
        animal.setAnimalGender(animalEntity.getGender());
        animal.setAnimalAge(animalEntity.getAnimalAge());
        animal.setAnimalSize(animalEntity.getSizeCategory().getCategory());
        animal.setAnimalCharacteristics(animalEntity.getAnimalCharacteristics());
        return animal;
    }

    @Override
    public String updateAnimal(long id, AnimalAddBindingModel animalViewModel) {
        AnimalsEntity animal = animalRepository.findById(id).orElseThrow();
        animal.setAnimalName(animalViewModel.getAnimalName());
        animal.setAnimalSpecies(animalViewModel.getAnimalSpecies());
        animal.setGender(animalViewModel.getAnimalGender());
        animal.setAnimalAge(animalViewModel.getAnimalAge());
        animal.setSizeCategory(sizeCategoryRepository.findByCategory(animalViewModel.getAnimalSize()));
        animal.setAnimalCharacteristics(animalViewModel.getAnimalCharacteristics());

        animalRepository.save(animal);
        return "redirect:/animals";
    }

    @Override
    public List<AnimalsEntity> getAllAnimals() {
        return animalRepository.findAll();
    }
}
