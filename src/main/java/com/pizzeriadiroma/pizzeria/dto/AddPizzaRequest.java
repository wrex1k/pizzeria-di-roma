package com.pizzeriadiroma.pizzeria.web;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

public class PizzaUpsertForm {

    @NotBlank
    @Size(min = 2, max = 50)
    private String name;

    @NotBlank
    @Size(max = 2000)
    private String description;

    @NotNull
    @DecimalMin(value = "0.00", inclusive = true)
    private BigDecimal basePrice;

    @Size(max = 500)
    private String imageUrl;

    private Boolean featured = false;

    private List<Integer> tagIds;
    private List<Integer> ingredientIds;

    // getters/setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getBasePrice() { return basePrice; }
    public void setBasePrice(BigDecimal basePrice) { this.basePrice = basePrice; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public Boolean getFeatured() { return featured; }
    public void setFeatured(Boolean featured) { this.featured = featured; }

    public List<Integer> getTagIds() { return tagIds; }
    public void setTagIds(List<Integer> tagIds) { this.tagIds = tagIds; }

    public List<Integer> getIngredientIds() { return ingredientIds; }
    public void setIngredientIds(List<Integer> ingredientIds) { this.ingredientIds = ingredientIds; }
}
