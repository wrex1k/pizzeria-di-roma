package com.pizzeriadiroma.pizzeria.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ingredients")

public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Ingredient name cannot be blank.")
    @Size(max = 200, message = "Ingredient name must be max 200 characters.")
    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, length = 50)
    private String emoji;

    @NotNull(message = "Calories value cannot be null.")
    @DecimalMin(value = "0.00", message = "Calories must be non-negative.")
    @Digits(integer = 10, fraction = 2)
    @Column(name = "calories_per_100g", nullable = false)
    private BigDecimal caloriesPer100g = BigDecimal.ZERO;

    @NotNull
    @DecimalMin(value = "0.00")
    @Digits(integer = 10, fraction = 2)
    @Column(name = "proteins_per_100g", nullable = false)
    private BigDecimal proteinsPer100g = BigDecimal.ZERO;

    @NotNull
    @DecimalMin(value = "0.00")
    @Digits(integer = 10, fraction = 2)
    @Column(name = "carbs_per_100g", nullable = false)
    private BigDecimal carbsPer100g = BigDecimal.ZERO;

    @NotNull
    @DecimalMin(value = "0.00")
    @Digits(integer = 10, fraction = 2)
    @Column(name = "fats_per_100g", nullable = false)
    private BigDecimal fatsPer100g = BigDecimal.ZERO;

    @NotNull
    @Column(name = "extra", nullable = false)
    private Boolean extra;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public Ingredient() {}

    public Ingredient(String name) { this.name = name; }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getEmoji() {return emoji; }

    public void setEmoji(String emoji) { this.emoji = emoji; }

    public BigDecimal getCaloriesPer100g() { return caloriesPer100g; }

    public void setCaloriesPer100g(BigDecimal caloriesPer100g) { this.caloriesPer100g = caloriesPer100g; }

    public BigDecimal getProteinsPer100g() { return proteinsPer100g; }

    public void setProteinsPer100g(BigDecimal proteinsPer100g) { this.proteinsPer100g = proteinsPer100g; }

    public BigDecimal getCarbsPer100g() { return carbsPer100g; }

    public void setCarbsPer100g(BigDecimal carbsPer100g) { this.carbsPer100g = carbsPer100g; }

    public BigDecimal getFatsPer100g() { return fatsPer100g; }

    public void setFatsPer100g(BigDecimal fatsPer100g) { this.fatsPer100g = fatsPer100g; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
