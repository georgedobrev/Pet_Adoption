package com.example.controllers;

import com.example.persistence.binding.UserRegisterBindingModel;
import com.example.persistence.entities.UserEntity;
import com.example.persistence.enums.RoleEnum;
import com.example.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.mapper.UserRegisterMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService,  PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new UserRegisterBindingModel());
        return "login";
    }

    @PostMapping("/login")
    public String loginConfirm(@ModelAttribute("user") UserRegisterBindingModel user, HttpServletRequest request) {
        UserDetails userDetails = userService.loadUserByUsername(user.getUserEmail());
        if (userDetails != null && passwordEncoder.matches(user.getUserPassword(), userDetails.getPassword())) {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    userDetails.getPassword(),
                    userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(token);
            return "redirect:/";
        }
        return "redirect:/users/login?error=true"; //add error
    }
//    @PostMapping("/login")
//    public String loginConfirm(@ModelAttribute("user") UserRegisterBindingModel user, HttpServletRequest request) {
//        UserDetails userDetails = userService.loadUserByUsername(user.getUserEmail());
//        if (userDetails != null && passwordEncoder.matches(user.getUserPassword(), userDetails.getPassword())) {
//            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
//                    userDetails.getUsername(), // Use getUsername() instead of getPassword()
//                    userDetails.getPassword(),
//                    userDetails.getAuthorities());
//            SecurityContextHolder.getContext().setAuthentication(token);
//            return "redirect:/";
//        }
//        return "redirect:/users/login?error=true";
//    }


    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new UserRegisterBindingModel());
        return "register";
    }

    @PostMapping("/register")
    public String registerConfirm(@ModelAttribute("user") UserRegisterBindingModel user) {
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
