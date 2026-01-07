package com.pizzeriadiroma.pizzeria.service;

import com.pizzeriadiroma.pizzeria.entity.Cart;
import com.pizzeriadiroma.pizzeria.entity.CartItem;
import com.pizzeriadiroma.pizzeria.entity.Drink;
import com.pizzeriadiroma.pizzeria.entity.Pizza;
import com.pizzeriadiroma.pizzeria.entity.PizzaSize;
import com.pizzeriadiroma.pizzeria.entity.User;
import com.pizzeriadiroma.pizzeria.repository.CartRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Optional;
@Service
public class CartService {

    private final CartRepository cartRepository;
    private final PizzaSizeService pizzaSizeService;
    private final IngredientService ingredientService;
    private final PriceCalculationService priceCalculationService;

    public CartService(CartRepository cartRepository, PizzaSizeService pizzaSizeService, IngredientService ingredientService, PriceCalculationService priceCalculationService) {
        this.cartRepository = cartRepository;
        this.pizzaSizeService = pizzaSizeService;
        this.ingredientService = ingredientService;
        this.priceCalculationService = priceCalculationService;
    }

    public Cart getOrCreateCart(User user) {
        return cartRepository.findByUser(user).orElseGet(() -> {
            Cart cart = new Cart();
            cart.setUser(user);
            return cartRepository.save(cart);
        });
    }

    @Transactional
    public void addItem(User user, Pizza pizza, String size, Integer quantity, String extraIngredients) {
        validatePositiveQuantity(quantity);

        Cart cart = getOrCreateCart(user);

        PizzaSize pizzaSize = requirePizzaSize(size);

        String ingredientNames = resolveIngredientNames(extraIngredients);
        BigDecimal unitPrice = calculatePizzaUnitPrice(pizza, pizzaSize, extraIngredients);

        Optional<CartItem> existing = findPizzaItem(cart, pizza, pizzaSize, ingredientNames);

        if (existing.isPresent()) {
            increaseQuantityAndRecalc(existing.get(), quantity, unitPrice);
        } else {
            CartItem item = createPizzaItem(cart, pizza, pizzaSize, ingredientNames, unitPrice, quantity);
            cart.getItems().add(item);
        }

        touchAndSave(cart);
    }

    @Transactional
    public void addDrinkItem(User user, Drink drink, Integer quantity) {
        validatePositiveQuantity(quantity);

        Cart cart = getOrCreateCart(user);

        BigDecimal unitPrice = money(drink.getPrice());

        Optional<CartItem> existing = findDrinkItem(cart, drink);

        if (existing.isPresent()) {
            increaseQuantityAndRecalc(existing.get(), quantity, unitPrice);
        } else {
            CartItem item = createDrinkItem(cart, drink, unitPrice, quantity);
            cart.getItems().add(item);
        }

        touchAndSave(cart);
    }

    @Transactional
    public void updateItemQuantity(User user, Integer itemId, Integer quantity) {
        validateQuantityNotNull(quantity);

        Cart cart = getOrCreateCart(user);

        CartItem item = findItemOrThrow(cart, itemId);

        requireOwnership(user, item);

        if (quantity <= 0) {
            cart.getItems().remove(item);
        } else {
            item.setQuantity(quantity);
            item.setTotalPrice(money(item.getUnitPrice().multiply(BigDecimal.valueOf(quantity))));
        }

        touchAndSave(cart);
    }

    @Transactional
    public void removeItem(User user, Integer itemId) {
        Cart cart = getOrCreateCart(user);

        cart.getItems().removeIf(item ->
                item.getId().equals(itemId) &&
                        item.getCart().getUser().getId().equals(user.getId())
        );

        touchAndSave(cart);
    }

    @Transactional
    public void clearCart(User user) {
        Cart cart = getOrCreateCart(user);
        cart.getItems().clear();
        touchAndSave(cart);
    }

    public int getCartItemCount(User user) {
        if (user == null) return 0;

        return cartRepository.findByUser(user)
                .map(cart -> cart.getItems().stream().mapToInt(CartItem::getQuantity).sum())
                .orElse(0);
    }

    private void validatePositiveQuantity(Integer quantity) {
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
    }

    private void validateQuantityNotNull(Integer quantity) {
        if (quantity == null) {
            throw new IllegalArgumentException("Quantity cannot be null");
        }
    }

