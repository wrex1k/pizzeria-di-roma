package com.pizzeriadiroma.pizzeria.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

public class AddPizzaRequest {

    @NotBlank(message = "{validation.pizzaName.required}")
    @Size(min = 2, max = 50, message = "{validation.pizzaName.size}")
    @Pattern(
            regexp = "^[\\p{L}0-9][\\p{L}0-9 '\\-&,\\.]*$",
            message = "{validation.pizzaName.pattern}"
    )
    private String name;

    @NotBlank(message = "{validation.description.required}")
    @Size(min = 10, max = 2000, message = "{validation.description.size.min}")
    private String description;

    @NotNull(message = "{validation.basePrice.required}")
    @DecimalMin(value = "0.00", inclusive = true, message = "{validation.basePrice.decimalMin}")
    @DecimalMax(value = "9999.99", inclusive = true, message = "{validation.basePrice.decimalMax}")
    @Digits(integer = 4, fraction = 2, message = "{validation.basePrice.digits}")
    private BigDecimal basePrice;

    @NotNull(message = "{validation.imageUrl.required}")
    @Size(max = 100, message = "{validation.imageUrl.size}")
    private String imageUrl;

    private Boolean available;
    private Boolean featured;

    @Size(max = 20, message = "{validation.tagIds.size}")
    private List<@NotNull(message = "{validation.tagId.notNull}")
    @Positive(message = "{validation.tagId.positive}") Integer> tagIds;

    @Size(max = 50, message = "{validation.ingredientIds.size}")
    private List<@NotNull(message = "{validation.ingredientId.notNull}")
    @Positive(message = "{validation.ingredientId.positive}") Integer> ingredientIds;

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
