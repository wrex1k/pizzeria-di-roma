package com.pizzeriadiroma.pizzeria.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class CompanyInfoRequest {

    @NotBlank(message = "Company name must not be blank.")
    @Size(min = 2, max = 100, message = "Company name must be between 2 and 100 characters.")
    private String name;

    @NotBlank(message = "Description must not be blank.")
    @Size(max = 2000, message = "Description must be max 2000 characters.")
    private String description;

    @Pattern(
            regexp = "^\\+\\d{1,3}(\\s?\\d{3}){3}$",
            message = "Invalid phone number format. Expected format: +421 905 123 456"
    )
    @Size(max = 30, message = "Phone number must not exceed 30 characters.")
    private String phone;

    @Pattern(
            regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            message = "Invalid email format. Example: info@pizzeriadiroma.it"
    )
    @Size(max = 100, message = "Email must not exceed 100 characters.")
    private String email;

    @Size(max = 100, message = "Address must not exceed 100 characters.")
    private String address;

    @Pattern(regexp = "^(https?://)?([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$",
            message = "Invalid URL format.")
    @Size(max = 200, message = "Facebook URL must not exceed 100 characters.")
    private String facebookUrl;

    @Pattern(regexp = "^(https?://)?([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$",
            message = "Invalid URL format.")
    @Size(max = 200, message = "Instagram URL must not exceed 100 characters.")
    private String instagramUrl;

    @Pattern(regexp = "^(https?://)?([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$",
            message = "Invalid URL format.")
    @Size(max = 200, message = "Twitter URL must not exceed 100 characters.")
    private String twitterUrl;

    @NotNull(message = "DPH rate is required.")
    @DecimalMin(value = "0.00", inclusive = true, message = "DPH must be >= 0.")
    @DecimalMax(value = "100.00", inclusive = true, message = "DPH must be <= 100.")
    private BigDecimal dphRate;

    @NotNull(message = "Free delivery from is required.")
    @DecimalMin(value = "0.00", inclusive = true, message = "Free delivery from must be >= 0.")
    private BigDecimal freeDeliveryFrom;

    @NotNull(message = "Delivery price is required.")
    @DecimalMin(value = "0.00", inclusive = true, message = "Delivery price must be >= 0.")
    private BigDecimal deliveryPrice;

    @NotNull(message = "Extra ingredient price is required.")
    @DecimalMin(value = "0.00", inclusive = true, message = "Extra ingredient price must be >= 0.")
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
