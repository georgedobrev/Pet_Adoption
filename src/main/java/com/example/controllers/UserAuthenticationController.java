package com.example.controllers;

import com.example.configuration.auth.AuthenticationResponse;
import com.example.persistence.binding.UserRegisterBindingModel;
import com.example.persistence.entities.UserEntity;
import com.example.persistence.entities.UserSecurityEntity;
import com.example.persistence.enums.RoleEnum;
import com.example.service.UserService;
import com.example.service.impl.AuthenticationServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.mapper.UserRegisterMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/users")  //@RequestMapping("/Authentication")
@RequiredArgsConstructor
public class UserAuthenticationController {

    private final AuthenticationServiceImpl service;


//    @PostMapping("/register")
//    public ResponseEntity<AuthenticationResponse> register(
//            @RequestBody RegisterRequest request) {
//        return ResponseEntity.ok(service.register(request));
//    }
    // Display the form to the user
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("request", new RegisterRequest());
        return "register"; // this is the name of the view (e.g., a Thymeleaf template) to display
    }

    // Handle the form submission
    @PostMapping("/register")
    public String register(@ModelAttribute("request") RegisterRequest request, Model model) {
        AuthenticationResponse response = service.register(request);
        model.addAttribute("response", response);
        return "redirect:/users/login"; // this is the name of the view to display after a successful registration
    }










    @GetMapping("/authenticate")
    public String showLoginForm(Model model) {
        model.addAttribute("request", new AuthenticationRequest());
        return "login";
    }

    @PostMapping("/authenticate")
    public String authenticate(@ModelAttribute("request") AuthenticationRequest request, Model model) {
        AuthenticationResponse response = service.authenticate(request);
        model.addAttribute("response", response);
        return "loginSuccess";
    }

    @PostMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        service.refreshToken(request, response);
    }
}
