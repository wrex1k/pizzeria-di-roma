package com.pizzeriadiroma.pizzeria.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pizza_sizes")
public class PizzaSize {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(name = "size_label", nullable = false)
    private String sizeLabel;

    @Column(name = "price_extra", nullable = false, precision = 5, scale = 2)
    private BigDecimal priceExtra;

    @Column(name = "ingredient_multiplier", nullable = false, precision = 5, scale = 2)
    private BigDecimal ingredientMultiplier;

    @Column(name = "calories_multiplier", nullable = false, precision = 5, scale = 2)
    private BigDecimal caloriesMultiplier;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public PizzaSize() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSizeLabel() { return sizeLabel; }
    public void setSizeLabel(String sizeLabel) { this.sizeLabel = sizeLabel; }

    public BigDecimal getPriceExtra() { return priceExtra; }
    public void setPriceExtra(BigDecimal priceExtra) { this.priceExtra = priceExtra; }

    public BigDecimal getIngredientMultiplier() { return ingredientMultiplier; }
    public void setIngredientMultiplier(BigDecimal ingredientMultiplier) { this.ingredientMultiplier = ingredientMultiplier; }

    public BigDecimal getCaloriesMultiplier() { return caloriesMultiplier; }
    public void setCaloriesMultiplier(BigDecimal caloriesMultiplier) { this.caloriesMultiplier = caloriesMultiplier; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
