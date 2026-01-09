package com.pizzeriadiroma.pizzeria.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class PizzaNotFoundException extends ResourceNotFoundException {

    public PizzaNotFoundException(String slug) {
        super("Pizza not found with slug: " + slug);
    }

    public PizzaNotFoundException(Integer id) { super("Pizza not found with ID: " + id); }
}