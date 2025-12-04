package com.pizzeriadiroma.pizzeria.repository;

import com.pizzeriadiroma.pizzeria.entity.Drink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DrinkRepository extends JpaRepository<Drink, Long> {

    @Query("SELECT d FROM Drink d WHERE d.isAvailable = true")
    List<Drink> findAllAvailable();
}
