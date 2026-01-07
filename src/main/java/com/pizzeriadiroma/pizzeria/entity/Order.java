package com.pizzeriadiroma.pizzeria.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    public enum DeliveryMethod {
        DELIVERY,
        PICKUP
    }

    public enum OrderSource {
        ONLINE,
        RESTAURANT
    }

    public enum PaymentMethod {
        CARD,
        CASH
    }

    public enum OrderStatus {
        PENDING,
        PREPARING,
        READY,
        PICKED_UP,
        OUT_FOR_DELIVERY,
        DELIVERED,
        CANCELLED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_number", nullable = false, unique = true, length = 60)
    private String orderNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "customer_first_name", nullable = false, length = 80)
    private String customerFirstName;

    @Column(name = "customer_last_name", nullable = false, length = 80)
    private String customerLastName;

    @Column(name = "customer_phone", length = 40)
    private String customerPhone;

    @Column(name = "delivery_street", length = 160)
    private String deliveryStreet;

    @Column(name = "delivery_city", length = 120)
    private String deliveryCity;

    @Column(name = "delivery_postal_code", length = 20)
    private String deliveryPostalCode;

    @Column(name = "delivery_instructions", columnDefinition = "TEXT")
    private String deliveryInstructions;

    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_method", nullable = false)
    private DeliveryMethod deliveryMethod = DeliveryMethod.DELIVERY;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_source", nullable = false)
    private OrderSource orderSource = OrderSource.ONLINE;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    private PaymentMethod paymentMethod = PaymentMethod.CASH;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status = OrderStatus.PENDING;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_chef_id")
    private User assignedChef;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_courier_id")
    private User assignedCourier;

    @Column(name = "subtotal_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotalAmount = BigDecimal.ZERO;

    @Column(name = "delivery_fee_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal deliveryFeeAmount = BigDecimal.ZERO;

    @Column(name = "discount_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal discountAmount = BigDecimal.ZERO;

    @Column(name = "dph_percent", nullable = false, precision = 5, scale = 2)
    private BigDecimal dphPercent = new BigDecimal("20.00");

    @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount = BigDecimal.ZERO;

    @Column(name = "loyalty_points_earned")
    private Integer loyaltyPointsEarned = 0;

    @Column(name = "estimated_preparation_time", nullable = false)
    private Integer estimatedPreparationTime = 15;

    @Column(name = "estimated_delivery_time", nullable = false)
    private Integer estimatedDeliveryTime = 30;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "preparation_start_at")
    private LocalDateTime preparationStartAt;

    @Column(name = "ready_at")
    private LocalDateTime readyAt;

    @Column(name = "out_for_delivery_at")
    private LocalDateTime outForDeliveryAt;

    @Column(name = "delivered_at")
    private LocalDateTime deliveredAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderItem> items = new ArrayList<>();

    // Constructors
    public Order() {
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getDeliveryStreet() {
        return deliveryStreet;
    }

    public void setDeliveryStreet(String deliveryStreet) {
        this.deliveryStreet = deliveryStreet;
    }

    public String getDeliveryCity() {
        return deliveryCity;
    }

    public void setDeliveryCity(String deliveryCity) {
        this.deliveryCity = deliveryCity;
    }

    public String getDeliveryPostalCode() {
        return deliveryPostalCode;
    }

    public void setDeliveryPostalCode(String deliveryPostalCode) {
        this.deliveryPostalCode = deliveryPostalCode;
    }

    public String getDeliveryInstructions() {
        return deliveryInstructions;
    }

    public void setDeliveryInstructions(String deliveryInstructions) {
        this.deliveryInstructions = deliveryInstructions;
    }

    public DeliveryMethod getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(DeliveryMethod deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public OrderSource getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(OrderSource orderSource) {
        this.orderSource = orderSource;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public User getAssignedChef() {
        return assignedChef;
    }

    public void setAssignedChef(User assignedChef) {
        this.assignedChef = assignedChef;
    }

    public User getAssignedCourier() {
        return assignedCourier;
    }

    public void setAssignedCourier(User assignedCourier) {
        this.assignedCourier = assignedCourier;
    }

    public BigDecimal getSubtotalAmount() {
        return subtotalAmount;
    }

    public void setSubtotalAmount(BigDecimal subtotalAmount) {
        this.subtotalAmount = subtotalAmount;
    }

    public BigDecimal getDeliveryFeeAmount() {
        return deliveryFeeAmount;
    }

    public void setDeliveryFeeAmount(BigDecimal deliveryFeeAmount) {
        this.deliveryFeeAmount = deliveryFeeAmount;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public BigDecimal getDphPercent() {
        return dphPercent;
    }

    public void setDphPercent(BigDecimal dphPercent) {
        this.dphPercent = dphPercent;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getLoyaltyPointsEarned() {
        return loyaltyPointsEarned;
    }

    public void setLoyaltyPointsEarned(Integer loyaltyPointsEarned) {
        this.loyaltyPointsEarned = loyaltyPointsEarned;
    }

    public Integer getEstimatedPreparationTime() {
        return estimatedPreparationTime;
    }

    public void setEstimatedPreparationTime(Integer estimatedPreparationTime) {
        this.estimatedPreparationTime = estimatedPreparationTime;
    }

    public Integer getEstimatedDeliveryTime() {
        return estimatedDeliveryTime;
    }

    public void setEstimatedDeliveryTime(Integer estimatedDeliveryTime) {
        this.estimatedDeliveryTime = estimatedDeliveryTime;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getPreparationStartAt() {
        return preparationStartAt;
    }

    public void setPreparationStartAt(LocalDateTime preparationStartAt) {
        this.preparationStartAt = preparationStartAt;
    }

    public LocalDateTime getReadyAt() {
        return readyAt;
    }

    public void setReadyAt(LocalDateTime readyAt) {
        this.readyAt = readyAt;
    }

    public LocalDateTime getOutForDeliveryAt() {
        return outForDeliveryAt;
    }

    public void setOutForDeliveryAt(LocalDateTime outForDeliveryAt) {
        this.outForDeliveryAt = outForDeliveryAt;
    }

    public LocalDateTime getDeliveredAt() {
        return deliveredAt;
    }

    public void setDeliveredAt(LocalDateTime deliveredAt) {
        this.deliveredAt = deliveredAt;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public void addItem(OrderItem item) {
        items.add(item);
        item.setOrder(this);
    }

    public void removeItem(OrderItem item) {
        items.remove(item);
        item.setOrder(null);
    }

    public String getFullDeliveryAddress() {
        if (deliveryStreet != null && deliveryCity != null && deliveryPostalCode != null) {
            return deliveryStreet + ", " + deliveryPostalCode + " " + deliveryCity;
        }
        return "N/A";
    }

    public String getCustomerFullName() {
        return customerFirstName + " " + customerLastName;
    }
}
