package com.pizzeriadiroma.pizzeria.dto;

import jakarta.validation.constraints.*;

public class RegisterRequest {

    @NotBlank(message = "{validation.firstName.required}")
    @Size(min = 2, max = 20, message = "{validation.firstName.size}")
    private String firstName;

    @NotBlank(message = "{validation.lastName.required}")
    @Size(min = 2, max = 40, message = "{validation.lastName.size}")
    private String lastName;

    @NotBlank(message = "{validation.email.required}")
    @Email(message = "{validation.email.invalid}")
    @Size(max = 255, message = "{validation.email.size}")
    private String email;

    @NotBlank(message = "{validation.phone.required}")
    @Pattern(regexp = "^\\+[0-9]{1,3}[\\s0-9]{7,15}$", message = "{validation.phone.invalid}")
    @Size(min = 9, max = 20, message = "{validation.phone.size}")
    private String phone;

    @NotBlank(message = "{validation.password.required}")
    @Size(min = 8, message = "{validation.password.size}")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z\\d]).+$",
            message = "{validation.password.pattern}"
    )
    private String password;

    @NotBlank(message = "{validation.confirmPassword.required}")
    private String confirmPassword;

    @AssertTrue(message = "{validation.termsAccepted.required}")
    private Boolean termsAccepted;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Boolean getTermsAccepted() {
        return termsAccepted;
    }

    public void setTermsAccepted(Boolean termsAccepted) {
        this.termsAccepted = termsAccepted;
    }
}
