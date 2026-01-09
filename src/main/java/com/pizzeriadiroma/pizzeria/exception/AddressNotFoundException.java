package com.pizzeriadiroma.pizzeria.exception;

public class AddressNotFoundException extends ResourceNotFoundException {
    public AddressNotFoundException(Long id) {
        super("Address not found with ID: " + id);
    }
}
