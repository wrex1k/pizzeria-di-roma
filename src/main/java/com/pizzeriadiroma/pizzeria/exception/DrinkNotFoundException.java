package com.pizzeriadiroma.pizzeria.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class DrinkNotFoundException extends ResourceNotFoundException {

    public DrinkNotFoundException(Long id) {
        super("Drink not found with ID: " + id);
    }
}
