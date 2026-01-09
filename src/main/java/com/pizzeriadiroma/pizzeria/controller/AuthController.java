package com.pizzeriadiroma.pizzeria.controller;

import com.pizzeriadiroma.pizzeria.dto.RegisterRequest;
import com.pizzeriadiroma.pizzeria.exception.ValidationException;
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
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "auth/index";
        }

        try {
            userService.validateAndRegister(registerRequest);
        } catch (ValidationException ex) {
            String msg = ex.getMessage() == null ? "Validation error." : ex.getMessage();

            if (msg.toLowerCase().contains("password")) {
                bindingResult.rejectValue("confirmPassword", "error.user", msg);
            } else if (msg.toLowerCase().contains("email")) {
                bindingResult.rejectValue("email", "error.user", msg);
            } else {
                bindingResult.reject("error.user", msg);
            }
            return "auth/index";
        }

        return "redirect:/login?register=true";
    }
}
