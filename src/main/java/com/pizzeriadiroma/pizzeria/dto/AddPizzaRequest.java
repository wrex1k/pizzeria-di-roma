package com.pizzeriadiroma.pizzeria.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

public class AddPizzaRequest {

    @NotBlank(message = "Pizza name is required.")
    @Size(min = 2, max = 50, message = "Pizza name must be between 2 and 50 characters.")
    @Pattern(
            regexp = "^[\\p{L}0-9][\\p{L}0-9 '\\-&,\\.]*$",
            message = "Pizza name contains invalid characters."
    )
    private String name;

    @NotBlank(message = "Description is required.")
    @Size(min = 10, max = 2000, message = "Description must be between 10 and 2000 characters.")
    private String description;

    @NotNull(message = "Base price is required.")
    @DecimalMin(value = "0.00", inclusive = true, message = "Base price must be 0.00 or more.")
    @DecimalMax(value = "9999.99", inclusive = true, message = "Base price must not exceed 9999.99.")
    @Digits(integer = 4, fraction = 2, message = "Base price must have up to 4 digits and 2 decimal places (e.g., 9.99).")
    private BigDecimal basePrice;

    @NotNull(message = "Image URL is required.")
    @Size(max = 100, message = "Image URL must not exceed 500 characters.")
    private String imageUrl;

    private Boolean available;
    private Boolean featured;

    @Size(max = 20, message = "You can select up to 20 tags.")
    private List<@NotNull(message = "Tag ID cannot be null.")
    @Positive(message = "Tag ID must be a positive number.") Integer> tagIds;

    @Size(max = 50, message = "You can select up to 50 ingredients.")
    private List<@NotNull(message = "Ingredient ID cannot be null.")
    @Positive(message = "Ingredient ID must be a positive number.") Integer> ingredientIds;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getBasePrice() { return basePrice; }
    public void setBasePrice(BigDecimal basePrice) { this.basePrice = basePrice; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public Boolean getAvailable() { return available; }
    public void setAvailable(Boolean available) { this.available = available; }

    public Boolean getFeatured() { return featured; }
    public void setFeatured(Boolean featured) { this.featured = featured; }

    public List<Integer> getTagIds() { return tagIds; }
    public void setTagIds(List<Integer> tagIds) { this.tagIds = tagIds; }

    public List<Integer> getIngredientIds() { return ingredientIds; }
    public void setIngredientIds(List<Integer> ingredientIds) { this.ingredientIds = ingredientIds; }
}
