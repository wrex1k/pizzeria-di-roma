package com.pizzeriadiroma.pizzeria.controller;

import com.pizzeriadiroma.pizzeria.entity.Pizza;
import com.pizzeriadiroma.pizzeria.entity.PizzaSize;
import com.pizzeriadiroma.pizzeria.service.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

@Controller
public class PizzaController {

    private final PizzaService pizzaService;
    private final DrinkService drinkService;
    private final IngredientService ingredientService;
    private final PizzaSizeService pizzaSizeService;
    private final PriceCalculationService priceCalculationService;
    private final CompanyInfoService companyInfoService;

    public PizzaController(PizzaService pizzaService, DrinkService drinkService, IngredientService ingredientService, PizzaSizeService pizzaSizeService, PriceCalculationService priceCalculationService, CompanyInfoService companyInfoService) {
        this.pizzaService = pizzaService;
        this.drinkService = drinkService;
        this.ingredientService = ingredientService;
        this.pizzaSizeService = pizzaSizeService;
        this.priceCalculationService = priceCalculationService;
        this.companyInfoService = companyInfoService;
    }

    @GetMapping("/pizza/{slug}")
    public String pizzaDetail(@PathVariable String slug, HttpServletRequest request, Model model) {

        Pizza pizza = pizzaService.findBySlug(slug);

        model.addAttribute("pizza", pizza);
        model.addAttribute("pizzaTags", pizza.getTags());
        model.addAttribute("sizes", pizzaSizeService.getAll());
        model.addAttribute("allIngredients", ingredientService.findExtraIngredientsForPizza(pizza));
        model.addAttribute("allDrinks", drinkService.getAllDrinks());
        model.addAttribute("companyInfo", companyInfoService.getCompanyInfo());
        model.addAttribute("extraIngredientPrice", priceCalculationService.getExtraIngredientPrice());
        model.addAttribute("returnTo", request.getRequestURI());

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
        PizzaSize selectedSize = pizzaSizeService.getByName(size);

        List<String> extraIds = (ingredients == null || ingredients.isBlank())
                ? Collections.emptyList()
                : Arrays.asList(ingredients.split(","));

        BigDecimal total = priceCalculationService.calculatePizzaTotal(pizza, selectedSize, quantity, extraIds);

        return new PriceResponse(total.toPlainString());
    }

    public record PriceResponse(String price) {}

}
