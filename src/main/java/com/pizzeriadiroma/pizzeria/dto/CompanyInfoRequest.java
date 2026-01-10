package com.pizzeriadiroma.pizzeria.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class CompanyInfoRequest {

    @NotBlank(message = "{validation.companyName.required}")
    @Size(min = 2, max = 100, message = "{validation.companyName.size}")
    private String name;

    @NotBlank(message = "{validation.description.required}")
    @Size(max = 2000, message = "{validation.description.size.max}")
    private String description;

    @Pattern(
            regexp = "^\\+\\d{1,3}(\\s?\\d{3}){3}$",
            message = "{validation.companyPhone.invalid}"
    )
    @Size(max = 30, message = "{validation.companyPhone.size}")
    private String phone;

    @Pattern(
            regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            message = "{validation.companyEmail.invalid}"
    )
    @Size(max = 100, message = "{validation.companyEmail.size}")
    private String email;

    @Size(max = 100, message = "{validation.address.size}")
    private String address;

    @Pattern(regexp = "^(https?://)?([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$",
            message = "{validation.url.invalid}")
    @Size(max = 200, message = "{validation.facebookUrl.size}")
    private String facebookUrl;

    @Pattern(regexp = "^(https?://)?([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$",
            message = "{validation.url.invalid}")
    @Size(max = 200, message = "{validation.instagramUrl.size}")
    private String instagramUrl;

    @Pattern(regexp = "^(https?://)?([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$",
            message = "{validation.url.invalid}")
    @Size(max = 200, message = "{validation.twitterUrl.size}")
    private String twitterUrl;

    @NotNull(message = "{validation.dphRate.required}")
    @DecimalMin(value = "0.00", inclusive = true, message = "{validation.dphRate.decimalMin}")
    @DecimalMax(value = "100.00", inclusive = true, message = "{validation.dphRate.decimalMax}")
    private BigDecimal dphRate;

    @NotNull(message = "{validation.freeDeliveryFrom.required}")
    @DecimalMin(value = "0.00", inclusive = true, message = "{validation.freeDeliveryFrom.decimalMin}")
    private BigDecimal freeDeliveryFrom;

    @NotNull(message = "{validation.deliveryPrice.required}")
    @DecimalMin(value = "0.00", inclusive = true, message = "{validation.deliveryPrice.decimalMin}")
    private BigDecimal deliveryPrice;

    @NotNull(message = "{validation.extraIngredientPrice.required}")
    @DecimalMin(value = "0.00", inclusive = true, message = "{validation.extraIngredientPrice.decimalMin}")
    private BigDecimal extraIngredientPrice;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFacebookUrl() {
        return facebookUrl;
    }

    public void setFacebookUrl(String facebookUrl) {
        this.facebookUrl = facebookUrl;
    }

    public String getInstagramUrl() {
        return instagramUrl;
    }

    public void setInstagramUrl(String instagramUrl) {
        this.instagramUrl = instagramUrl;
    }

    public String getTwitterUrl() {
        return twitterUrl;
    }

    public void setTwitterUrl(String twitterUrl) {
        this.twitterUrl = twitterUrl;
    }

    public BigDecimal getDphRate() {
        return dphRate;
    }

    public void setDphRate(BigDecimal dphRate) {
        this.dphRate = dphRate;
    }

    public BigDecimal getFreeDeliveryFrom() {
        return freeDeliveryFrom;
    }

    public void setFreeDeliveryFrom(BigDecimal freeDeliveryFrom) {
        this.freeDeliveryFrom = freeDeliveryFrom;
    }

    public BigDecimal getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(BigDecimal deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public BigDecimal getExtraIngredientPrice() {
        return extraIngredientPrice;
    }

    public void setExtraIngredientPrice(BigDecimal extraIngredientPrice) {
        this.extraIngredientPrice = extraIngredientPrice;
    }
}
