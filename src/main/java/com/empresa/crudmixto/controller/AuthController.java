package com.empresa.crudmixto.controller;

import com.empresa.crudmixto.entity.User;
import com.empresa.crudmixto.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "register";
        }

        // Verificar si el email ya existe
        if (userService.findByEmail(user.getEmail()).isPresent()) {
            model.addAttribute("emailError", "El correo electrónico ya está registrado");
            return "register";
        }

        // Verificar si el username ya existe
        if (userService.findByUsername(user.getUsername()).isPresent()) {
            model.addAttribute("usernameError", "El nombre de usuario ya está registrado");
            return "register";
        }

        userService.save(user);
        return "redirect:/login?registered=true";
    }
}