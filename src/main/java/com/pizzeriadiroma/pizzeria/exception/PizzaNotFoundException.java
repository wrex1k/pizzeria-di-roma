package com.pizzeriadiroma.pizzeria.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PizzaNotFoundException extends RuntimeException {

    public PizzaNotFoundException(String slug) {
        super("Pizza not found with slug: " + slug);
    }
}
