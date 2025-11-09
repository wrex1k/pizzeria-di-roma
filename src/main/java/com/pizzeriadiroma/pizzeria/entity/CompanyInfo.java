package com.pizzeriadiroma.pizzeria.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
@Table(name = "company_info")
public class CompanyInfo {
    public CompanyInfo() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Company name must not be blank.")
    @Size(min = 2, max = 100, message = "Company name must be between 2 and 50 characters.")
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank(message = "Description must not be blank.")
    @Size(max = 2000, message = "Description must be max 2000 characters.")
    @Column(name = "description", nullable = false)
    private String description;

    @Pattern(
            regexp = "^\\+\\d{1,3}(\\s?\\d{3}){3}$",
            message = "Invalid phone number format. Expected format: +421 905 123 456"
    )
    @Size(max = 30, message = "Phone number must not exceed 30 characters.")
    @Column(length = 30)
    private String phone;

    @Pattern(
            regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            message = "Invalid email format. Example: info@pizzeriadiroma.it"
    )
    @Size(max = 100, message = "Email must not exceed 100 characters.")
    @Column(length = 100)
    private String email;

    @Size(max = 100, message = "Address must not exceed 100 characters.")
    @Column(length = 100)
    private String address;

    @Pattern(
            regexp = "^(https?://)?([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$",
            message = "Invalid URL format."
    )
    @Size(max = 100, message = "Facebook URL must not exceed 100 characters.")
    @Column(name = "facebook_url", length = 100)
    private String facebookUrl;

    @Pattern(
            regexp = "^(https?://)?([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$",
            message = "Invalid URL format."
    )
    @Size(max = 100, message = "Instagram URL must not exceed 100 characters.")
    @Column(name = "instagram_url", length = 100)
    private String instagramUrl;

    @Pattern(
            regexp = "^(https?://)?([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$",
            message = "Invalid URL format."
    )
    @Size(max = 100, message = "Twitter URL must not exceed 100 characters.")
    @Column(name = "twitter_url", length = 100)
    private String twitterUrl;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

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
