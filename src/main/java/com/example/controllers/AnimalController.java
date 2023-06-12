package com.example.controllers;

import com.example.persistence.binding.AnimalAddBindingModel;
import com.example.persistence.binding.UpdateAnimalBindingModel;
import com.example.persistence.entities.AnimalsEntity;
import com.example.persistence.view.AddAnimalViewModel;
import com.example.service.AnimalService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.mapper.AnimalMapper;


import java.util.List;

@Controller
@RequestMapping("/animals")
public class AnimalController {
    private final AnimalService animalService;


    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @GetMapping("/showAll")
    public String showAnimalList(Model model) {
        List<AnimalsEntity> animals = animalService.getAllAnimals();
        model.addAttribute("animals", animals);
        return "animal-list";
    }

    @GetMapping("/add")
    public String showAddAnimalForm(Model model) {
        model.addAttribute("animal", new AnimalAddBindingModel());
        return "add-animal";
    }

    @PostMapping("/add")
    public String addAnimal(@ModelAttribute("animal") AnimalAddBindingModel animalAddBindingModel) {
        animalService.addAnimal(animalAddBindingModel);
        return "redirect:/animals/showAll";
    }

    @GetMapping("/{id}/edit")
    public String showUpdateAnimalForm(@PathVariable("id") long id, Model model) {
        AddAnimalViewModel existingAnimal = animalService.getAnimalById(id);

        model.addAttribute("animal", existingAnimal);
        return "edit-animal";
    }


    @PostMapping("/{id}/edit")
    public String updateAnimal(
            @PathVariable("id") long id,
            @ModelAttribute("animal") UpdateAnimalBindingModel updateAnimalBindingModel
    ) {
        animalService.updateAnimal(id, updateAnimalBindingModel);
        return "redirect:/animals/showAll";
    }


}
