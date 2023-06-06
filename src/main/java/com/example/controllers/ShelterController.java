package com.example.controllers;

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

    @GetMapping
    public String getAllShelters(Model model) {
        model.addAttribute("shelters", shelterService.getAllShelters());
        return "shelter-list";
    }

    @GetMapping("/add")
    public String showAddShelterForm(Model model) {
        model.addAttribute("shelter", new SheltersEntity());
        return "shelter-add";
    }

    @PostMapping("/add")
    public String addShelter(@ModelAttribute("shelter") AddShelterViewModel shelterViewModel) {
        SheltersEntity shelter = new SheltersEntity();
        shelter.setShelterName(shelterViewModel.getShelterName());
        shelter.setShelterCity(shelterViewModel.getShelterCity());
        shelter.setShelterEmail(shelterViewModel.getShelterEmail());
        shelter.setShelterAddress(shelterViewModel.getShelterAddress());

        shelterService.createShelter(shelter);
        return "redirect:/shelters";
    }

    @GetMapping("/{id}")
    public String getAnimalsByShelterId(@PathVariable("id") long id, Model model) {
        SheltersEntity shelter = shelterService.getShelterById(id);
        List<AnimalsEntity> animals = animalService.getAnimalsByShelterId(shelter.getShelterID());
        model.addAttribute("shelter", shelter);
        model.addAttribute("animals", animals);
        return "shelter-animals";
    }

    @GetMapping("/{id}/edit")
    public String showEditShelterForm(@PathVariable("id") long id, Model model) {
        SheltersEntity shelter = shelterService.getShelterById(id);
        model.addAttribute("shelter", shelter);
        return "shelter-edit";
    }

    @PostMapping("/{id}/edit")
    public String updateShelter(@PathVariable("id") long id, @ModelAttribute("shelter") SheltersEntity shelter) {
        shelter.setShelterID(id);
        shelterService.updateShelter(shelter);
        return "redirect:/shelters";
    }

    @GetMapping("/{id}/delete")
    public String deleteShelter(@PathVariable("id") long id) {
        SheltersEntity shelter = shelterService.getShelterById(id);
        shelterService.deleteShelter(shelter);
        return "redirect:/shelters";
    }
}
