package com.pizzeriadiroma.pizzeria.repository;

import com.pizzeriadiroma.pizzeria.entity.PizzaSize;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PizzaSizeRepository extends JpaRepository<PizzaSize, Integer> {
    Optional<PizzaSize> findByName(String name);
}
