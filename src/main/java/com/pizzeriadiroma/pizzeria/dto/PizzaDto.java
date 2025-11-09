package com.pizzeriadiroma.pizzeria.dto;

import java.math.BigDecimal;
import java.util.List;

public class PizzaDto {
    private Integer id;
    private String name;
    private String description;
    private String imageUrl;
    private BigDecimal basePrice;
    private boolean isAvailable;
    private boolean isFeatured;
    private List<String> tagNames;

    public PizzaDto(Integer id,
                    String name,
                    String description,
                    String imageUrl,
                    BigDecimal basePrice,
                    boolean isAvailable,
                    boolean isFeatured,
                    List<String> tagNames) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.basePrice = basePrice;
        this.isAvailable = isAvailable;
        this.isFeatured = isFeatured;
        this.tagNames = tagNames;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public boolean isFeatured() {
        return isFeatured;
    }

    public void setFeatured(boolean featured) {
        isFeatured = featured;
    }

    public List<String> getTagNames() {
        return tagNames;
    }

    public void setTagNames(List<String> tagNames) {
        this.tagNames = tagNames;
    }

    @Override
    public String toString() {
        return "PizzaDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", basePrice=" + basePrice +
                ", isAvailable=" + isAvailable +
                ", isFeatured=" + isFeatured +
                ", tagNames=" + tagNames +
                '}';
    }
}
