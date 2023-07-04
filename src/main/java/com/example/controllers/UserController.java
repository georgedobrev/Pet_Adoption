package com.example.controllers;

import com.example.persistence.entities.AuthorityEntity;
import com.example.persistence.entities.UserEntity;
import com.example.persistence.enums.RoleEnum;
import com.example.persistence.repositories.AuthorityRepository;
import com.example.persistence.view.UserViewModel;
import com.example.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final AuthorityRepository authorityRepository;

    public UserController(UserService userService, AuthorityRepository authorityRepository) {
        this.userService = userService;
        this.authorityRepository = authorityRepository;
    }

    @GetMapping
    public String showUserList(Model model){
        List<UserEntity> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "user-list";
    }

    @GetMapping("/{id}/edit")
    public String showUpdateUserForm(@PathVariable("id") long id, Model model){
        UserViewModel existingUser = userService.getUserById(id);
        model.addAttribute("user", existingUser);
        model.addAttribute("userId", id);
        //model.addAttribute("allRoles", RoleEnum.values()); // Add this line to populate allRoles
        List<AuthorityEntity> roles = authorityRepository.findAll();
        model.addAttribute("allRoles", roles);
        return "user-edit";
    }

    @PostMapping("/{id}/roles")
    public String updateUserRoles(@PathVariable long id, @ModelAttribute("user") UserViewModel user) {
        userService.updateUserRoles(id, user.getAuthorities());
        return "redirect:/users";
    }



}
