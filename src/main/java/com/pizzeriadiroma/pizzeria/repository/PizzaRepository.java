package com.pizzeriadiroma.pizzeria.repository;

import com.pizzeriadiroma.pizzeria.entity.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PizzaRepository extends JpaRepository<Pizza, Long> {

    @Query("SELECT p FROM Pizza p WHERE p.isAvailable = true AND p.isFeatured = true")
    List<Pizza> findFeaturedPizzas();

    @Query("SELECT p FROM Pizza p WHERE p.isAvailable = true")
    List<Pizza> findAllAvailable();

    @Query("SELECT p FROM Pizza p JOIN p.tags t WHERE t.name = :tagName")
    List<Pizza> findByTagName(@Param("tagName") String tagName);

    Optional<Pizza> findBySlug(String slug);
}