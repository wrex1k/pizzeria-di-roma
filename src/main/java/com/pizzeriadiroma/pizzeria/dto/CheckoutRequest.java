package com.pizzeriadiroma.pizzeria.dto;

import com.pizzeriadiroma.pizzeria.entity.Order;
import jakarta.validation.constraints.*;

public class CheckoutRequest {

    @NotBlank(message = "{validation.fullName.required}")
    @Size(min = 3, max = 100, message = "{validation.fullName.size}")
    private String fullName;

    @NotBlank(message = "{validation.email.required}")
    @Email(message = "{validation.email.invalid}")
    private String email;

    @NotBlank(message = "{validation.phone.required}")
    @Pattern(regexp = "^\\+[0-9]{1,3}[\\s0-9]{7,15}$", message = "{validation.phone.invalid}")
    private String phone;

    @Size(max = 200, message = "{validation.deliveryInstructions.size}")
    private String deliveryInstructions;

    private Long addressId;

    @NotNull(message = "{validation.deliveryMethod.required}")
    private Order.DeliveryMethod deliveryMethod;

    @NotNull(message = "{validation.paymentMethod.required}")
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
