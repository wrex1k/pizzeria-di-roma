package com.pizzeriadiroma.pizzeria.service;

import com.pizzeriadiroma.pizzeria.entity.*;
import com.pizzeriadiroma.pizzeria.exception.ValidationException;
import com.pizzeriadiroma.pizzeria.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final PriceCalculationService priceCalculationService;
    private final CartService cartService;

    public OrderService(
            OrderRepository orderRepository,
            PriceCalculationService priceCalculationService,
            CartService cartService
    ) {
        this.orderRepository = orderRepository;
        this.priceCalculationService = priceCalculationService;
        this.cartService = cartService;
    }

    @Transactional(readOnly = true)
    public List<Order> getOrdersByUser(User user) {
        return orderRepository.findByUserOrderByCreatedAtDesc(user);
    }

    @Transactional(readOnly = true)
    public long countOrdersByUser(User user) {
        return orderRepository.countByUser(user);
    }

    @Transactional(readOnly = true)
    public BigDecimal calculateTotalSpent(User user) {
        List<Order> orders = orderRepository.findByUserOrderByCreatedAtDesc(user);
        return orders.stream()
                .filter(order -> order.getStatus() == Order.OrderStatus.DELIVERED)
                .map(Order::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Transactional(readOnly = true)
    public BigDecimal calculateAverageOrderValue(User user) {
        List<Order> deliveredOrders = orderRepository.findByUserAndStatusOrderByCreatedAtDesc(
                user, Order.OrderStatus.DELIVERED);

        if (deliveredOrders.isEmpty()) {
            return BigDecimal.ZERO;
        }

        BigDecimal totalSpent = deliveredOrders.stream()
                .map(Order::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return totalSpent.divide(
                new BigDecimal(deliveredOrders.size()), 
                2, 
                RoundingMode.HALF_UP
        );
    }

    @Transactional
    public Order createOrder(
            User user,
            Cart cart,
            UserAddress deliveryAddress,
            Order.DeliveryMethod deliveryMethod,
            Order.PaymentMethod paymentMethod,
            String deliveryInstructions
    ) {

        if (user == null) {
            throw new ValidationException("User is required.");
        }

        if (cart == null) {
            throw new ValidationException("Cart does not exist.");
        }

        if (cart.getItems() == null || cart.getItems().isEmpty()) {
            throw new ValidationException("Cart is empty.");
        }

        if (deliveryMethod == null) {
            throw new ValidationException("Delivery method is required.");
        }

        if (paymentMethod == null) {
            throw new ValidationException("Payment method is required.");
        }

        if (deliveryMethod == Order.DeliveryMethod.DELIVERY && deliveryAddress == null) {
            throw new ValidationException(
                    "Delivery address is required for delivery orders."
            );
        }

        if (deliveryMethod == Order.DeliveryMethod.DELIVERY) {
            if (isBlank(deliveryAddress.getStreet())
                    || isBlank(deliveryAddress.getHouseNumber())
                    || isBlank(deliveryAddress.getCity())
                    || isBlank(deliveryAddress.getPostalCode())) {

                throw new ValidationException(
                        "Delivery address is incomplete."
                );
            }
        }

        Order order = new Order();

        order.setOrderNumber(generateOrderNumber());

        order.setUser(user);
        order.setCustomerFirstName(user.getFirstName());
        order.setCustomerLastName(user.getLastName());
        order.setCustomerPhone(user.getPhone());

        if (deliveryAddress != null) {
            order.setDeliveryStreet(
                    deliveryAddress.getStreet() + " " + deliveryAddress.getHouseNumber()
            );
            order.setDeliveryCity(deliveryAddress.getCity());
            order.setDeliveryPostalCode(deliveryAddress.getPostalCode());
        }

        order.setDeliveryInstructions(deliveryInstructions);
        order.setDeliveryMethod(deliveryMethod);
        order.setPaymentMethod(paymentMethod);
        order.setOrderSource(Order.OrderSource.ONLINE);
        order.setStatus(Order.OrderStatus.PENDING);

        BigDecimal subtotal = cart.getSubtotal();
        BigDecimal deliveryFee =
                deliveryMethod == Order.DeliveryMethod.DELIVERY
                        ? cartService.calculateDeliveryFee(cart)
                        : BigDecimal.ZERO;

        BigDecimal vatPercent = priceCalculationService.getVatRate();

        order.setSubtotalAmount(subtotal);
        order.setDeliveryFeeAmount(deliveryFee);
        order.setDiscountAmount(BigDecimal.ZERO);
        order.setDphPercent(vatPercent);
        order.setTotalAmount(subtotal.add(deliveryFee));

        order.setEstimatedPreparationTime(
                deliveryMethod == Order.DeliveryMethod.PICKUP ? 20 : 25
        );
        order.setEstimatedDeliveryTime(
                deliveryMethod == Order.DeliveryMethod.DELIVERY ? 35 : 0
        );

        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = new OrderItem();

            if (cartItem.getPizza() != null) {
                orderItem.setItemType(OrderItem.ItemType.PIZZA);
                orderItem.setPizza(cartItem.getPizza());
                orderItem.setSize(cartItem.getPizzaSize());
            } else if (cartItem.getDrink() != null) {
                orderItem.setItemType(OrderItem.ItemType.DRINK);
                orderItem.setDrink(cartItem.getDrink());
            } else {
                throw new ValidationException("Invalid cart item.");
            }

            orderItem.setItemName(cartItem.getItemName());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setUnitPrice(cartItem.getUnitPrice());
            orderItem.setTotalPrice(cartItem.getTotalPrice());

            order.addItem(orderItem);
        }

        return orderRepository.save(order);
    }

    private String generateOrderNumber() {
        String timestamp =
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String randomPart =
                UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return "ORD-" + timestamp + "-" + randomPart;
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