    private PizzaSize requirePizzaSize(String size) {
        PizzaSize pizzaSize = pizzaSizeService.getByName(size);
        if (pizzaSize == null) {
            throw new IllegalArgumentException("Invalid pizza size: " + size);
        }
        return pizzaSize;
    }

    private BigDecimal calculatePizzaUnitPrice(Pizza pizza, PizzaSize pizzaSize, String extraIngredients) {
        BigDecimal unit = pizza.getBasePrice().add(pizzaSize.getPriceExtra());
        unit = unit.add(calculateExtraIngredientsPrice(extraIngredients));
        return money(unit);
    }

    private BigDecimal calculateExtraIngredientsPrice(String extraIngredients) {
        if (extraIngredients == null || extraIngredients.trim().isEmpty()) {
            return BigDecimal.ZERO;
        }

        long count = java.util.Arrays.stream(extraIngredients.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .count();

        return priceCalculationService.getExtraIngredientPrice().multiply(BigDecimal.valueOf(count));
    }

    private String resolveIngredientNames(String extraIngredients) {
        if (extraIngredients == null || extraIngredients.trim().isEmpty()) {
            return null;
        }

        StringBuilder names = new StringBuilder();
        for (String idStr : extraIngredients.split(",")) {
            int id = Integer.parseInt(idStr.trim());
            ingredientService.findById(id).ifPresent(ing -> {
                if (!names.isEmpty()) names.append(", ");
                names.append(ing.getName());
            });
        }

        return names.isEmpty() ? null : names.toString();
    }

    private Optional<CartItem> findPizzaItem(Cart cart, Pizza pizza, PizzaSize pizzaSize, String ingredientNames) {
        return cart.getItems().stream()
                .filter(item -> item.getPizza() != null
                        && item.getPizza().getId().equals(pizza.getId())
                        && item.getPizzaSize().getId().equals(pizzaSize.getId())
                        && java.util.Objects.equals(item.getExtraIngredients(), ingredientNames))
                .findFirst();
    }

    private Optional<CartItem> findDrinkItem(Cart cart, Drink drink) {
        return cart.getItems().stream()
                .filter(item -> item.getDrink() != null && item.getDrink().getId().equals(drink.getId()))
                .findFirst();
    }

    private void increaseQuantityAndRecalc(CartItem item, int addQuantity, BigDecimal unitPrice) {
        int newQuantity = item.getQuantity() + addQuantity;
        item.setQuantity(newQuantity);
        item.setTotalPrice(money(unitPrice.multiply(BigDecimal.valueOf(newQuantity))));
    }

    private CartItem createPizzaItem(Cart cart, Pizza pizza, PizzaSize pizzaSize, String ingredientNames,
                                     BigDecimal unitPrice, int quantity) {
        CartItem item = new CartItem();
        item.setCart(cart);
        item.setPizza(pizza);
        item.setPizzaSize(pizzaSize);
        item.setItemName(pizza.getName() + " " + pizzaSize.getName());
        item.setUnitPrice(unitPrice);
        item.setQuantity(quantity);
        item.setExtraIngredients(ingredientNames);
        item.setTotalPrice(money(unitPrice.multiply(BigDecimal.valueOf(quantity))));
        return item;
    }

    private CartItem createDrinkItem(Cart cart, Drink drink, BigDecimal unitPrice, int quantity) {
        CartItem item = new CartItem();
        item.setCart(cart);
        item.setDrink(drink);
        item.setItemName(drink.getName());
        item.setUnitPrice(unitPrice);
        item.setQuantity(quantity);
        item.setTotalPrice(money(unitPrice.multiply(BigDecimal.valueOf(quantity))));
        return item;
    }

    public BigDecimal calculateDeliveryFee(Cart cart) {
        BigDecimal subtotal = cart.getSubtotal();
        return priceCalculationService.calculateDeliveryFee(subtotal);
    }

    private CartItem findItemOrThrow(Cart cart, Integer itemId) {
        return cart.getItems().stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Cart item not found: " + itemId));
    }

    private void requireOwnership(User user, CartItem item) {
        if (!item.getCart().getUser().getId().equals(user.getId())) {
            throw new SecurityException("Item does not belong to user");
        }
    }

    private void touchAndSave(Cart cart) {
        cart.setUpdatedAt(LocalDateTime.now());
        cartRepository.save(cart);
    }

    private BigDecimal money(BigDecimal value) {
        return value.setScale(2, RoundingMode.HALF_UP);
    }
}
