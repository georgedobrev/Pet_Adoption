package com.example.controllers;

import com.example.persistence.entities.SheltersEntity;
import com.example.service.ShelterService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/shelters")
public class ShelterController {
    private final ShelterService shelterService;

    public ShelterController(ShelterService shelterService) {
        this.shelterService = shelterService;
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
    public String addShelter(@ModelAttribute("shelter") SheltersEntity shelter) {
        shelterService.createShelter(shelter);
        return "redirect:/shelters";
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