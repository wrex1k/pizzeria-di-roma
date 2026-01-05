package com.pizzeriadiroma.pizzeria.controller;

import com.pizzeriadiroma.pizzeria.dto.RegisterRequest;
import com.pizzeriadiroma.pizzeria.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("user", new RegisterRequest());
        return "auth/index";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new RegisterRequest());
        return "auth/index";
    }

    @PostMapping("/register")
    public String register(
            @Valid @ModelAttribute("user") RegisterRequest registerRequest,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "auth/index";
        }

        String error = userService.validateAndRegister(registerRequest);
        
        if (error != null) {
            if (error.contains("Passwords")) {
                bindingResult.rejectValue("confirmPassword", "error.user", error);
            } else if (error.contains("Email")) {
                bindingResult.rejectValue("email", "error.user", error);
            }
            return "auth/index";
        }

        return "redirect:/login?register=true";
    }
}
