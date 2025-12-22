package com.pizzeriadiroma.pizzeria.service;

import com.pizzeriadiroma.pizzeria.entity.Pizza;
import com.pizzeriadiroma.pizzeria.exception.PizzaNotFoundException;
import com.pizzeriadiroma.pizzeria.repository.PizzaRepository;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.List;

@Service
public class PizzaService {

    private final PizzaRepository pizzaRepository;

    public PizzaService(PizzaRepository pizzaRepository) {
        this.pizzaRepository = pizzaRepository;
    }

    public List<Pizza> getFeaturedPizzas() {
        return pizzaRepository.findFeaturedPizzas();
    }

    public List<Pizza> getAllPizzas() {
        return pizzaRepository.findAllAvailable();
    }

    public List<Pizza> getPizzasByTag(String tagName) {
        return pizzaRepository.findByTagName(tagName);
    }

    public Pizza findBySlug(String slug) {
        return pizzaRepository.findBySlug(slug)
                .orElseThrow(() -> new PizzaNotFoundException(slug));
    }

    private String generateSlug(String name) {
        if (name == null) return null;

        String normalized = Normalizer.normalize(name, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");

        return normalized.toLowerCase()
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("^-|-$", "");
    }
}
