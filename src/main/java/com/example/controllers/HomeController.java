//package com.example.controllers;
//
//import com.example.service.AnimalService;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//
//@Controller
//public class HomeController {
//
//    private final AnimalService animalService;
//
//    public HomeController(AnimalService animalService) {
//        this.animalService = animalService;
//    }
//
//    @GetMapping("/")
//    public String showHomePage(Model model) {
//       model.addAttribute("animals", animalService.getAllAnimals());
//        return "home";
//    }
//}