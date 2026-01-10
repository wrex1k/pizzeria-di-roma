package com.pizzeriadiroma.pizzeria.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.context.i18n.LocaleContextHolder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Locale;

@Entity
@Table(name = "company_info")
public class CompanyInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "{validation.companyName.required}")
    @Size(min = 2, max = 100, message = "{validation.companyName.size}")
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @NotBlank(message = "{validation.description.required}")
    @Size(max = 2000, message = "{validation.description.size.max}")
    @Column(name = "description_en", nullable = false, length = 2000)
    private String description_en;

    @NotBlank(message = "{validation.description.required}")
    @Size(max = 2000, message = "{validation.description.size.max}")
    @Column(name = "description_sk", nullable = false, length = 2000)
    private String description_sk;

    @Pattern(
            regexp = "^\\+\\d{1,3}(\\s?\\d{3}){3}$",
            message = "{validation.companyPhone.invalid}"
    )
    @Size(max = 30, message = "{validation.companyPhone.size}")
    @Column(name = "phone", length = 30)
    private String phone;

    @Pattern(
            regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            message = "{validation.companyEmail.invalid}"
    )
    @Size(max = 100, message = "{validation.companyEmail.size}")
    @Column(name = "email", length = 100)
    private String email;

    @Size(max = 100, message = "{validation.address.size}")
    @Column(name = "address", length = 100)
    private String address;

    @Pattern(
            regexp = "^(https?://)?([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$",
            message = "{validation.url.invalid}"
    )
    @Size(max = 200, message = "{validation.facebookUrl.size}")
    @Column(name = "facebook_url", length = 200)
    private String facebookUrl;

    @Pattern(
            regexp = "^(https?://)?([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$",
            message = "{validation.url.invalid}"
    )
    @Size(max = 200, message = "{validation.instagramUrl.size}")
    @Column(name = "instagram_url", length = 200)
    private String instagramUrl;

    @Pattern(
            regexp = "^(https?://)?([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$",
            message = "{validation.url.invalid}"
    )
    @Size(max = 200, message = "{validation.twitterUrl.size}")
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
