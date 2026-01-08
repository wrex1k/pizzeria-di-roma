package com.pizzeriadiroma.pizzeria.dto;

import com.pizzeriadiroma.pizzeria.entity.Order;
import jakarta.validation.constraints.*;

public class CheckoutRequest {

    @NotBlank(message = "Full name is required")
    @Size(min = 3, max = 100, message = "Full name must be between 3 and 100 characters")
    private String fullName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\+[0-9]{1,3}[\\s0-9]{7,15}$", message = "Enter a valid phone number")
    private String phone;

    @Size(max = 200, message = "Delivery instructions must not exceed 200 characters")
    private String deliveryInstructions;

    private Long addressId;

    @NotNull(message = "Please select a delivery method")
    private Order.DeliveryMethod deliveryMethod;

    @NotNull(message = "Please select a payment method")
    private Order.PaymentMethod paymentMethod;

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getDeliveryInstructions() { return deliveryInstructions; }
    public void setDeliveryInstructions(String deliveryInstructions) { this.deliveryInstructions = deliveryInstructions; }

    public Long getAddressId() { return addressId; }
    public void setAddressId(Long addressId) { this.addressId = addressId; }

    public Order.DeliveryMethod getDeliveryMethod() { return deliveryMethod; }
    public void setDeliveryMethod(Order.DeliveryMethod deliveryMethod) { this.deliveryMethod = deliveryMethod; }

    public Order.PaymentMethod getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(Order.PaymentMethod paymentMethod) { this.paymentMethod = paymentMethod; }
}
