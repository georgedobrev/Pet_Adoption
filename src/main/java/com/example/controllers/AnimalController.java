package com.example.controllers;

import com.example.persistence.binding.AnimalAddBindingModel;
import com.example.persistence.entities.AnimalsEntity;
import com.example.persistence.entities.SheltersEntity;
import com.example.persistence.entities.SizeCategoryEntity;
import com.example.service.impl.AnimalServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/animals")
public class AnimalController {
    private final AnimalServiceImpl serviceAnimal;

    public AnimalController(AnimalServiceImpl serviceAnimal) {
        this.serviceAnimal = serviceAnimal;
    }

    @GetMapping("")
    public String showAnimalList(Model model) {
        List<AnimalsEntity> animals = serviceAnimal.getAllAnimals();
        model.addAttribute("animals", animals);
        return "animal-list";
    }

    @PostMapping("/add")
    public String addAnimal(@ModelAttribute("animal") AnimalAddBindingModel animalViewModel , SheltersEntity shelters , SizeCategoryEntity size) {
        return serviceAnimal.addAnimal(animalViewModel , shelters , size);
    }

    @PostMapping("/{id}/edit")
    public String updateAnimal(@PathVariable("id") long id, @ModelAttribute("animal") AnimalAddBindingModel animalViewModel) {
        return serviceAnimal.updateAnimal(id, animalViewModel);
    }
}

