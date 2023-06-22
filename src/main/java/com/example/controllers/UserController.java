//package com.example.controllers;
//
//import com.example.persistence.binding.UserRegisterBindingModel;
//import com.example.persistence.entities.UserEntity;
//import com.example.persistence.entities.UserSecurityEntity;
//import com.example.persistence.enums.RoleEnum;
//import com.example.service.UserService;
//import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import com.example.mapper.UserRegisterMapper;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.security.Principal;
//import java.util.List;
//
//@Controller
//@RequestMapping("/users")
//public class UserController {
//    // equal to authenticationController
//    private final UserService userService;
//    private final PasswordEncoder passwordEncoder;
//
//
//    public UserController(UserService userService,  PasswordEncoder passwordEncoder) {
//        this.userService = userService;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    @GetMapping("/login")
//    public String login(Model model) {
//        model.addAttribute("user", new UserRegisterBindingModel());
//        return "login";
//    }
//    //SpringSecurity does that on its own!
////    @PostMapping("/login")
////    public String loginConfirm(@ModelAttribute("user") UserRegisterBindingModel user) {
////        try {
////            UserEntity userEntity = userService.loginUser(user);
////            UserDetails userDetails = new UserSecurityEntity(userEntity);
////            UsernamePasswordAuthenticationToken authentication =
////                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
////            SecurityContextHolder.getContext().setAuthentication(authentication);
////            return "redirect:/";
////        } catch (BadCredentialsException ex) {
////            return "redirect:/users/login?error=true";
////        }
////    }
//
//    @GetMapping("/register")
//    public String register(Model model) {
//        model.addAttribute("user", new UserRegisterBindingModel());
//        return "register";
//    }
//    @PostMapping("/register")
//    public String registerConfirm(@ModelAttribute("user") UserRegisterBindingModel user) {
//        userService.register(user);
//        return "redirect:/users/login";
//    }
//
//
//    @GetMapping("/user-list")
//    public String userList(Model model) {
//        List<UserEntity> users = userService.getAllUsers();
//        model.addAttribute("users", users);
//        return "user-list";
//    }
//}
