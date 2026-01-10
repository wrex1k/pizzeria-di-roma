package com.pizzeriadiroma.pizzeria.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.context.i18n.LocaleContextHolder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "pizzas")
public class Pizza {

    public Pizza() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "{validation.pizzaName.required}")
    @Size(min = 2, max = 50, message = "{validation.pizzaName.size}")
    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true, updatable = false)
    private String slug;

    @NotBlank(message = "{validation.description.required}")
    @Size(max = 2000, message = "{validation.description.size.max}")
    @Column(nullable = false)
    private String description_en;

    @NotBlank(message = "{validation.description.required}")
    @Size(max = 2000, message = "{validation.description.size.max}")
    @Column(nullable = false)
    private String description_sk;

    @Size(max = 500, message = "{validation.imageUrl.size}")
    @Column(name = "image_url")
    private String imageUrl;

    @NotNull(message = "{validation.basePrice.required}")
    @DecimalMin(value = "0.00", inclusive = true, message = "{validation.basePrice.decimalMin}")
    @Digits(integer = 8, fraction = 2, message = "{validation.basePrice.digits}")
    @Column(name = "base_price", nullable = false)
    private BigDecimal basePrice;

    @NotNull(message = "{validation.availability.required}")
    @Column(name = "is_available", nullable = false)
    private Boolean isAvailable = true;

    @Column(name = "is_featured", nullable = false)
    private Boolean isFeatured = false;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "pizza_tags",
            joinColumns = @JoinColumn(name = "pizza_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    @OrderBy("name ASC")
    private Set<Tag> tags = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "pizza_ingredients",
            joinColumns = @JoinColumn(name = "pizza_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    @OrderBy("name ASC")
    private Set<Ingredient> ingredients = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "pizza_available_sizes",
            joinColumns = @JoinColumn(name = "pizza_id"),
            inverseJoinColumns = @JoinColumn(name = "size_id")
    )
    @OrderBy("priceExtra ASC")
    private List<PizzaSize> sizes = new ArrayList<>();


    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getDescription() {
        Locale locale = LocaleContextHolder.getLocale();

        if ("sk".equals(locale.getLanguage())) {
            return description_sk;
        }

        return description_en;
    }

    public void setDescription(String description) {
        Locale locale = LocaleContextHolder.getLocale();

        if ("sk".equals(locale.getLanguage())) {
            this.description_sk = description;
        } else {
            this.description_en = description;
        }
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

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public Boolean getFeatured() {
        return isFeatured;
    }

    public void setFeatured(Boolean isFeatured) {
        this.isFeatured = isFeatured;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public List<PizzaSize> getSizes() {
        return sizes;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
