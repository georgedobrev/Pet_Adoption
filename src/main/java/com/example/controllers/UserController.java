package com.example.controllers;

import com.example.persistence.binding.UserRegisterBindingModel;
import com.example.persistence.entities.UserEntity;
import com.example.persistence.view.UserViewModel;
import com.example.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new UserViewModel());
        return "register";
    }

    @PostMapping("/register")
    public String registerConfirm(@ModelAttribute UserRegisterBindingModel userEntity) {
        userService.register(modelMapper.map(userEntity, UserEntity.class));
        return "redirect:/users/login";
    }
}
