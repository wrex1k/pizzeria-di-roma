package com.pizzeriadiroma.pizzeria.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "company_info")
public class CompanyInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Company name must not be blank.")
    @Size(min = 2, max = 100, message = "Company name must be between 2 and 100 characters.")
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @NotBlank(message = "Description must not be blank.")
    @Size(max = 2000, message = "Description must be max 2000 characters.")
    @Column(name = "description", nullable = false, length = 2000)
    private String description;

    @Pattern(
            regexp = "^\\+\\d{1,3}(\\s?\\d{3}){3}$",
            message = "Invalid phone number format. Expected format: +421 905 123 456"
    )
    @Size(max = 30, message = "Phone number must not exceed 30 characters.")
    @Column(name = "phone", length = 30)
    private String phone;

    @Pattern(
            regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            message = "Invalid email format. Example: info@pizzeriadiroma.it"
    )
    @Size(max = 100, message = "Email must not exceed 100 characters.")
    @Column(name = "email", length = 100)
    private String email;

    @Size(max = 100, message = "Address must not exceed 100 characters.")
    @Column(name = "address", length = 100)
    private String address;

    @Pattern(
            regexp = "^(https?://)?([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$",
            message = "Invalid URL format."
    )
    @Size(max = 200, message = "Facebook URL must not exceed 200 characters.")
    @Column(name = "facebook_url", length = 200)
    private String facebookUrl;

    @Pattern(
            regexp = "^(https?://)?([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$",
            message = "Invalid URL format."
    )
    @Size(max = 200, message = "Instagram URL must not exceed 200 characters.")
    @Column(name = "instagram_url", length = 200)
    private String instagramUrl;

    @Pattern(
            regexp = "^(https?://)?([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$",
            message = "Invalid URL format."
    )
    @Size(max = 200, message = "Twitter URL must not exceed 200 characters.")
    @Column(name = "twitter_url", length = 200)
    private String twitterUrl;

    @NotNull
    @DecimalMin(value = "0.00", inclusive = true)
    @DecimalMax(value = "100.00", inclusive = true)
    @Column(name = "dph_rate", nullable = false, precision = 10, scale = 2)
    private BigDecimal dphRate;

    @NotNull
    @DecimalMin(value = "0.00", inclusive = true)
    @Column(name = "free_delivery_from", nullable = false, precision = 10, scale = 2)
    private BigDecimal freeDeliveryFrom;

    @NotNull
    @DecimalMin(value = "0.00", inclusive = true)
    @Column(name = "delivery_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal deliveryPrice;

    @NotNull
    @DecimalMin(value = "0.00", inclusive = true)
    @Column(name = "extra_ingredient_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal extraIngredientPrice;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getFacebookUrl() { return facebookUrl; }
    public void setFacebookUrl(String facebookUrl) { this.facebookUrl = facebookUrl; }

    public String getInstagramUrl() { return instagramUrl; }
    public void setInstagramUrl(String instagramUrl) { this.instagramUrl = instagramUrl; }

    public String getTwitterUrl() { return twitterUrl; }
    public void setTwitterUrl(String twitterUrl) { this.twitterUrl = twitterUrl; }

    public BigDecimal getDphRate() { return dphRate; }
    public void setDphRate(BigDecimal dphRate) { this.dphRate = dphRate; }

    public BigDecimal getFreeDeliveryFrom() { return freeDeliveryFrom; }
    public void setFreeDeliveryFrom(BigDecimal freeDeliveryFrom) { this.freeDeliveryFrom = freeDeliveryFrom; }

    public BigDecimal getDeliveryPrice() { return deliveryPrice; }
    public void setDeliveryPrice(BigDecimal deliveryPrice) { this.deliveryPrice = deliveryPrice; }

    public BigDecimal getExtraIngredientPrice() { return extraIngredientPrice; }
    public void setExtraIngredientPrice(BigDecimal extraIngredientPrice) { this.extraIngredientPrice = extraIngredientPrice; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
