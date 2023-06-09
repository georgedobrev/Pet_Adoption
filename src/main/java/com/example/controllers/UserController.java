package com.example.controllers;

import com.example.persistence.binding.UserRegisterBindingModel;
import com.example.persistence.entities.UserEntity;
import com.example.persistence.enums.RoleEnum;
import com.example.service.UserService;
import com.example.mapper.UserRegisterMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UserRegisterMapper userRegisterMapper;

    public UserController(UserService userService, UserRegisterMapper userRegisterMapper) {
        this.userService = userService;
        this.userRegisterMapper = userRegisterMapper;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new UserRegisterBindingModel());
        return "register";
    }

    @PostMapping("/register")
    public String registerConfirm(@ModelAttribute("user") UserRegisterBindingModel user) {
        UserEntity userEntity = userRegisterMapper.toUserEntity(user);
        userEntity.setRoles(RoleEnum.USER);
        userService.register(user);
        return "redirect:/users/login";
    }

    @GetMapping("/user-list")
    public String userList(Model model) {
        List<UserEntity> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "user-list";
    }


}
