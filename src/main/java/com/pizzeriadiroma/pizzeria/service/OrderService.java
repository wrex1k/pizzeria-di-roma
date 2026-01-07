package com.pizzeriadiroma.pizzeria.service;

import com.pizzeriadiroma.pizzeria.entity.Order;
import com.pizzeriadiroma.pizzeria.entity.User;
import com.pizzeriadiroma.pizzeria.repository.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
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

}
