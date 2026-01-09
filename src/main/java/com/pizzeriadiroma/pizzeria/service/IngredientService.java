package com.pizzeriadiroma.pizzeria.service;

import com.pizzeriadiroma.pizzeria.entity.Ingredient;
import com.pizzeriadiroma.pizzeria.entity.Pizza;
import com.pizzeriadiroma.pizzeria.repository.IngredientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public List<Ingredient> findExtraIngredientsForPizza(Pizza pizza) {

        List<Ingredient> pizzaIngredients = pizza.getIngredients()
                .stream()
                .toList();

        List<Ingredient> extraIngredients = ingredientRepository.findAllByExtraTrueOrderByNameAsc();

        extraIngredients.removeAll(pizzaIngredients);

        return extraIngredients;

    }

    public Optional<Ingredient> findOptionalById(Integer id) {
        return ingredientRepository.findById(id);
    }
}
