package com.example.controllers;

import com.example.model.entities.AnimalsEntity;

import com.example.model.entities.SheltersEntity;
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

    @GetMapping
    public String getAllAnimals(Model model) {
        List<AnimalsEntity> animals = animalService.getAllAnimals();
        model.addAttribute("animals", animals);
        return "animal-list";
    }

    @GetMapping("/add")
    public String showAddAnimalForm(Model model) {
        List<SheltersEntity> shelters = shelterService.getAllShelters();
        model.addAttribute("shelters", shelters);
        model.addAttribute("animal", new AnimalsEntity());
        return "animal-add";
    }

    @PostMapping("/add")
    public String addAnimal(@ModelAttribute("animal") AnimalsEntity animal) {
        animalService.createAnimal(animal);
        return "redirect:/animals";
    }

    @GetMapping("/{id}/edit")
    public String showEditAnimalForm(@PathVariable("id") long id, Model model) {
        AnimalsEntity animal = animalService.getAnimalById(id);
        List<SheltersEntity> shelters = shelterService.getAllShelters();
        model.addAttribute("animal", animal);
        model.addAttribute("shelters", shelters);
        return "animal-edit";
    }

    @PostMapping("/{id}/edit")
    public String updateAnimal(@PathVariable("id") long id, @ModelAttribute("animal") AnimalsEntity animal) {
        animal.setAnimalID(id);
        animalService.updateAnimal(animal);
        return "redirect:/animals";
    }

    @GetMapping("/{id}/delete")
    public String deleteAnimal(@PathVariable("id") long id) {
        AnimalsEntity animal = animalService.getAnimalById(id);
        animalService.deleteAnimal(animal);
        return "redirect:/animals";
    }
}