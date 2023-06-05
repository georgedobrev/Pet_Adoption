package com.example.controllers;

import com.example.persistence.entities.AnimalsEntity;
import com.example.persistence.entities.SheltersEntity;
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
    //    List<SheltersEntity> shelters = shelterService.getAllShelters();
        //     model.addAttribute("shelters", shelters);
        model.addAttribute("animal", new AddAnimalViewModel());
        return "animal-add";
    }

    @PostMapping("/add")
    public String addAnimal(@ModelAttribute("animal") AddAnimalViewModel animalViewModel) {
        AnimalsEntity animal = new AnimalsEntity();
//        animal.setAnimalName(animalViewModel.getAnimalName());
//        animal.setAnimalSpecies(animalViewModel.getAnimalSpecies());
//        animal.setGender(animalViewModel.getAnimalGender());
//        animal.setAnimalAge(animalViewModel.getAnimalAge());
//        animal.setSizeCategory(animalViewModel.getAnimalSize());
//        animal.setAnimalCharacteristics(animalViewModel.getAnimalCharacteristics());


        animalService.createAnimal(animal);
        return "redirect:/animals";
    }

    @GetMapping("/{id}/edit")
    public String showEditAnimalForm(@PathVariable("id") long id, Model model) {
        AnimalsEntity animalEntity = animalService.getAnimalById(id);
     //   List<SheltersEntity> shelters = shelterService.getAllShelters();

//        AddAnimalViewModel animal = new AddAnimalViewModel();
//        animal.setAnimalName(animalEntity.getAnimalName());
//        animal.setAnimalSpecies(animalEntity.getAnimalSpecies());
//        animal.setAnimalGender(animalEntity.getGender());
//        animal.setAnimalAge(animalEntity.getAnimalAge());
//        animal.setAnimalSize(animalEntity.getSizeCategory());
//        animal.setAnimalCharacteristics(animalEntity.getAnimalCharacteristics());
//        model.addAttribute("animal", animal);
//        model.addAttribute("shelters", shelters);
        return "animal-edit";
    }


    @PostMapping("/{id}/edit")
    public String updateAnimal(@PathVariable("id") long id, @ModelAttribute("animal") AddAnimalViewModel animalViewModel) {
        AnimalsEntity animal = animalService.getAnimalById(id);
//        animal.setAnimalName(animalViewModel.getAnimalName());
//        animal.setAnimalSpecies(animalViewModel.getAnimalSpecies());
//        animal.setGender(animalViewModel.getAnimalGender());
//        animal.setAnimalAge(animalViewModel.getAnimalAge());
//        animal.setSizeCategory(animalViewModel.getAnimalSize());
//        animal.setAnimalCharacteristics(animalViewModel.getAnimalCharacteristics());


        animalService.updateAnimal(animal);
        return "redirect:/animals";
    }

}


