package com.example.controllers;

import com.example.persistence.binding.UserAddBindingModel;
import com.example.persistence.entities.UserEntity;
import com.example.persistence.enums.RoleEnum;
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

    public UserController(UserService userService) {
        this.userService = userService;
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
        return "user-edit";
    }

    @PostMapping("/users/{id}/roles")
    public String updateUserRoles(@PathVariable long id, @RequestParam Set<RoleEnum> newRoles) {
        userService.updateUserRoles(id, newRoles);
        return "redirect:/users/" + id;  // Redirect to the user details page
    }


}
