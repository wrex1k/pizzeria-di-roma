package com.pizzeriadiroma.pizzeria.controller;

import com.pizzeriadiroma.pizzeria.service.PizzaService;
import com.pizzeriadiroma.pizzeria.service.TagService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    private final PizzaService pizzaService;
    private final TagService tagService;

    public HomeController(PizzaService pizzaService, TagService tagService) {
        this.pizzaService = pizzaService;
        this.tagService = tagService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("featuredPizzas", pizzaService.getFeaturedPizzas());
        model.addAttribute("tags", tagService.getAllTags());
        model.addAttribute("allPizzas", pizzaService.getAllPizzas());
        model.addAttribute("selectedTag", null);

        return "home";
    }

    @GetMapping("/filter")
    public String filter(@RequestParam(required = false) String tag, Model model) {

        if (tag == null || tag.isEmpty()) {
            model.addAttribute("pizzas", pizzaService.getAllPizzas());
        } else {
            model.addAttribute("pizzas", pizzaService.getPizzasByTag(tag));
        }

        return "fragments/components/pizza/pizza-grid :: pizza-grid(pizzas=${pizzas})";
    }


}
