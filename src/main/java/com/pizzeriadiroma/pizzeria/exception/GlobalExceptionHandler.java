package com.pizzeriadiroma.pizzeria.exception;

import com.pizzeriadiroma.pizzeria.exception.UserNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({UserNotFoundException.class})
    public String handleUserNotFound(UserNotFoundException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("errorTitle", "User Not Found");
        return "error/404";
    }

    @ExceptionHandler({PizzaNotFoundException.class})
    public String handlePizzaNotFound(PizzaNotFoundException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("errorTitle", "Pizza Not Found");
        return "error/404";
    }

    @ExceptionHandler({RuntimeException.class})
    public String handleGeneralError(RuntimeException ex, Model model) {
        model.addAttribute("errorMessage", "An unexpected error occurred: " + ex.getMessage());
        model.addAttribute("errorTitle", "Error");
        return "error/404";
    }
}
