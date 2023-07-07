package com.example.controllers;

import com.example.configuration.auth.AuthenticationResponse;
import com.example.persistence.binding.UserLoginBindingModel;
import com.example.persistence.binding.UserRegisterBindingModel;
import com.example.persistence.entities.UserEntity;
import com.example.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import static com.example.util.Utility.getSiteURL;

@Controller
@RequestMapping("/users")  //@RequestMapping("/Authentication")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // Display the form to the user
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new UserRegisterBindingModel());
        return "register";
    }

    // Handle the form submission
    @PostMapping("/register")
    public String register(@ModelAttribute("user") UserRegisterBindingModel request, Model model) {
        AuthenticationResponse response = userService.register(request);
        model.addAttribute("user", response);
        return "redirect:/users/register";
    }

    @GetMapping("/user-list")
    public String userList(Model model) {
        List<UserEntity> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "user-list";
    }


    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("users", new UserLoginBindingModel());
        return "login";
    }

    @PostMapping("/login")
    public String authenticate(@ModelAttribute("users") UserLoginBindingModel request, Model model, HttpServletResponse response) {
        AuthenticationResponse authResponse = userService.authenticate(request);
        Cookie jwtCookie = new Cookie("jwt", authResponse.getUserAccessToken());
        jwtCookie.setHttpOnly(true);
        response.addCookie(jwtCookie);
        model.addAttribute("users", authResponse);
        return "redirect:/";
    }


    @PostMapping("/refresh-token")
    @ResponseBody
    public AuthenticationResponse refreshToken(@RequestParam String refreshToken) throws IOException {
        return userService.refreshToken(refreshToken);
    }

    @PostMapping("/process_register")
    public String processRegister(UserEntity user, HttpServletRequest request)
            throws UnsupportedEncodingException, MessagingException {
        userService.registerEmailSender(user, getSiteURL(request));
        return "register-success";
    }


    @GetMapping("/verify")
    public String verifyUser(@Param("code") String code) {
        if (userService.verify(code)) {
            return "verify-success";
        } else {
            return "register-fail";
        }
    }
}
