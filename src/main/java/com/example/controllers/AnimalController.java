package com.example.controllers;

import com.example.persistence.binding.AnimalAddBindingModel;
import com.example.persistence.entities.AnimalsEntity;
import com.example.persistence.entities.SheltersEntity;
import com.example.persistence.enums.AnimalSexEnum;
import com.example.persistence.enums.AnimalSpeciesEnum;
import com.example.persistence.enums.SizeCategoryEnum;
import com.example.persistence.view.AddAnimalViewModel;
import com.example.service.AnimalService;
import com.example.service.ShelterService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/animals")
public class AnimalController {
    private final AnimalService animalService;
    private final ShelterService shelterService;

    public AnimalController(AnimalService animalService, ShelterService shelterService) {
        this.animalService = animalService;
        this.shelterService = shelterService;
    }

    @GetMapping("/add")
    public String showAddAnimalForm(Model model) {
        List<SheltersEntity> shelters = shelterService.getAllShelters();
        model.addAttribute("shelters", shelters);
        model.addAttribute("animal", new AddAnimalViewModel());
        return "animal-add";
    }

    @PostMapping("/add")
    public String addAnimal(@ModelAttribute("animal") AnimalAddBindingModel animalViewModel) {
     AnimalsEntity animal = new AnimalsEntity();
     animal.setAnimalName(animalViewModel.getAnimalName());
     animal.setAnimalSpecies(AnimalSpeciesEnum.valueOf(String.valueOf(animalViewModel.getAnimalSpecies())));
     animal.setGender(AnimalSexEnum.valueOf(String.valueOf(animalViewModel.getAnimalGender())));
     animal.setAnimalAge(animalViewModel.getAnimalAge());
        animal.setSizeCategory(SizeCategoryEnum.valueOf(String.valueOf(animalViewModel.getAnimalSize())));
        animal.setAnimalCharacteristics(animalViewModel.getAnimalCharacteristics());
       animalService.createAnimal(animal);
        return "redirect:/animals";
    }

    @GetMapping("/{id}/edit")
    public String showEditAnimalForm(@PathVariable("id") long id, Model model) {
        AnimalsEntity animalEntity = animalService.getAnimalById(id);
        List<SheltersEntity> shelters = shelterService.getAllShelters();

      AddAnimalViewModel animal = new AddAnimalViewModel();
      animal.setAnimalName(animalEntity.getAnimalName());
      animal.setAnimalSpecies(AnimalSpeciesEnum.valueOf(String.valueOf(animalEntity.getAnimalSpecies())));
      animal.setAnimalGender(AnimalSexEnum.valueOf(String.valueOf(animalEntity.getGender())));
      animal.setAnimalAge(animalEntity.getAnimalAge());
      animal.setAnimalSize(SizeCategoryEnum.valueOf(String.valueOf(animalEntity.getSizeCategory())));
      animal.setAnimalCharacteristics(animalEntity.getAnimalCharacteristics());
      model.addAttribute("animal", animal);
       model.addAttribute("shelters", shelters);
        return "animal-edit";
    }


    @PostMapping("/{id}/edit")
    public String updateAnimal(@PathVariable("id") long id, @ModelAttribute("animal") AnimalAddBindingModel animalViewModel) {
        AnimalsEntity animal = animalService.getAnimalById(id);
       animal.setAnimalName(animalViewModel.getAnimalName());
       animal.setAnimalSpecies(AnimalSpeciesEnum.valueOf(String.valueOf(animalViewModel.getAnimalSpecies())));
       animal.setGender(AnimalSexEnum.valueOf(String.valueOf(animalViewModel.getAnimalGender())));
       animal.setAnimalAge(animalViewModel.getAnimalAge());
       animal.setSizeCategory(SizeCategoryEnum.valueOf(String.valueOf(animalViewModel.getAnimalSize())));
       animal.setAnimalCharacteristics(animalViewModel.getAnimalCharacteristics());


        animalService.updateAnimal(animal);
        return "redirect:/animals";
    }

}


