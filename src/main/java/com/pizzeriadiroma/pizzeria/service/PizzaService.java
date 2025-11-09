package com.pizzeriadiroma.pizzeria.service;

import com.pizzeriadiroma.pizzeria.dto.PizzaDto;
import com.pizzeriadiroma.pizzeria.entity.Pizza;
import com.pizzeriadiroma.pizzeria.repository.PizzaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PizzaService {

    private final PizzaRepository pizzaRepository;

    public PizzaService(PizzaRepository pizzaRepository) {
        this.pizzaRepository = pizzaRepository;
    }

    public List<PizzaDto> getFeaturedPizzas() {
        return pizzaRepository.findFeaturedPizzas()
                .stream()
                .map(this::toDto)
                .toList();
    }

    public List<PizzaDto> getAllPizzas() {
        return pizzaRepository.findAllAvailable()
                .stream()
                .map(this::toDto)
                .toList();
    }

    public List<PizzaDto> getPizzasByTag(String tagName) {
        return pizzaRepository.findByTagName(tagName)
                .stream()
                .map(this::toDto)
                .toList();
    }

    private PizzaDto toDto(Pizza pizza) {
        List<String> tagNames = pizza.getTags() != null
                ? pizza.getTags().stream()
                .map(tag -> tag.getName())
                .sorted()
                .toList()
                : List.of();

        return new PizzaDto(
                pizza.getId(),
                pizza.getName(),
                pizza.getDescription(),
                pizza.getImageUrl(),
                pizza.getBasePrice(),
                pizza.getIsAvailable(),
                pizza.getIsFeatured(),
                tagNames
        );
    }

}
