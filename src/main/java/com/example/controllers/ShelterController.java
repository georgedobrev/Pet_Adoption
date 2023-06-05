package com.example.controllers;

import com.example.persistence.binding.ShelterAddBindingModel;
import com.example.persistence.entities.AnimalsEntity;
import com.example.persistence.entities.SheltersEntity;
import com.example.persistence.view.AddShelterViewModel;
import com.example.service.AnimalService;
import com.example.service.ShelterService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/shelters")
public class ShelterController {
    private final ShelterService shelterService;
    private final AnimalService animalService;

    public ShelterController(ShelterService shelterService, AnimalService animalService) {
        this.shelterService = shelterService;
        this.animalService = animalService;
    }

    @GetMapping("/add")
    public String showAddShelterForm(Model model) {
        model.addAttribute("shelter", shelterService.getAddShelterViewModel());
        return "shelter-add";
    }

    @PostMapping("/add")
    public String addShelter(@ModelAttribute("shelter") ShelterAddBindingModel shelterViewModel) {
        return shelterService.addShelter(shelterViewModel);
    }

    @GetMapping("/{id}/edit")
    public String showEditShelterForm(@PathVariable("id") long id, Model model) {
        model.addAttribute("shelter", shelterService.getShelterForEditing(id));
        return "shelter-edit";
    }

    @PostMapping("/{id}/edit")
    public String updateShelter(@PathVariable("id") long id, @ModelAttribute("shelter") ShelterAddBindingModel shelterViewModel) {
        return shelterService.updateShelter(id, shelterViewModel);
    }

}
