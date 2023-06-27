package com.example.controllers;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String showHomePage() {
        return "index";
    }

    @GetMapping("/home")
    public String showHome() {
        return "index";
    }
 //  @GetMapping("/user")
 //  public String getUserPage(OAuth2AuthenticationToken authentication, Model model) {
 //      if (authentication != null) {
 //          String name = authentication.getPrincipal().getAttribute("name");
 //          String emaul = authentication.getPrincipal().getAttribute("lastNAme");
 //          String firstname = authentication.getPrincipal().getAttribute("firstname");
 //          String lastname = authentication.getPrincipal().getAttribute("email");
 //          model.addAttribute("name", name);
 //      }
 //      return "user";
 //  }

}