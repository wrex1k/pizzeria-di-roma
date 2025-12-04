package com.pizzeriadiroma.pizzeria.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "pizzas")
public class Pizza {
    public Pizza() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Pizza name must not be blank.")
    @Size(min = 2, max = 50, message = "Pizza name must be between 2 and 50 characters.")
    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String slug;

    @NotBlank(message = "Pizza description must not be blank.")
    @Size(max = 2000, message = "Description must be max 2000 characters.")
    @Column(nullable = false)
    private String description;

    @Size(max = 500, message = "Image URL must not exceed 500 characters.")
    @Column(name = "image_url")
    private String imageUrl;

    @NotNull(message = "Base price cannot be null.")
    @DecimalMin(value = "0.00", inclusive = true, message = "Base price must be greater than or equal to 0.")
    @Digits(integer = 8, fraction = 2, message = "Base price must have up to 8 digits and 2 decimals.")
    @Column(name = "base_price", nullable = false)
    private BigDecimal basePrice;

    @NotNull(message = "Availability flag cannot be null.")
    @Column(name = "is_available", nullable = false)
    private Boolean isAvailable = true;

    @Column(name = "is_featured")
    private Boolean isFeatured = false;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @ManyToMany(fetch = FetchType.LAZY)
    @OrderBy("name ASC")
    @JoinTable(
            name = "pizza_tags",
            joinColumns = @JoinColumn(name = "pizza_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();

    @OneToMany(mappedBy = "pizza", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PizzaIngredient> ingredients = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() { return slug; }

    public void setSlug(String slug) { this.slug = slug; }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) { this.basePrice = basePrice; }

    public Boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public Boolean isFeatured() {
        return isFeatured;
    }

    public void setFeatured(Boolean isFeatured) {
        this.isFeatured = isFeatured;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Set<Tag> getTags() { return tags; }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public List<PizzaIngredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<PizzaIngredient> ingredients) { this.ingredients = ingredients; }
}
