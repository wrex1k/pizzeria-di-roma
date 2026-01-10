package com.pizzeriadiroma.pizzeria.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_addresses")
public class UserAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "label", length = 80)
    private String label;

    @NotBlank(message = "{validation.street.required}")
    @Size(min = 3, max = 160, message = "{validation.street.size}")
    @Pattern(regexp = "^[^\\d]+$", message = "{validation.street.pattern}")
    @Column(name = "street", nullable = false, length = 160)
    private String street;

    @NotBlank(message = "{validation.houseNumber.required}")
    @Pattern(regexp = "\\d+", message = "{validation.houseNumber.pattern}")
    @Size(min = 1, max = 40, message = "{validation.houseNumber.size}")
    @Column(name = "house_number", nullable = false, length = 40)
    private String houseNumber;

    @NotBlank(message = "{validation.city.required}")
    @Size(min = 2, max = 120, message = "{validation.city.size}")
    @Pattern(regexp = "^[^\\d]+$", message = "{validation.city.pattern}")
    @Column(name = "city", nullable = false, length = 120)
    private String city;

    @NotBlank(message = "{validation.postalCode.required}")
    @Pattern(regexp = "\\d+", message = "{validation.postalCode.pattern}")
    @Size(min = 3, max = 10, message = "{validation.postalCode.size}")
    @Column(name = "postal_code", nullable = false, length = 10)
    private String postalCode;

    @NotBlank(message = "{validation.country.required}")
    @Size(min = 2, max = 80, message = "{validation.country.size}")
    @Pattern(regexp = "^[^\\d]+$", message = "{validation.country.pattern}")
    @Column(name = "country", nullable = false, length = 80)
    private String country;

    @Column(name = "is_default", nullable = false)
    private Boolean isDefault = false;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at", nullable = false)
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
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

}
