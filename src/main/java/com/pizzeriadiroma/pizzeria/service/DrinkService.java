package com.pizzeriadiroma.pizzeria.service;

import com.pizzeriadiroma.pizzeria.entity.Drink;
import com.pizzeriadiroma.pizzeria.exception.DrinkNotFoundException;
import com.pizzeriadiroma.pizzeria.repository.DrinkRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DrinkService {

    private final DrinkRepository drinkRepository;

    public DrinkService(DrinkRepository drinkRepository) {
        this.drinkRepository = drinkRepository;
    }

    public List<Drink> getAllDrinks() {
        return drinkRepository.findAllAvailable();
    }

    public Drink findById(Long id) {
        return drinkRepository.findById(id)
                .orElseThrow(() -> new DrinkNotFoundException(id));
    }
}