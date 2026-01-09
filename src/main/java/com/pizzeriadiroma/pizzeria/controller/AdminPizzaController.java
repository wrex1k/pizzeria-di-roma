package com.pizzeriadiroma.pizzeria.controller;

import com.pizzeriadiroma.pizzeria.dto.AddPizzaRequest;
import com.pizzeriadiroma.pizzeria.entity.Pizza;
import com.pizzeriadiroma.pizzeria.repository.IngredientRepository;
import com.pizzeriadiroma.pizzeria.repository.PizzaRepository;
import com.pizzeriadiroma.pizzeria.repository.TagRepository;
import com.pizzeriadiroma.pizzeria.service.PizzaAdminService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/pizzas")
public class AdminPizzaController {

    private final PizzaRepository pizzaRepository;
    private final TagRepository tagRepository;
    private final IngredientRepository ingredientRepository;
    private final PizzaAdminService pizzaAdminService;

    public AdminPizzaController(
            PizzaRepository pizzaRepository,
            TagRepository tagRepository,
            IngredientRepository ingredientRepository,
            PizzaAdminService pizzaAdminService
    ) {
        this.pizzaRepository = pizzaRepository;
        this.tagRepository = tagRepository;
        this.ingredientRepository = ingredientRepository;
        this.pizzaAdminService = pizzaAdminService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("pizzas", pizzaRepository.findAll());
        model.addAttribute("allTags", tagRepository.findAll());
        model.addAttribute("allIngredients", ingredientRepository.findAll());

        if (!model.containsAttribute("pizzaForm")) {
            model.addAttribute("pizzaForm", new AddPizzaRequest());
        }

        if (!model.containsAttribute("editing")) {
            model.addAttribute("editing", false);
        }

        return "admin/pizzas";
    }

    @PostMapping
    public String create(
            @Valid @ModelAttribute("pizzaForm") AddPizzaRequest pizzaForm,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("pizzas", pizzaRepository.findAll());
            model.addAttribute("allTags", tagRepository.findAll());
            model.addAttribute("allIngredients", ingredientRepository.findAll());
            model.addAttribute("editing", false);
            return "admin/pizzas";
        }

        Pizza pizza = new Pizza();
        pizzaAdminService.applyFormToEntity(pizzaForm, pizza);
        pizza.setSlug(pizzaAdminService.uniqueSlugForName(pizza.getName(), null));

        pizzaRepository.save(pizza);

        redirectAttributes.addFlashAttribute("success", "Pizza created successfully!");
        return "redirect:/admin/pizzas";
    }

    @GetMapping("/{slug}/edit")
    public String edit(@PathVariable String slug, Model model, RedirectAttributes redirectAttributes) {
        Pizza pizza = pizzaRepository.findBySlug(slug).orElse(null);
        if (pizza == null) {
            redirectAttributes.addFlashAttribute("error", "Pizza not found.");
            return "redirect:/admin/pizzas";
        }

        if (!model.containsAttribute("pizzaForm")) {
            model.addAttribute("pizzaForm", pizzaAdminService.toForm(pizza));
        }

        model.addAttribute("pizzas", pizzaRepository.findAll());
        model.addAttribute("allTags", tagRepository.findAll());
        model.addAttribute("allIngredients", ingredientRepository.findAll());

        model.addAttribute("editing", true);
        model.addAttribute("editSlug", slug);

        return "admin/pizzas";
    }

    @PostMapping("/{slug}/edit")
    public String update(
            @PathVariable String slug,
            @Valid @ModelAttribute("pizzaForm") AddPizzaRequest pizzaForm,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        Pizza pizza = pizzaRepository.findBySlug(slug).orElse(null);
        if (pizza == null) {
            redirectAttributes.addFlashAttribute("error", "Pizza not found.");
            return "redirect:/admin/pizzas";
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("pizzas", pizzaRepository.findAll());
            model.addAttribute("allTags", tagRepository.findAll());
            model.addAttribute("allIngredients", ingredientRepository.findAll());

            model.addAttribute("editing", true);
            model.addAttribute("editSlug", slug);

            return "admin/pizzas";
        }

        pizzaAdminService.applyFormToEntity(pizzaForm, pizza);
        pizza.setSlug(pizzaAdminService.uniqueSlugForName(pizza.getName(), pizza.getId()));
        pizzaRepository.save(pizza);

        redirectAttributes.addFlashAttribute("success", "Pizza updated successfully!");
        return "redirect:/admin/pizzas/" + pizza.getSlug() + "/edit";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        pizzaAdminService.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Pizza deleted successfully!");
        return "redirect:/admin/pizzas";
    }

}
