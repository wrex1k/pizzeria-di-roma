package com.pizzeriadiroma.pizzeria.controller;

import com.pizzeriadiroma.pizzeria.entity.Pizza;
import com.pizzeriadiroma.pizzeria.entity.PizzaSize;
import com.pizzeriadiroma.pizzeria.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Controller
public class PizzaController {

    private final PizzaService pizzaService;
    private final DrinkService drinkService;
    private final IngredientService ingredientService;
    private final PizzaSizeService pizzaSizeService;
    private final NutritionService nutritionService;

    public PizzaController(PizzaService pizzaService, DrinkService drinkService, IngredientService ingredientService, PizzaSizeService pizzaSizeService, NutritionService nutritionService) {
        this.pizzaService = pizzaService;
        this.drinkService = drinkService;
        this.ingredientService = ingredientService;
        this.pizzaSizeService = pizzaSizeService;
        this.nutritionService = nutritionService;
    }

    @GetMapping("/pizza/{slug}")
    public String pizzaDetail(@PathVariable String slug, Model model) {

        Pizza pizza = pizzaService.findBySlug(slug);

        model.addAttribute("pizza", pizza);
        model.addAttribute("pizzaTags", pizza.getTags());

        Map<String, Integer> nutrition = nutritionService.calculate(pizza, pizzaSizeService.getByName("MEDIUM"));
        model.addAttribute("nutrition", nutrition);
        model.addAttribute("sizes", pizzaSizeService.getAll());


        model.addAttribute("allIngredients", ingredientService.findExtraIngredientsForPizza(pizza));
        model.addAttribute("allDrinks", drinkService.getAllDrinks());

        return "pizza";
    }

    @PostMapping("/pizza/calc-price")
    @ResponseBody
    public PriceResponse calculatePrice(
            @RequestParam String slug,
            @RequestParam String size,
            @RequestParam Integer quantity,
            @RequestParam(required = false) String ingredients
    ) {
        Pizza pizza = pizzaService.findBySlug(slug);
        BigDecimal base = pizza.getBasePrice();

        PizzaSize selectedSize = pizzaSizeService.getByName(size);

        List<String> extraIds = (ingredients == null || ingredients.isBlank())
                ? Collections.emptyList()
                : Arrays.asList(ingredients.split(","));

        BigDecimal extraPrice = BigDecimal.valueOf(extraIds.size())
                .multiply(BigDecimal.valueOf(1.50))
                .setScale(2, RoundingMode.HALF_UP);

        BigDecimal priceForSize = base
                .add(selectedSize.getPriceExtra())
                .setScale(2, RoundingMode.HALF_UP);

        BigDecimal perPizza = priceForSize.add(extraPrice)
                .setScale(2, RoundingMode.HALF_UP);

        BigDecimal total = perPizza
                .multiply(BigDecimal.valueOf(quantity))
                .setScale(2, RoundingMode.HALF_UP);

        return new PriceResponse(total.toPlainString());
    }

    public record PriceResponse(String price) {}

    @GetMapping("/pizza/nutrition")
    @ResponseBody
    public Map<String, Integer> getNutrition(
            @RequestParam String slug,
            @RequestParam String size
    ) {
        Pizza pizza = pizzaService.findBySlug(slug);
        PizzaSize pizzaSize = pizzaSizeService.getByName(size);

        return nutritionService.calculate(pizza, pizzaSize);
    }

}
