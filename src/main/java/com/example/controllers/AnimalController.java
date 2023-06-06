package com.example.controllers;

import com.example.persistence.binding.AnimalAddBindingModel;
import com.example.persistence.entities.AnimalsEntity;
import com.example.persistence.entities.SheltersEntity;
import com.example.service.ServiceAnimal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/animals")
public class AnimalController {
    private final ServiceAnimal serviceAnimal;

    public AnimalController(ServiceAnimal serviceAnimal) {
        this.serviceAnimal = serviceAnimal;
    }

    @GetMapping("")
    public String showAnimalList(Model model) {
        List<AnimalsEntity> animals = serviceAnimal.getAllAnimals();
        model.addAttribute("animals", animals);
        return "animal-list";
    }

    @GetMapping("/add")
    public String showAddAnimalForm(Model model) {
        List<SheltersEntity> shelters = serviceAnimal.getAllShelters();
        model.addAttribute("shelters", shelters);
        model.addAttribute("animal", serviceAnimal.getAddAnimalViewModel());
        return "animal-add";
    }

    @PostMapping("/add")
    public String addAnimal(@ModelAttribute("animal") AnimalAddBindingModel animalViewModel) {
        return serviceAnimal.addAnimal(animalViewModel);
    }

    @GetMapping("/{id}/edit")
    public String showEditAnimalForm(@PathVariable("id") long id, Model model) {
        List<SheltersEntity> shelters = serviceAnimal.getAllShelters();
        model.addAttribute("animal", serviceAnimal.getAnimalForEdit(id));
        model.addAttribute("shelters", shelters);
        return "animal-edit";
    }

    @PostMapping("/{id}/edit")
    public String updateAnimal(@PathVariable("id") long id, @ModelAttribute("animal") AnimalAddBindingModel animalViewModel) {
        return serviceAnimal.updateAnimal(id, animalViewModel);
    }
}

