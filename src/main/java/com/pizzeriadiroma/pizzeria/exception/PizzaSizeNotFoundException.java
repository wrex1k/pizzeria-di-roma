package com.pizzeriadiroma.pizzeria.exception;

public class PizzaSizeNotFoundException extends ResourceNotFoundException {

    public PizzaSizeNotFoundException(String name) {
        super("Pizza size not found: " + name);
    }
}
