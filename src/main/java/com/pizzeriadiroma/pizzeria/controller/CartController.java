package com.pizzeriadiroma.pizzeria.controller;

import com.pizzeriadiroma.pizzeria.entity.Cart;
import com.pizzeriadiroma.pizzeria.entity.Drink;
import com.pizzeriadiroma.pizzeria.entity.Pizza;
import com.pizzeriadiroma.pizzeria.entity.User;
import com.pizzeriadiroma.pizzeria.service.CartService;
import com.pizzeriadiroma.pizzeria.service.CompanyInfoService;
import com.pizzeriadiroma.pizzeria.service.DrinkService;
import com.pizzeriadiroma.pizzeria.service.PizzaService;
import com.pizzeriadiroma.pizzeria.service.IngredientService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private final PizzaService pizzaService;
    private final IngredientService ingredientService;
    private final DrinkService drinkService;
    private final CompanyInfoService companyInfoService;

    public CartController(CartService cartService, PizzaService pizzaService, IngredientService ingredientService, DrinkService drinkService, CompanyInfoService companyInfoService) {
        this.cartService = cartService;
        this.pizzaService = pizzaService;
        this.ingredientService = ingredientService;
        this.drinkService = drinkService;
        this.companyInfoService = companyInfoService;
    }

    @GetMapping
    public String viewCart(@AuthenticationPrincipal User user, Model model) {
        Cart cart = cartService.getOrCreateCart(user);
        model.addAttribute("cart", cart);
        model.addAttribute("ingredientService", ingredientService);
        model.addAttribute("companyInfo", companyInfoService.getCompanyInfo());
        model.addAttribute("deliveryFee", cartService.calculateDeliveryFee(cart));
        return "cart";
    }

    @PostMapping("/add-pizza")
    public String addToCart(
            @RequestParam Integer pizzaId,
            @RequestParam(defaultValue = "Small") String size,
            @RequestParam(defaultValue = "1") Integer quantity,
            @RequestParam(required = false) String ingredients,
            @AuthenticationPrincipal User user) {

        Pizza pizza = pizzaService.findById(pizzaId);
        if (pizza == null) {
            return "redirect:/";
        }

        cartService.addItem(user, pizza, size, quantity, ingredients);
        return "redirect:/pizza/" + pizza.getSlug();
    }

    @PostMapping("/add-drink")
    public String addDrinkToCart(
            @RequestParam Long drinkId,
            @RequestParam(defaultValue = "1") Integer quantity,
            @RequestParam(required = false) String returnTo,
            @AuthenticationPrincipal User user) {

        Drink drink = drinkService.findById(drinkId);
        if (drink == null) {
            return "redirect:/";
        }

        cartService.addDrinkItem(user, drink, quantity);

        return (returnTo != null && !returnTo.isBlank() && returnTo.startsWith("/"))
                ? "redirect:" + returnTo : "redirect:/";
    }

    @PostMapping("/update/{id}")
    public String updateItem(@PathVariable Integer id, @RequestParam Integer quantity, @AuthenticationPrincipal User user) {
        cartService.updateItemQuantity(user, id, quantity);
        return "redirect:/cart";
    }

    @PostMapping("/remove/{id}")
    public String removeItem(@PathVariable Integer id, @AuthenticationPrincipal User user) {
        cartService.removeItem(user, id);
        return "redirect:/cart";
    }
}
