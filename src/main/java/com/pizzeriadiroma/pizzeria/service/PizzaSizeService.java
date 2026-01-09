package com.pizzeriadiroma.pizzeria.service;

import com.pizzeriadiroma.pizzeria.entity.PizzaSize;
import com.pizzeriadiroma.pizzeria.exception.PizzaSizeNotFoundException;
import com.pizzeriadiroma.pizzeria.repository.PizzaSizeRepository;
    import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PizzaSizeService {

    private final PizzaSizeRepository pizzaSizeRepository;

    public PizzaSizeService(PizzaSizeRepository pizzaSizeRepository) {
        this.pizzaSizeRepository = pizzaSizeRepository;
    }

    public PizzaSize getByName(String name) {
        return pizzaSizeRepository.findByName(name)
                .orElseThrow(() -> new PizzaSizeNotFoundException(name));
    }

    public List<PizzaSize> getAll() {
        return pizzaSizeRepository.findAll();
    }
}
