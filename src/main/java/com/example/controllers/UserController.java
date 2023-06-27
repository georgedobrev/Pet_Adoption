package com.example.controllers;

import com.example.persistence.binding.UserRegisterBindingModel;
import com.example.persistence.entities.UserEntity;
import com.example.persistence.enums.RoleEnum;
import com.example.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.mapper.UserRegisterMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
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

//    @PostMapping("/login")
//    public String loginConfirm(@ModelAttribute("user") UserRegisterBindingModel user, HttpServletRequest request) {
//        UserDetails userDetails = userService.loadUserByUsername(user.getUserEmail());
//        if (userDetails != null && passwordEncoder.matches(user.getUserPassword(), userDetails.getPassword())) {
//            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
//                    userDetails.getPassword(),
//                    userDetails.getAuthorities());
//            SecurityContextHolder.getContext().setAuthentication(token);
//            return "redirect:/"; //userService
//        }
//        return "redirect:/users/login?error=true"; //add error
//    }

    @PostMapping("/login")
    public String loginConfirm(@ModelAttribute("user") UserRegisterBindingModel user) {
        try {
            userService.loginUser(user);
            return "redirect:/";
        } catch (BadCredentialsException ex) {
            return "redirect:/users/login?error=true";
        }
    }
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

    @PostMapping("/process_register")
    public String processRegister(UserEntity user, HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {
        userService.register(user, getSiteURL(request));
        return "register-success";
    }

    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }

    @GetMapping("/verify")
    public String verifyUser(@Param("code") String code) {
        if (userService.verify(code)) {
            return "verify-success";
        } else {
            return "verify-fail";
        }
    }

    @GetMapping("/about-schedule-a-meeting")
    public String aboutScheduleMeeting() {
        return "about-schedule-a-meeting";
    }
}
